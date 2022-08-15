package com.flybirds.oauth.service.subjoin.impl;

import com.flybirds.api.RemoteSmsService;
import com.flybirds.api.RemoteUserService;
import com.flybirds.api.core.dto.SysSmsSendMessageDto;
import com.flybirds.api.core.enums.SmsSendMessageTypeEnum;
import com.flybirds.common.constant.CodeConstant;
import com.flybirds.common.util.result.Result;
import com.flybirds.oauth.service.subjoin.LoginSubJoinService;
import com.flybirds.security.service.UserTokenServiceManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * login serviceimp
 *
 * @author: flybirds
 */
@Service
@SuppressWarnings("all")
public class LoginSubJoinServiceImpl implements LoginSubJoinService {


    @Autowired
    UserTokenServiceManger userTokenService;

    @Autowired
    RemoteUserService remoteUserService;

    @Autowired
    RemoteSmsService remoteSmsService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<?> sendLoginSmsCode(String mobile) {
        Result<String> result = remoteUserService.checkPhoneValid(mobile);
        if(CodeConstant.Number.SUCCESS == result.getCode()){
            //校验手机号是否存已经连续登录在

            switch (userTokenService.checkLoginStatusByMobile(mobile)){

                case NORMAL:
                    result.setMsg("获取验证码成功，验证码已发送！");
                    //请求sms服务发送短信
                    remoteSmsService.sendSmsMessage(new SysSmsSendMessageDto()
                                            .buildSmsMessage(mobile, SmsSendMessageTypeEnum.LOGIN_SYSTEM_MOBILE));
                    break;
                case NO_FAILURE:
                    result.setMsg("验证码已发送，未失效！");
                    result.setCode(CodeConstant.Number.FAILURE);
                case FREQUENT_OPERATION:
                    result.setMsg("验证码发送频繁，请在10分钟操作！");
                    result.setCode(CodeConstant.Number.FAILURE);
            }
        }
        return result;
    }

}
