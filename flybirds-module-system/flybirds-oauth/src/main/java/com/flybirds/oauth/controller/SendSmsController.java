package com.flybirds.oauth.controller;

import com.flybirds.api.RemoteLogService;
import com.flybirds.api.core.dto.LogininforRespDTO;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.enums.login.AccountLoginStatusEnum;
import com.flybirds.common.exception.BaseException;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.common.util.ip.IpUtils;
import com.flybirds.common.util.ip.UserAgentUtils;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.servlet.ServletUtils;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.oauth.service.subjoin.LoginSubJoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.flybirds.common.enums.login.AccountLoginChannelEnum.SMS;


/**
 * 用户登录- sms验证码登录
 *
 * @author :flybirds
 */
@RestController
@RequestMapping("/token")
@Slf4j
@SuppressWarnings("all")
public class SendSmsController {

    @Autowired
    RemoteLogService remoteLogService;

    @Autowired
    LoginSubJoinService subJoinService;

    @GetMapping("/loginSmsCode/{mobile}")
    public Result<?> LoginSmsCodeByMobile(@PathVariable(value = "mobile") String mobile){
       try {
           AssertUtil.isNotNull(mobile,"用户名/密码必须填写");
           AssertUtil.isTrue(mobile.length() != Constant.User.PHONE_LENGTH
                   || !StringUtils.isMobile(mobile),"手机格式不正确");

          // 手机号是否注册过，先使用直接去用户信息中查找的方法，后续改成高级检索操作
           return subJoinService.sendLoginSmsCode(mobile);
       }catch (BaseException e){
           remoteLogService.saveLogininfor( LogininforRespDTO.builder()
                   .userAgent(UserAgentUtils.getUserAgent(ServletUtils.getRequest()))
                   .userIp(IpUtils.getIpAddr(ServletUtils.getRequest()))
                   .username(mobile)
                   .message("手机号码未填写非法请求")
                   .status(String.valueOf(AccountLoginStatusEnum.LOGIN_FAILURE.getValue()))
                   .type("0")
                   .accountLoginChannelEnum(SMS)
                   .build());
           throw e;
       }
    }

}
