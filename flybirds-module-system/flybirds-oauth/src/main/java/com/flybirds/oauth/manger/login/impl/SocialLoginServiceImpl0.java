package com.flybirds.oauth.manger.login.impl;

import com.flybirds.api.core.dto.LogininforRespDTO;
import com.flybirds.api.core.entity.SysUser;
import com.flybirds.api.model.UserInfo;
import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.core.model.SocialLoginReqVo0;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.common.util.core.KeyValue;
import com.flybirds.common.util.ip.IpUtils;
import com.flybirds.common.util.ip.UserAgentUtils;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.servlet.ServletUtils;
import com.flybirds.oauth.manger.login.UserLoginAdapter0;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

import static com.flybirds.common.enums.login.AccountLoginChannelEnum.SOCIAL;
import static com.flybirds.common.enums.login.AccountLoginStatusEnum.LOGIN_FAILURE;
import static com.flybirds.common.enums.login.AccountLoginStatusEnum.LOGIN_SUCCESS;
import static com.flybirds.common.exception.enums.SysErrorCodeConstants.*;

/**
 * login  第三方验证登录 实现
 *
 * @author: flybirds
 */
@Service(value = "SocialLoginServiceImpl0")
public class SocialLoginServiceImpl0 extends UserLoginAdapter0<SocialLoginReqVo0> {


    @Override
    public void vaild(SocialLoginReqVo0 body) {
        AssertUtil.isNotNull(body.getCode(),AUTH_LOGIN_SOCIAL_CODE_NOT_NULL);
        AssertUtil.isNotNull(body.getState(),AUTH_LOGIN_SOCIAL_STATE_NOT_NULL);
        AssertUtil.isNotNull(body.getType(),AUTH_LOGIN_SOCIAL_TYPE_NOT_NULL);
        Result<List<KeyValue<String,String>>> result = remoteSocialService.socialLogin(body);
        AssertUtil.isTrue(result.unSuccess(),result.getMsg());
        List<KeyValue<String,String>> data = result.getData();
        body.setUserName(data.get(0).getValue());
        body.setPassWord(data.get(1).getValue());
    }

    @Override
    public AuthToken login0(SocialLoginReqVo0 body) {

        MultiValueMap<String,Object> formData = new LinkedMultiValueMap<>(); //构建fromData
        formData.add(SecurityConstant.OAUTH_USERNAME,body.getUserName());
        formData.add(SecurityConstant.OAUTH_PASSWORD,body.getPassWord());
        return httpOauthInit(formData,body.getLoginChannel(),body.getClientId(),body.getClientSecret(),body.getGrantType());
    }

    @Override
    public UserDetails aroundUserDetails(String userName) {
        Result<UserInfo> loginUserResult = remoteUserService.getUserInfo(userName);
        checkUserNotNull(loginUserResult,userName);
        UserInfo userInfo = loginUserResult.getData();
        SysUser sysUser0 = userInfo.getSysUser();
        sysUser0.setPassWord(passwordEncoder.encode(sysUser0.getPassWord())); ////再加一次密
        return super.builder0(userInfo,userName,SOCIAL);
    }

    @Override
    public Exception afterException(AuthToken authToken, SocialLoginReqVo0 body,Exception e) {

        //远程发送登录失败的日子记录
        sendRemoteLogininfo( LogininforRespDTO.builder()
                .userAgent(UserAgentUtils.getUserAgent(ServletUtils.getRequest()))
                .userIp(IpUtils.getIpAddr(ServletUtils.getRequest()))
                .username(Optional.ofNullable(body.getUserName()).orElseGet(()->{
                   return  "授权码code 类型 {}" + body.getType();
                }))
                .message(e.getMessage())
                .status(String.valueOf(LOGIN_FAILURE.getValue()))
                .type("0")
                .accountLoginChannelEnum(SOCIAL)
                .build());
        log.info("授权码code ={}",body.getCode());
        eventPushLoginTask(authToken,body.getUserName(),SOCIAL,LOGIN_FAILURE);
        return e;
    }

    @Override
    public void afterCommitTask(AuthToken authToken) {
        eventPushLoginTask(authToken,authToken.getUser_name(),SOCIAL,LOGIN_SUCCESS);
    }



}
