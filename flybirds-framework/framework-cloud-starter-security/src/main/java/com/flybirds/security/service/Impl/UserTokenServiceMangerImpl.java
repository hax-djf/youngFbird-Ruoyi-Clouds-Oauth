package com.flybirds.security.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import com.flybirds.common.constant.CacheConstantEnum;
import com.flybirds.common.constant.CodeConstant;
import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.enums.sms.SmsCodeCacheStatusEnum;
import com.flybirds.common.exception.BaseException;
import com.flybirds.common.exception.UserException;
import com.flybirds.common.util.date.DateUtils;
import com.flybirds.common.util.idtool.IdUtils;
import com.flybirds.common.util.sign.Md5Utils;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.security.core.SecurityUtils;
import com.flybirds.security.model.LoginUser;
import com.flybirds.security.oauth.OauthRedisManger;
import com.flybirds.security.service.UserTokenServiceManger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户toke服务管理器
 *
 * @author :flybirds
 */
@Component
@Slf4j
public class UserTokenServiceMangerImpl implements UserTokenServiceManger {

    private  static final Long SAVE_MOBILE_CODE_EXPRRE = 15l  * 60;

    private  static final Long REMOVE_MOBILE_CODE_EXPRRE = 5l  * 60;

    @Autowired
    private TokenStore tokenStore;

    /* 从缓存中获取loginUser */
    @Override
    public LoginUser getLoginUserToCache() {

        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(SecurityUtils.getToken());

        LoginUser loginUser = null;
        if(oAuth2Authentication != null){
            loginUser= (LoginUser) oAuth2Authentication.getPrincipal();
        }
        return loginUser;

    }
    /* 更新缓存中用户数据 */
    @Override
    public void setLoginUserToCache(LoginUser loginUser) {

        try {
            OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(SecurityUtils.getToken());
            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(SecurityUtils.getToken());
            oAuth2Authentication.setDetails(loginUser);
            tokenStore.storeAccessToken(oAuth2AccessToken,oAuth2Authentication);
            log.info("用户数据更新完成 时间{} 用户名{}", DateUtils.getDate(),loginUser.getUsername());
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException(String.format("用户数据更新完成 时间 %s ,用户名 %s", DateUtils.getDate(),loginUser.getUsername()));
        }
    }

    /**
     * 刷新在线用户以及userTokenAccess的存储数据
     * @param EXPIRE_TIME 登录信息
     * @param oAuth2AccessToken
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void refreshToken(long EXPIRE_TIME,DefaultOAuth2AccessToken oAuth2AccessToken) {
        /**
         * 需要获取到oauth2Access数据
         * 1.在线usertoken,在线用户
         * 2.获取到2个key值，进行时间设置
         */
       AuthToken authToken = UserTokenServiceManger.oAuth2AccessTokenToAuthToken(oAuth2AccessToken);
       try {

           OauthRedisManger.builder().expireTimeUserTokenBoth2Keys(getUserTokenToCacheKey(authToken)
                   ,getUserTokenListToCachetKey(authToken),EXPIRE_TIME);

           log.info("刷新在线用户token数据成功 用户名{}",authToken.getUser_name());

       }catch (RuntimeException e){
           e.printStackTrace();
           throw new BaseException(String.format("刷新在线用户token数据成功 用户名 %s",authToken.getUser_name()));
       }
    }

    /* 获取随机密钥 */
    @Override
    public String getRandomSecretKey() {
      return IdUtils.fastSimpleUUID();
    }

    /**
     * 获取存储token密钥
     */
    @Override
    public String getUserTokenToCacheKey(AuthToken authToken) {
        /**
         * 内部密钥的生成策略
         * 1.设计方案【存在同一个用户多个人登录】->使用功能用户强制退出
         用户名+加密的盐值【从数据库中查询】+token的随机码【ase的加密】+MD5加密操作 -->对用一个token数据
         */
        String user_name = authToken.getUser_name();
        String oauth_token_secretkey = authToken.getOauth_token_secretkey();
        return getUserTokenToCacheKey(user_name,oauth_token_secretkey);
    }
    @Override
    public String getUserTokenToCacheKey(Map<String,?> map) {

        String user_name = (String) map.get(SecurityConstant.DETAILS_USERNAME);
        String oauth_token_secretkey = (String) map.get(SecurityConstant.OAUTH_TOKEN_SECRETKEY);
        return getUserTokenToCacheKey(user_name,oauth_token_secretkey);
    }
    @Override
    public String getUserTokenToCacheKey(String user_name,String oauth_token_secretkey){
        String userMd5Token = Md5Utils.hash(new StringBuilder(user_name)
                .append(oauth_token_secretkey)
                .toString());
        return userMd5Token;
    }

    /* 存储authToken值 */
    @Override
    public void saveOauth2UserAccessToeknToRedis(AuthToken authToken) {
        OauthRedisManger.builder().setObjectByKey(CacheConstantEnum.LOGIN_TOKEN_KEY.buildSuffix(getUserTokenToCacheKey(authToken)),
                authToken.getAccessToken(),Long.valueOf(authToken.getExpires_in()), TimeUnit.SECONDS);
    }

    /* 多用户key在线获取 加密机制 [用户名+用户的授权范围+加密盐值随机码]*/
