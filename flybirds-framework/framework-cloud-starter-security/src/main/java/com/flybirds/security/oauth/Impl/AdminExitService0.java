package com.flybirds.security.oauth.Impl;

import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.servlet.ServletUtils;
import com.flybirds.security.mange.LoginOutAdapter0;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.flybirds.common.enums.login.AccountLoginOutChannelEnum.ADMIN_EXIT;

/**
 * @author :flybirds
 */
@Service(value = "AdminExitService0")
public class AdminExitService0 extends LoginOutAdapter0{

    @Override
    public void userExit(String... tokenValue) {
        Result<OAuth2AccessToken> result = out(tokenValue[0]);
        OAuth2AccessToken accessToken = result.getData();
        Map<String, ?> map = accessToken.getAdditionalInformation();
        closeTokenKey(accessToken,tokenValue[0]);
        // 当前登录用户的系统信息
        String adminUserName = ServletUtils.getRequest().getHeader(SecurityConstant.DETAILS_USERNAME);
        sendRemoteLogInfor(map, adminUserName + ADMIN_EXIT.getLabel());
    }
}
