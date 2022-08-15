package com.flybirds.oauth.manger.login.impl;

import com.flybirds.api.core.dto.LogininforRespDTO;
import com.flybirds.api.model.UserInfo;
import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.core.model.SocialLoginReqVo0;
import com.flybirds.common.enums.login.AccountLoginGrantType;
import com.flybirds.common.enums.login.AccountLoginStatusEnum;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.common.util.ip.IpUtils;
import com.flybirds.common.util.ip.UserAgentUtils;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.servlet.ServletUtils;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.oauth.manger.login.UserLoginAdapter0;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.flybirds.common.enums.login.AccountLoginChannelEnum.SOCIAL2;
import static com.flybirds.common.enums.login.AccountLoginStatusEnum.LOGIN_FAILURE;
import static com.flybirds.common.enums.login.AccountLoginStatusEnum.LOGIN_SUCCESS;
import static com.flybirds.common.exception.enums.SysErrorCodeConstants.AUTH_LOGIN_SOCIAL_CODE_NOT_NULL;
import static com.flybirds.common.exception.enums.SysErrorCodeConstants.AUTH_LOGIN_SOCIAL_STATE_NOT_NULL;
import static com.flybirds.common.exception.enums.SysErrorCodeConstants.AUTH_LOGIN_SOCIAL_TYPE_NOT_NULL;

/**
 * login  第三方验证登录 实现
 *
 * @author: flybirds
 */
@Service(value = "SocialLoginServiceImpl2")
public class SocialLoginServiceImpl2 extends UserLoginAdapter0<SocialLoginReqVo0> {

    @Override
    public void vaild(SocialLoginReqVo0 body) {
        AssertUtil.isNotNull(body.getCode(),AUTH_LOGIN_SOCIAL_CODE_NOT_NULL);
        AssertUtil.isNotNull(body.getState(),AUTH_LOGIN_SOCIAL_STATE_NOT_NULL);
        AssertUtil.isNotNull(body.getType(),AUTH_LOGIN_SOCIAL_TYPE_NOT_NULL);
        body.setGrantType(AccountLoginGrantType.OAUTH2_PASSWORD_CONSTANT);
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
        Result<UserInfo> result = null;
        if(StringUtils.isEmail(userName)){
            result = remoteUserService.getUserInfoByEmail(userName);
        }else if(StringUtils.isMobile(userName)){
            result = remoteUserService.getUserInfoByMobile(userName);
        }else{
            result = remoteUserService.getUserInfo(userName);
        }
        super.checkUserNotNull(result,userName);
        UserInfo userInfo = result.getData();
        return super.builder0(userInfo,userName,SOCIAL2);
    }

    @Override
    public Exception afterException(AuthToken authToken, SocialLoginReqVo0 body,Exception e) {

        //远程发送登录失败的日子记录
        sendRemoteLogininfo(LogininforRespDTO.builder()
                .userAgent(UserAgentUtils.getUserAgent(ServletUtils.getRequest()))
                .userIp(IpUtils.getIpAddr(ServletUtils.getRequest()))
                .username(body.getUserName())
                .message(e.getMessage())
                .status(String.valueOf(AccountLoginStatusEnum.LOGIN_FAILURE.getValue()))
                .type("0")
                .accountLoginChannelEnum(SOCIAL2)
                .build());
        eventPushLoginTask(authToken,body.getUserName(),SOCIAL2,LOGIN_FAILURE);
        return e;
    }

    @Override
    public void afterCommitTask(AuthToken authToken) {
        eventPushLoginTask(authToken,authToken.getUser_name(),SOCIAL2,LOGIN_SUCCESS);
    }

    /* 社交账号&用户绑定 */
    public boolean socialLoginBind2(SocialLoginReqVo0 body,Long userId){
        Result<Boolean> result = remoteSocialService.socialLoginBind2(body, userId);
        return result.getData();
    }


}
