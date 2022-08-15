package com.flybirds.oauth.manger.login;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flybirds.api.RemoteLogService;
import com.flybirds.api.RemoteSocialService;
import com.flybirds.api.RemoteUserService;
import com.flybirds.api.core.dto.LogininforRespDTO;
import com.flybirds.api.core.entity.SysUser;
import com.flybirds.api.model.UserInfo;
import com.flybirds.common.constant.ApiConstant;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.core.model.LoginReqVo0;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import com.flybirds.common.enums.login.AccountLoginGrantType;
import com.flybirds.common.enums.login.AccountLoginStatusEnum;
import com.flybirds.common.enums.user.UserStatusEnum;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.common.util.ip.IpUtils;
import com.flybirds.common.util.ip.UserAgentUtils;
import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.servlet.ServletUtils;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.oauth.exception.CustomOauthException;
import com.flybirds.oauth.push.LoginEventPush;
import com.flybirds.security.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;

/**
 * login 服务管理
 *
 * @author flybirds
 */
public abstract class UserLoginAdapter0<T extends LoginReqVo0> implements UserLoginHandler0<T> {

    public Logger log = LoggerFactory.getLogger(UserLoginAdapter0.class);
    @Autowired
    protected RemoteLogService remoteLogService;
    @Autowired
    protected RemoteUserService remoteUserService;
    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    protected LoadBalancerClient loadBalancerClient;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected RemoteSocialService remoteSocialService;
    /**
     * @since 1.0
     * 发送远程日志 common
     *
     * @param dto
     */
     protected void sendRemoteLogininfo(LogininforRespDTO dto){

        remoteLogService.saveLogininfor(dto);
     }

