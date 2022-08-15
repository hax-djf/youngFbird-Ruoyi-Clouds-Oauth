package com.flybirds.system.sysUser.service.Impl;

import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.core.model.UserAgentEntity;
import com.flybirds.common.util.aes.AESUtil;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.security.service.UserTokenServiceManger;
import com.flybirds.system.sysUser.entity.SysUserOnline;
import com.flybirds.system.sysUser.service.ISysUserOnlineService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 在线用户 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysUserOnlineServiceImpl implements ISysUserOnlineService
{
    /**
     * 通过登录地址查询信息
     *
     * @param ipaddr 登录地址
     * @param oAuth2AccessToken 用户信息
     * @return 在线用户信息
     */
    @Override
    public SysUserOnline selectOnlineByIpaddr(String ipaddr, OAuth2AccessToken oAuth2AccessToken, UserAgentEntity userAgentEntity)
    {
        if (StringUtils.equals(ipaddr, userAgentEntity.getIpaddr()))
        {
            return loginUserToUserOnline(oAuth2AccessToken);
        }
        return null;
    }

    /**
     * 通过用户名称查询信息
     *
     * @param userName 用户名称
     * @param oAuth2AccessToken 用户信息
     * @return 在线用户信息
     */
    @Override
    public SysUserOnline selectOnlineByUserName(String userName, OAuth2AccessToken oAuth2AccessToken,String user_name)
    {
        if (StringUtils.equals(userName, user_name))
        {
            return loginUserToUserOnline(oAuth2AccessToken);
        }
        return null;
    }

    /**
     * 通过登录地址/用户名称查询信息
     *
     * @param ipaddr 登录地址
     * @param userName 用户名称
     * @param oAuth2AccessToken 用户信息
     * @return 在线用户信息
     */
    @Override
    public SysUserOnline selectOnlineByInfo(String ipaddr, String userName,OAuth2AccessToken oAuth2AccessToken,UserAgentEntity userAgentEntity,String user_name)
    {
        if (StringUtils.equals(ipaddr, userAgentEntity.getIpaddr()) && StringUtils.equals(userName, user_name))
        {
            return loginUserToUserOnline(oAuth2AccessToken);
        }
        return null;
    }

    /**
     * 设置在线用户信息
     *
     * @param oAuth2AccessToken 用户信息
     * @return 在线用户
     */
    @Override
    public SysUserOnline loginUserToUserOnline(OAuth2AccessToken oAuth2AccessToken)
    {
        if (StringUtils.isNull(oAuth2AccessToken)) {
            return null;
        }
        Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
        String userName = (String) additionalInformation.get(SecurityConstant.DETAILS_USERNAME);
        String oauthTokenSecretkey = (String) additionalInformation.get(SecurityConstant.OAUTH_TOKEN_SECRETKEY);
        UserAgentEntity userAgentEntity = UserTokenServiceManger.builderUerAgent(additionalInformation);
        SysUserOnline sysUserOnline = new SysUserOnline();
        //token加密
        sysUserOnline.setTokenSecretkey(AESUtil.encrypt(oauthTokenSecretkey,userName));
        sysUserOnline.setUserName(userName);
        sysUserOnline.setIpaddr(userAgentEntity.getIpaddr());
        sysUserOnline.setLoginTime(userAgentEntity.getLoginTime());
        sysUserOnline.setLoginLocation(userAgentEntity.getLoginLocation());
        sysUserOnline.setBrowser(userAgentEntity.getBrowser());
        sysUserOnline.setOs(userAgentEntity.getOs());
        sysUserOnline.setScope(oAuth2AccessToken.getScope().toString());
        sysUserOnline.setExpiresIn(oAuth2AccessToken.getExpiresIn());
        return sysUserOnline;
    }
}
