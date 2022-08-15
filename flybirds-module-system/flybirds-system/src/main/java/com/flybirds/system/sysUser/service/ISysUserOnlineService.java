package com.flybirds.system.sysUser.service;


import com.flybirds.common.core.model.UserAgentEntity;
import com.flybirds.system.sysUser.entity.SysUserOnline;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * 在线用户 服务层
 * 
 * @author ruoyi
 */
public interface ISysUserOnlineService
{
    /**
     * 通过登录地址查询信息
     *
     * @param ipaddr 登录地址
     * @param oAuth2AccessToken 用户信息
     * @return 在线用户信息
     */
     SysUserOnline selectOnlineByIpaddr(String ipaddr, OAuth2AccessToken oAuth2AccessToken, UserAgentEntity userAgentEntity);

    /**
     * 通过用户名称查询信息
     *
     * @param userName 用户名称
     * @param oAuth2AccessToken 用户信息
     * @return 在线用户信息
     */
     SysUserOnline selectOnlineByUserName(String userName, OAuth2AccessToken oAuth2AccessToken, String user_name);

    /**
     * 通过登录地址/用户名称查询信息
     *
     * @param ipaddr 登录地址
     * @param userName 用户名称
     * @param oAuth2AccessToken 用户信息
     * @return 在线用户信息
     */
     SysUserOnline selectOnlineByInfo(String ipaddr, String userName, OAuth2AccessToken oAuth2AccessToken, UserAgentEntity userAgentEntity, String user_name);

    /**
     * 设置在线用户信息
     *
     * @param oAuth2AccessToken 用户信息
     * @return 在线用户
     */
     SysUserOnline loginUserToUserOnline(OAuth2AccessToken oAuth2AccessToken);
}
