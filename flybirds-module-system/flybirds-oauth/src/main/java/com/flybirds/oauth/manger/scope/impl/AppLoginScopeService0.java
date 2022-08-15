package com.flybirds.oauth.manger.scope.impl;

import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.enums.login.AccountLoginOutChannelEnum;
import com.flybirds.oauth.manger.scope.LoginScopeAdapter0;
import com.flybirds.security.factory.Oauth2OutFactory;
import com.flybirds.security.mange.LoginOutHandler0;
import com.flybirds.security.oauth.OauthRedisManger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *  app 用户登录 scope 检测
 *
 * @author :flybirds
 */
@Service(value = "AppLoginScopeService0")
public class AppLoginScopeService0 extends LoginScopeAdapter0{
    private Logger log = LoggerFactory.getLogger(LoginScopeAdapter0.class);

    @Override
    public void scopeDetect(String key, AuthToken authToken) {
        // todo 这里需要根据用户设置的同时允许几个用户在线进行是否剔除已经登录的用户->默认这里只会存在一个数据，获取删除
        String tokenValue = OauthRedisManger.builder().getCacheListEnd(key);
        LoginOutHandler0 service = Oauth2OutFactory.get0(AccountLoginOutChannelEnum.USER_FORCEDOUT);
        service.userExit(tokenValue,authToken.getUser_agent(),authToken.getUser_ip());
    }
}
