package com.flybirds.security.mange;

import cn.hutool.core.util.ObjectUtil;
import com.flybirds.api.RemoteLogService;
import com.flybirds.api.core.dto.LogininforRespDTO;
import com.flybirds.common.constant.CacheConstantEnum;
import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.exception.BaseException;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.spring.SpringUtils;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.security.model.OutTask;
import com.flybirds.security.oauth.OauthRedisManger;
import com.flybirds.security.service.UserTokenServiceManger;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

import static com.flybirds.common.enums.login.AccountLoginOutStatusEnum.OUT_SUCCESS;

/**
 * 订阅者 登录之后进行用户个人信息存储操作
 *
 * @author :flybirds
 */
@Slf4j
public abstract class LoginOutAdapter0 implements EventHandler<OutTask>,LoginOutHandler0{

    private Disruptor<OutTask> disruptor;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private RemoteLogService remoteLogService;

    @Autowired
    private UserTokenServiceManger userTokenService;

    @Override
    public void onEvent(OutTask event, long taskid, boolean endOfBatch) throws Exception {

       try {
           AssertUtil.isNotNull(event,String.format("token移除任务参数异常 任务序号为id %s",taskid));
           userExit(event.getTokenValue());
           log.info(" {} 退出任务提交成功 token = {}",event.getOutChannel().getLabel(),event.getTokenValue());
       }catch (Exception e){
           e.printStackTrace();
           log.error("用户退出完成任务提交失败 {}",e.getMessage());
           //任务日志先不开发
       }
    }
    /**
     * 获取登录之后处理Disruptor处理队列
     * @return
     */
    @Override
    public Disruptor<OutTask> getDisruptor() {

        return disruptor;
    }

    /**
     * 初始化队列对象
     */
    @PostConstruct
    protected void initDisuptor() {
        if (disruptor == null) {
            synchronized (this) {
                if (disruptor == null) {
                    int ringBufferSize = 32 * 1024;
                    ThreadFactory threadFactory = (Runnable runnable) -> new Thread(runnable);
                    disruptor = new Disruptor<>(() -> OutTask.builder().build(), ringBufferSize, threadFactory);
                    //从spring工厂中获取本身的bean.  由于事务问题，无法同步进行，所以顺序进行
                    disruptor.handleEventsWith(SpringUtils.getBean(this.getClass()));
                    //可以使用then进行后续的订阅者的操作

                    //定义异常默认处理方式
                    disruptor.setDefaultExceptionHandler(new ExceptionHandler<OutTask>() {
                        @Override
                        public void handleEventException(Throwable ex, long sequence, OutTask event) {
                            log.info(" {} 退出任务提交失败 异常信息为:{} token = {}",event.getOutChannel().getLabel(),ex.getMessage(),event.getTokenValue());
                        }
                        @Override
                        public void handleOnStartException(final Throwable ex) {
                            log.error("Exception during onStart()", ex);
                        }

                        @Override
                        public void handleOnShutdownException(final Throwable ex) {
                            log.error("Exception during onShutdown()", ex);
                        }
                    });
                    //启动
                    disruptor.start();
                }
            }
        }
    }

    /* 退出 */
    protected Result<OAuth2AccessToken> out(String tokenValue){
        try {
            if (StringUtils.isEmpty(tokenValue))
            {
                return Result.ok();
            }
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            if (accessToken == null || StringUtils.isEmpty(accessToken.getValue()))
            {
                return Result.ok();
            }

            tokenStore.removeAccessToken(accessToken); // 清空 access token
            OAuth2RefreshToken refreshToken = accessToken.getRefreshToken(); // 清空 refresh token
            tokenStore.removeRefreshToken(refreshToken);
            return Result.ok(accessToken);
        }catch (Exception e){
            log.error("清除登录用户异常 令牌{}",tokenValue);
            Result.errorFail("清除登录用户异常");
            throw new BaseException("清除登录用户异常");
        }
    }

    /* 清除 tokenKey and TokenListKey */
    protected void closeTokenKey(OAuth2AccessToken accessToken, String tokenValue){
        Map<String, ?> map = accessToken.getAdditionalInformation();
        if (map.containsKey(SecurityConstant.DETAILS_USERNAME)) {

            String username = (String) map.get(SecurityConstant.DETAILS_USERNAME);
            String tokenKey = userTokenService.getUserTokenToCacheKey(map);
            String tokenListKey = userTokenService.getUserTokenListToCachetKey(map,accessToken);
            if(ObjectUtil.isNotNull(tokenKey)){
                //解析数据
                OauthRedisManger.builder().delKey(CacheConstantEnum.LOGIN_TOKEN_KEY.buildSuffix(tokenKey));
                log.info("清除在线用户 Token redisCache 清理成功 令牌[ {} ]",tokenValue);
            }
            if(ObjectUtil.isNotNull(tokenListKey)){
                OauthRedisManger.builder().delCacheList(CacheConstantEnum.LOGIN_TOKENLIST_KEY.buildSuffix(tokenListKey), tokenValue);
                log.info("清除在线用户 TokenList redisCache 清理成功 令牌[ {} ]", tokenValue);
            }
        }
    }

    /* 发送日志数据包 */
    protected void sendRemoteLogInfor(Map<String, ?> map,String message){
        if (map.containsKey(SecurityConstant.DETAILS_USERNAME)) {
            remoteLogService.saveLogininfor(LogininforRespDTO.builder()
                    .userAgent((String) map.get(SecurityConstant.OAUTH_USER_AGENT))
                    .userIp((String) map.get(SecurityConstant.OAUTH_USER_IP))
                    .username((String) map.get(SecurityConstant.DETAILS_USERNAME))
                    .message(message)
                    .status(String.valueOf(OUT_SUCCESS.getValue()))
                    .type("1")
                    .build());
        }
    }

}
