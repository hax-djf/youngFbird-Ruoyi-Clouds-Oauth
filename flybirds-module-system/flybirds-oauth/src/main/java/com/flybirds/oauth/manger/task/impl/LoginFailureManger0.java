package com.flybirds.oauth.manger.task.impl;

import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import com.flybirds.common.enums.login.AccountLoginOutChannelEnum;
import com.flybirds.oauth.manger.task.LoginTaskAdapter0;
import com.flybirds.security.factory.Oauth2OutFactory;
import com.flybirds.security.mange.LoginOutHandler0;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * login failure manger
 *
 * @author :flybrids
 */
public class LoginFailureManger0 extends LoginTaskAdapter0 {

    private static final Logger log = LoggerFactory.getLogger(LoginFailureManger0.class);

    @Override
    public void submitTask(AuthToken authToken, AccountLoginChannelEnum channel) {
        LoginOutHandler0 service = Oauth2OutFactory.get0(AccountLoginOutChannelEnum.LOGIN_OUT);
        service.userExit(authToken.getAccessToken());
        log.info("[ {} ] 渠道登录失败提交任务",channel.getLabel());
    }

}
