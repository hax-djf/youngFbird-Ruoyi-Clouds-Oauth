package com.flybirds.security.oauth.Impl;

import com.flybirds.common.core.model.UserAgentEntity;
import com.flybirds.common.util.result.Result;
import com.flybirds.security.mange.LoginOutAdapter0;
import com.flybirds.security.service.UserTokenServiceManger;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.flybirds.common.enums.login.AccountLoginOutChannelEnum.USER_FORCEDOUT;

/**
 * @author :flybirds
 */
@Service(value = "UserForceDoutService0")
public class UserForceDoutService0 extends LoginOutAdapter0 {

    @Override
    public void userExit(String... tokenValue) {
        Result<OAuth2AccessToken> result = out(tokenValue[0]);
        OAuth2AccessToken accessToken = result.getData();
        Map<String, ?> map = accessToken.getAdditionalInformation();
        closeTokenKey(accessToken, tokenValue[0]);
        // 当前登录用户的系统信息
        UserAgentEntity agent = UserTokenServiceManger.builderUerAgent(tokenValue[1], tokenValue[2]);
        sendRemoteLogInfor(map,String.format("[ %s ] 账号在%s,操作系统为 %s,ip地址 %s ",
                USER_FORCEDOUT.getLabel(),
                agent.getLoginLocation(),
                agent.getBrowser(),
                agent.getIpaddr())
        );
    }
}