    /**
     * @since 1.0
     * 校验 common
     *
     * @param userResult
     * @param username
     */
     protected void  checkUserNotNull(Result<UserInfo> userResult, String username){

        if (StringUtils.isNull(userResult)
                || StringUtils.isNull(userResult.getData())
                || StringUtils.isNull(userResult.getData().getSysUser())) {
            // 实际上抛出的异常信息为InvalidGrantException
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
    }

    /**
     * @since 1.o
     * 校验 common
     *
     * @param sysUser
     * @param username
     */
    protected void checkUserStatus(SysUser sysUser,String username) {

        AssertUtil.isTrue(sysUser.getDelFlag(),info ->{
            log.info("登录用户：{} 已被删除.", info);
        },username,new CustomOauthException(String.format("对不起，您的账号：%s 已注销",username)));

        AssertUtil.isTrue(UserStatusEnum.DISABLE.isTrue(sysUser.getStatus()),info ->{
            log.info("登录用户：{} 已被停用.", info);
        },username,new CustomOauthException(String.format("对不起，您的账号：%s 已被停用",username)));

        AssertUtil.isTrue(UserStatusEnum.LOCKS.isTrue(sysUser.getLocks()),info ->{
            log.info("登录用户：{} 已锁定.", info);
        },username,new CustomOauthException(String.format("对不起，您的账号：%s 已锁定",username)));

    }

    /**
     * @since 1.0
     * oauth2.0 请求 httpOauthInit common
     *
     * @param formData
     * @param accountLoginChannel
     * @param  loginParams clientId,clientSecret,grandType 顺序
     * @return
     */
    protected AuthToken httpOauthInit(MultiValueMap<String,Object> formData,Integer accountLoginChannel,String... loginParams){
        ServiceInstance choose = loadBalancerClient.choose(Constant.CloudServiceName.FLYBIRDS_OAUTH);
        String OauthUrl = new StringBuilder(choose.getUri().toString()).append(ApiConstant.API_AUTH2_TOKEN).toString();

        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add(SecurityConstant.HEADER,new StringBuilder(SecurityConstant.TOKEN_TYPE).append(Base64.getEncoder()
                .encodeToString(new String(loginParams[0]+":"+loginParams[1]).getBytes())).toString());

        headers.add(SecurityConstant.USER_AGENT, UserAgentUtils.getUserAgent(ServletUtils.getRequest())); // 浏览器
        headers.add(SecurityConstant.USER_IP, IpUtils.getIpAddr(ServletUtils.getRequest())); // IP
        formData.add(AccountLoginGrantType.OAUTH2_TYPE_CONSTANT,loginParams[2]); // 登录oauth2.0 类型
        formData.add(AccountLoginChannelEnum.ACCOUNT_LOGIN_CHANNEL,accountLoginChannel); //登录渠道
        return httpOuathTemplatePOST(request->
                        restTemplate.exchange(OauthUrl, HttpMethod.POST, request, Map.class)
                ,formData,headers);
    }

    /**
     * @since 1.0
     * get authToken data
     *
     * @param authTokenFunction
     * @param formData
     * @param headers
     * @return
     */
    private AuthToken httpOuathTemplatePOST(Function<HttpEntity<MultiValueMap>,ResponseEntity<Map>> authTokenFunction, MultiValueMap<String,Object> formData, MultiValueMap<String,String>  headers){

        HttpEntity<MultiValueMap> requestentity = new HttpEntity<>(formData,headers);
        /** 解释：
         *  发送请求地址为 {@link org.springframework.security.oauth2.provider.endpoint.TokenEndpoint#postAccessToken(Principal, Map)}
         *  可以重写实现，但是考虑到不多写全局异常，直接使用默认自带的，将不覆写接口 {@link ApiConstant.API_AUTH2_TOKEN}
         * */
        ResponseEntity<Map> response = authTokenFunction.apply(requestentity);
        Map<String,Object> body = response.getBody();
        AssertUtil.isTrue(StringUtils.isNotNull(body.get("code")),(String) body.getOrDefault(AjaxResult.MSG_TAG,"login error")); // 如果为异常信息，将装成AjaxResult 处理
        return JSON.parseObject(JSONObject.toJSONString(body), AuthToken.class);
    }

    /**
     * @since 1.0
     * event push task
     *
     * @param authToken
     * @param channel
     * @param status
     */
    protected void eventPushLoginTask(AuthToken authToken,String userName,AccountLoginChannelEnum channel,AccountLoginStatusEnum status){
            LoginEventPush.EventpushLoginTask(token ->
                            ObjectUtil.isNotEmpty(authToken) && StrUtil.isNotBlank(authToken.getAccessToken()),
                    authToken,userName,
                    channel, status);
    }

    protected UserDetails builder0(UserInfo userInfo,String userName,AccountLoginChannelEnum channel){
        checkUserStatus(userInfo.getSysUser(),userName);
        userInfo.getSysUser().setLastLoginChannel(channel);//设置登录渠道
        return builder0(userInfo);
    }

    /**
     * @since 1.0
     * builder userDetails
     *
     * @param userInfo
     * @return
     */
    protected UserDetails builder0(UserInfo userInfo) {
        SysUser user = userInfo.getSysUser();
        Set<String> dbAuthsSet = new HashSet<String>();
        if (StringUtils.isNotNull(userInfo.getRoles())
                && StringUtils.isNotEmpty(userInfo.getRoles())) {
            // 获取角色
            dbAuthsSet.addAll(userInfo.getRoles());
            // 获取权限
            dbAuthsSet.addAll(userInfo.getPermissions());
        }
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(dbAuthsSet.toArray(new String[0]));
        log.info("认证中的线程为 {}",Thread.currentThread().getName());
        log.info(String.format("【登录用户】：%s+【权限信息】:%s",user.getUserName(),dbAuthsSet.toString()));
        boolean enabled = ObjectUtil.equal(UserStatusEnum.DISABLE.getCode(),user.getStatus()); //1.nabled 启用
        boolean accountNonExpired = ObjectUtil.equal(UserStatusEnum.DELETED.getCode(),user.getDelFlag()); //2.account Non Expired 账户不过期
        boolean accountNonLocked = ObjectUtil.equal(UserStatusEnum.LOCKS.getCode(),user.getLocks()); //3.credentials Non Expired 凭证不过期 4. account Non Locked 非锁定账户
        return new LoginUser(user.getUserId(), user.getUserName(), user.getPassWord(), enabled, accountNonExpired, true, accountNonLocked,
                authorities,user,userInfo);

    }
}
