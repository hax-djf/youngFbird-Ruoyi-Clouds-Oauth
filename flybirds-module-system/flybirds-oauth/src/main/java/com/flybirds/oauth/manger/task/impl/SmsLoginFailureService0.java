package com.flybirds.oauth.manger.task.impl;

import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * sms success 服务
 *
 * @author : flybirds
 */
@Service(value = "SmsLoginFailureService0")
public class SmsLoginFailureService0 extends LoginFailureManger0{

    private static final Logger log = LoggerFactory.getLogger(SmsLoginFailureService0.class);

    @Override
    public void submitTask(AuthToken authToken, AccountLoginChannelEnum channel) {
        super.submitTask(authToken,channel);
    }
}