//    @Override
//    public String  getUserTokenListToCachetKey(AuthToken authToken) {
//        return  getUserTokenListToCachetKey(authToken.getUser_name(),authToken.getScope(),authToken.getUser_id());
//    }
//    @Override
//    public String  getUserTokenListToCachetKey(Map<String,?> authTokenmap, OAuth2AccessToken accessToken ) {
//        String user_name = (String) authTokenmap.get(SecurityConstant.DETAILS_USERNAME);
//        long user_id = (Long) authTokenmap.get(SecurityConstant.DETAILS_USER_ID);
//
//        String  accessTokensScope = null;
//        Set<String> scopeSet = accessToken.getScope();
//        if(StringUtils.isNotEmpty(scopeSet)){
//            accessTokensScope = scopeSet.stream().collect(Collectors.joining(" "));
//        }else{
//            throw new UserException(CodeConstant.character.FAILURE,"用户客户端数据权限为空，异常请求");
//        }
//        return getUserTokenListToCachetKey(user_name,accessTokensScope,user_id);
//    }
//    @Override
//    public String getUserTokenListToCachetKey(String user_name,String scope,long user_id){
//        return Md5Utils.hash(new StringBuilder(user_name)
//                .append(scope).append(user_id)
//                .toString());
//    }

    @Override
    public String  getUserTokenListToCachetKey(AuthToken authToken) {
        return  getUserTokenListToCachetKey(authToken.getUser_name(),authToken.getUser_id());
    }
    @Override
    public String  getUserTokenListToCachetKey(Map<String,?> authTokenmap, OAuth2AccessToken accessToken ) {
        String user_name = (String) authTokenmap.get(SecurityConstant.DETAILS_USERNAME);
        long user_id = (Long) authTokenmap.get(SecurityConstant.DETAILS_USER_ID);

        String  accessTokensScope = null;
        Set<String> scopeSet = accessToken.getScope();
        if(StringUtils.isNotEmpty(scopeSet)){
            accessTokensScope = scopeSet.stream().collect(Collectors.joining(" "));
        }else{
            throw new UserException(CodeConstant.character.FAILURE,"用户客户端数据权限为空，异常请求");
        }
        return getUserTokenListToCachetKey(user_name,user_id);
    }

    @Override
    public String getUserTokenListToCachetKey(String user_name,long user_id){
        return Md5Utils.hash(new StringBuilder(user_name)
                .append(user_id)
                .toString());
    }


    /* 手机号短信登录 获取验证校验处理 */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SmsCodeCacheStatusEnum checkLoginStatusByMobile(String mobile) {

        if(OauthRedisManger.builder().exists(CacheConstantEnum.CAPTCHA_CODE_KEY_MOBILE.buildSuffix(mobile))){
            return SmsCodeCacheStatusEnum.NO_FAILURE;
        }

        if(OauthRedisManger.builder().exists(CacheConstantEnum.CAPTCHA_CODE_KEY_MOBILE_NUMBER.buildSuffix(mobile))){

            //校验长度
            Long cacheListSizeByListKey = OauthRedisManger.builder().getCacheListSize(CacheConstantEnum.CAPTCHA_CODE_KEY_MOBILE_NUMBER.buildSuffix(mobile));

            if(ObjectUtil.isNotNull(cacheListSizeByListKey) && cacheListSizeByListKey > 3){
                //设置过期时间
                OauthRedisManger.builder().expireByKey(CacheConstantEnum.CAPTCHA_CODE_KEY_MOBILE_NUMBER.buildSuffix(mobile),REMOVE_MOBILE_CODE_EXPRRE);
                return SmsCodeCacheStatusEnum.FREQUENT_OPERATION;
            }
        }
        //记录短信获取次数
        OauthRedisManger.builder().setCacheListLeftPush(CacheConstantEnum.CAPTCHA_CODE_KEY_MOBILE_NUMBER.buildSuffix(mobile) , mobile , SAVE_MOBILE_CODE_EXPRRE);
        return SmsCodeCacheStatusEnum.NORMAL;
    }

    /**
     * 获取所有的tokenAccess数据
     * @param clientId
     * @return
     */
    public Collection<OAuth2AccessToken> getTokensByClientId(String clientId){

        return tokenStore.findTokensByClientId("flybirds");
    }
}
