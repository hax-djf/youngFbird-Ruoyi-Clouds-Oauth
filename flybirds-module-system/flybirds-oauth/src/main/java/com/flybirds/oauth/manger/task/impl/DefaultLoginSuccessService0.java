package com.flybirds.oauth.manger.task.impl;

import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 账号密码登录 success 服务
 *
 * @author : flybirds
 */
@Service(value = "DefaultLoginSuccessService0")
public class DefaultLoginSuccessService0 extends LoginSuccessManger0 {

    private static final Logger log = LoggerFactory.getLogger(DefaultLoginSuccessService0.class);

    @Override
    public void submitTask(AuthToken authToken, AccountLoginChannelEnum channel) {
        super.submitTask(authToken,channel);
    }
}
