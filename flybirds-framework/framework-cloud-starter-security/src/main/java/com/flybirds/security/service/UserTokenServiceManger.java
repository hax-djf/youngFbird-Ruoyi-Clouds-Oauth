package com.flybirds.security.service;

import com.flybirds.common.constant.CodeConstant;
import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.core.model.UserAgentEntity;
import com.flybirds.common.enums.sms.SmsCodeCacheStatusEnum;
import com.flybirds.common.exception.UserException;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.common.util.date.DateUtils;
import com.flybirds.common.util.ip.AddressUtils;
import com.flybirds.common.util.ip.IpUtils;
import com.flybirds.common.util.ip.UserAgentUtils;
import com.flybirds.common.util.servlet.ServletUtils;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.security.model.LoginUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  @author flybirds
 *  @description: UsertokenManger - 管理工具
 */
public interface UserTokenServiceManger {

    /* 获取缓存中的用户信息*/
     LoginUser getLoginUserToCache();

    /* 更新缓存中用户数*/
     void setLoginUserToCache(LoginUser loginUser);

    /**
     * 刷新在线用户以及userTokenAccess的存储数据【同步更新】
     * @param EXPIRE_TIME 登录信息
     */
     void refreshToken(long EXPIRE_TIME, DefaultOAuth2AccessToken oAuth2AccessToken);

    /* 获取随机密 */
     String getRandomSecretKey();

     /* 获取存储token密钥 */
     String getUserTokenToCacheKey(AuthToken authToken);
     String getUserTokenToCacheKey(Map<String, ?> map);
     String getUserTokenToCacheKey(String user_name, String oauth_token_secretkey);


    /** 存储authToken值 */
     void saveOauth2UserAccessToeknToRedis(AuthToken authToken);


     /* 获取同时在线的 tokenListkey*/
     String  getUserTokenListToCachetKey(AuthToken authToken);
     String  getUserTokenListToCachetKey(Map<String, ?> authTokenmap, OAuth2AccessToken accessToken);
//     String getUserTokenListToCachetKey(String user_name, String scope, long user_id);
     String getUserTokenListToCachetKey(String user_name,long user_id);

    /**
     * 校验短信登录到额状态
     * @param mobile
     * @return
     */
     SmsCodeCacheStatusEnum checkLoginStatusByMobile(String mobile);

     Collection<OAuth2AccessToken> getTokensByClientId(String clientId);

     /* 登录ip地址信息封装 */
    static UserAgentEntity genUserAgent(LoginUser loginUser) {

        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        String ip = IpUtils.getOauthUserIpAddr(httpServletRequest);
        return builderUerAgent(httpServletRequest,ip);
    }
    static UserAgentEntity genUserAgent() {

        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        String ip = IpUtils.getIpAddr(httpServletRequest);
        return builderUerAgent(httpServletRequest,ip);
    }
    static UserAgentEntity builderUerAgent(HttpServletRequest httpServletRequest,String ip){
        String borderName = UserAgentUtils.getBorderName(UserAgentUtils.getUserAgent(httpServletRequest));
        String browserVersion = UserAgentUtils.getBrowserVersion(UserAgentUtils.getUserAgent(httpServletRequest));
        String osName = UserAgentUtils.getOsName(UserAgentUtils.getUserAgent(httpServletRequest));
        String os = UserAgentUtils.getOs(UserAgentUtils.getUserAgent(httpServletRequest));
        return new UserAgentEntity(
                DateUtils.getNowDate().getTime(),
                ip,
                AddressUtils.getRealAddressByIP(ip),
                String.format("%s 版本:%s",borderName,browserVersion),
                String.format("%s 版本:%s",os,osName));
    }
    static UserAgentEntity builderUerAgent(String userAgent,String userIp){
        String borderName = UserAgentUtils.getBorderName(userAgent);
        String browserVersion = UserAgentUtils.getBrowserVersion(userAgent);
        String osName = UserAgentUtils.getOsName(userAgent);
        String os = UserAgentUtils.getOs(userAgent);
        return new UserAgentEntity(
                DateUtils.getNowDate().getTime(),
                userIp,
                AddressUtils.getRealAddressByIP(userIp),
                String.format("%s 版本:%s",borderName,browserVersion),
                String.format("%s 版本:%s",os,osName));
    }
    static UserAgentEntity builderUerAgent(Map<String,?> map){
        String userIp = (String) map.get(SecurityConstant.OAUTH_USER_IP);
        String userAgent = (String) map.get(SecurityConstant.OAUTH_USER_AGENT);
        String borderName = UserAgentUtils.getBorderName(userAgent);
        String browserVersion = UserAgentUtils.getBrowserVersion(userAgent);
        String osName = UserAgentUtils.getOsName(userAgent);
        String os = UserAgentUtils.getOs(userAgent);
        return new UserAgentEntity(
                DateUtils.getNowDate().getTime(),
                userIp,
                AddressUtils.getRealAddressByIP(userIp),
                String.format("%s 版本:%s",borderName,browserVersion),
                String.format("%s 版本:%s",os,osName));
    }

    /* 通过 DefaultOAuth2AccessToken 组装一个auth数据 */
    static AuthToken oAuth2AccessTokenToAuthToken(DefaultOAuth2AccessToken accessToken){

        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();

        AssertUtil.isTrue(StringUtils.isEmpty(accessToken.getScope()) && StringUtils.isNull(additionalInformation),
                new UserException(CodeConstant.character.FAILURE,"用户客户端数据权限为空，异常请求"));
        return AuthToken.builder().user_name((String)additionalInformation.getOrDefault(SecurityConstant.DETAILS_USERNAME,"用户名读取失败"))
                .scope(accessToken.getScope().stream().collect(Collectors.joining(" ")))
                .user_id((Long) additionalInformation.getOrDefault(SecurityConstant.DETAILS_USER_ID,"用户id读取失败"))
                .oauth_token_secretkey((String)additionalInformation.getOrDefault(SecurityConstant.OAUTH_TOKEN_SECRETKEY,"用户随机码读取失败"))
                .expires_in(accessToken.getExpiresIn())
                .user_agent((String)additionalInformation.getOrDefault(SecurityConstant.OAUTH_USER_AGENT,"用户登录地址数据读取失败"))
                .user_agent((String)additionalInformation.getOrDefault(SecurityConstant.OAUTH_USER_IP,"用户登录ip数据读取失败"))
                .build();
    }
}