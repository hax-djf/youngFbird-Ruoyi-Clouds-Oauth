package com.flybirds.security.oauth.Impl;

import com.flybirds.common.util.result.Result;
import com.flybirds.security.mange.LoginOutAdapter0;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.flybirds.common.enums.login.AccountLoginOutChannelEnum.LOGIN_OUT;

/**
 * @author :flybirds
 */
@Service(value = "LoginOutService0")
public class LoginOutService0 extends LoginOutAdapter0 {

    @Override
    public void userExit(String... tokenValue) {
        Result<OAuth2AccessToken> result = out(tokenValue[0]);
        OAuth2AccessToken accessToken = result.getData();
        Map<String, ?> map = accessToken.getAdditionalInformation();
        closeTokenKey(accessToken, tokenValue[0]);
        sendRemoteLogInfor(map, LOGIN_OUT.getLabel());
    }
}
