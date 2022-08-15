package com.flybirds.oauth.manger.task.impl;

import com.flybirds.common.constant.CacheConstantEnum;
import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import com.flybirds.security.oauth.OauthRedisManger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * sms success 服务
 *
 * @author : flybirds
 */
@Service(value = "SmsLoginSuccessService0")
public class SmsLoginSuccessService0 extends LoginSuccessManger0{

    private static final Logger log = LoggerFactory.getLogger(SmsLoginSuccessService0.class);

    @Override
    public void submitTask(AuthToken authToken, AccountLoginChannelEnum channel) {
        super.submitTask(authToken,channel);
        removeMolieCodeAndListByMobile(authToken.getMobile());
        log.info("[ {} ] 任务提交完成验证码数据清理 用户 {} 手机号 {}",channel.getLabel(),authToken.getUser_name(),authToken.getMobile());

    }

    /**
     * 验证码清理
     *
     * @param mobile
     */
    private void removeMolieCodeAndListByMobile(String mobile) {
        OauthRedisManger.builder().delKey(CacheConstantEnum.CAPTCHA_CODE_KEY_MOBILE.buildSuffix(mobile)); //清除验证码消息
        OauthRedisManger.builder().delKey(CacheConstantEnum.CAPTCHA_CODE_KEY_MOBILE_NUMBER.buildSuffix(mobile)); //  清除验证发送的次数
    }
}
