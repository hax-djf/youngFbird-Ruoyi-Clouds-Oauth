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

import java.util.Optional;

/**
 *  web 用户登录 scope 检测
 *
 * @author :flybirds
 */
@Service(value = "WebLoginScopeService0")
public class WebLoginScopeService0 extends LoginScopeAdapter0 {
    private Logger log = LoggerFactory.getLogger(LoginScopeAdapter0.class);

    @Override
    public void scopeDetect(String key,AuthToken authToken) {
        Integer onlineNumber = Optional.ofNullable(authToken.getOnline_number()).orElseGet(()->{
            return 1;
        });
        if(OauthRedisManger.builder().getCacheListSize(key) >= onlineNumber){
            //将所有的缓存数据全部清楚
            String tokenValue = OauthRedisManger.builder().getCacheListEnd(key);
            LoginOutHandler0 service = Oauth2OutFactory.get0(AccountLoginOutChannelEnum.USER_FORCEDOUT);
            service.userExit(tokenValue,authToken.getUser_agent(),authToken.getUser_ip());
        }
    }
}
