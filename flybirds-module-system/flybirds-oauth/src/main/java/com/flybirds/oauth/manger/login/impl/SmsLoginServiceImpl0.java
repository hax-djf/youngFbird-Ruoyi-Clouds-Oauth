package com.flybirds.oauth.manger.login.impl;

import com.flybirds.api.core.dto.LogininforRespDTO;
import com.flybirds.api.core.entity.SysUser;
import com.flybirds.api.model.UserInfo;
import com.flybirds.common.constant.CacheConstantEnum;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.core.model.SmsLoginReqVo0;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.common.util.ip.IpUtils;
import com.flybirds.common.util.ip.UserAgentUtils;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.servlet.ServletUtils;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.oauth.manger.login.UserLoginAdapter0;
import com.flybirds.security.oauth.OauthRedisManger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.flybirds.common.enums.login.AccountLoginChannelEnum.SMS;
import static com.flybirds.common.enums.login.AccountLoginStatusEnum.SMS_LOGIN_FAILURE;
import static com.flybirds.common.enums.login.AccountLoginStatusEnum.SMS_LOGIN_SUCCESS;
import static com.flybirds.common.exception.enums.SysErrorCodeConstants.*;

/**
 * login 手机短信验证码 实现
 *
 * @author: flybirds
 */
@Service(value = "SmsLoginServiceImpl0")
public class SmsLoginServiceImpl0 extends UserLoginAdapter0<SmsLoginReqVo0> {

    @Override
    public void vaild(SmsLoginReqVo0 body) {
        AssertUtil.isTrue(StringUtils.isAnyBlank(body.getMobile()),AUTH_LOGIN_MOBILE_NOT_NULL);
        AssertUtil.isTrue(body.getMobile().length() != Constant.User.PHONE_LENGTH
                || !StringUtils.isMobile(body.getMobile()),AUTH_LOGIN_MOBILE_FAIL);
        AssertUtil.isTrue(body.getMobileCode().length() != Constant.User.PHONE_CODE_LENGTH
                || StringUtils.matches(body.getMobileCode(),"^-?[0-9]+"),AUTH_LOGIN_CAPTCHA_CODE_FAIL);
        String code = body.getMobileCode();
        AssertUtil.isNotNull(code,AUTH_LOGIN_CAPTCHA_CODE_NOT_NULL);
        String codeQueryToRedis = OauthRedisManger.builder()
                .getObjectByKey(CacheConstantEnum.CAPTCHA_CODE_KEY_MOBILE.buildSuffix(body.getMobile()));
        AssertUtil.isNotNull(codeQueryToRedis,AUTH_LOGIN_CAPTCHA_CODE_NOT_FOUND);
        AssertUtil.isTrue(!code.equals(codeQueryToRedis),AUTH_LOGIN_CAPTCHA_CODE_ERROR);
    }

    @Override
    public AuthToken login0(SmsLoginReqVo0 body) {
        MultiValueMap<String,Object> formData = new LinkedMultiValueMap<>();
        formData.add(SecurityConstant.OAUTH_USERNAME,body.getMobile());
        return httpOauthInit(formData,body.getLoginChannel(),body.getClientId(),body.getClientSecret(),body.getGrantType());
    }

    @Override
    public UserDetails aroundUserDetails(String userName) {
        Result<UserInfo> result = remoteUserService.getUserInfoByMobile(userName);
        super.checkUserNotNull(result,userName);
        UserInfo userInfo= result.getData();
        SysUser sysUser0 = userInfo.getSysUser();
        sysUser0.setPassWord(passwordEncoder.encode(sysUser0.getPassWord())); //再加一次密
        return super.builder0(userInfo,userName,SMS);
    }

    @Override
    public Exception afterException(AuthToken authToken, SmsLoginReqVo0 body,Exception e) {

        //远程发送登录失败的日子记录
        sendRemoteLogininfo( LogininforRespDTO.builder()
                .userAgent(UserAgentUtils.getUserAgent(ServletUtils.getRequest()))
                .userIp(IpUtils.getIpAddr(ServletUtils.getRequest()))
                .username(body.getMobile())
                .message(e.getMessage())
                .status(String.valueOf(SMS_LOGIN_FAILURE.getValue()))
                .type("0")
                .accountLoginChannelEnum(SMS)
                .build());
        eventPushLoginTask(authToken,body.getMobile(),SMS,SMS_LOGIN_FAILURE);
        return e;
    }

    @Override
    public void afterCommitTask(AuthToken authToken) {
        eventPushLoginTask(authToken,authToken.getUser_name(),SMS,SMS_LOGIN_SUCCESS);


    }

}
