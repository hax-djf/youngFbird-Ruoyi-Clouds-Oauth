package com.flybirds.oauth.manger.login.impl;

import com.flybirds.api.core.dto.LogininforRespDTO;
import com.flybirds.api.model.UserInfo;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.core.model.AccountLoginReqVo0;
import com.flybirds.common.core.model.AuthToken;
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

import static com.flybirds.common.enums.login.AccountLoginChannelEnum.ACCOUNT_EMAIL_MOBILE;
import static com.flybirds.common.enums.login.AccountLoginStatusEnum.ACCOUNT_LOGIN_FAILURE;
import static com.flybirds.common.enums.login.AccountLoginStatusEnum.ACCOUNT_LOGIN_SUCCESS;
import static com.flybirds.common.exception.enums.SysErrorCodeConstants.*;

/**
 * login Account 账号密码 实现
 *
 * @author: flybirds
 */
@Service(value = "AccountLoginServiceImpl0")
public class AccountLoginServiceImpl0 extends UserLoginAdapter0<AccountLoginReqVo0> {



    @Override
    public void vaild(AccountLoginReqVo0 body) {
        AssertUtil.isTrue(StringUtils.isAnyBlank(body.getUsername(),
                body.getPassword()),AUTH_LOGIN_USER_PASS_NOT_NULL);
        AssertUtil.isTrue(body.getPassword().length() < Constant.User.PASSWORD_MIN_LENGTH
                || body.getPassword().length() > Constant.User.PASSWORD_MAX_LENGTH,AUTH_LOGIN_PASS_NOT_SCOPE);
        AssertUtil.isTrue(body.getUsername().length() < Constant.User.USERNAME_MIN_LENGTH
                || body.getUsername().length() > Constant.User.USERNAME_MAX_LENGTH,AUTH_LOGIN_USER_NOT_SCOPE);
    }

    @Override
    public AuthToken login0(AccountLoginReqVo0 body) {
        MultiValueMap<String,Object> formData = new LinkedMultiValueMap<>(); //构建fromData
        formData.add(SecurityConstant.OAUTH_USERNAME,body.getUsername());
        formData.add(SecurityConstant.OAUTH_PASSWORD,body.getPassword());
        return httpOauthInit(formData,body.getLoginChannel(),body.getClientId(),body.getClientSecret(),body.getGrantType());
    }

    @Override
    public UserDetails aroundUserDetails(String userName) {
        Result<UserInfo> result = null;
        if (StringUtils.isEmail(userName)) {
            result = remoteUserService.getUserInfoByEmail(userName);
        } else if (StringUtils.isMobile(userName)) {
            result = remoteUserService.getUserInfoByMobile(userName);
        } else {
            result = remoteUserService.getUserInfo(userName);
        }
        super.checkUserNotNull(result, userName);
        return super.builder0(result.getData(), userName, ACCOUNT_EMAIL_MOBILE);
    }

    @Override
    public Exception afterException(AuthToken authToken, AccountLoginReqVo0 body, Exception e) {

        sendRemoteLogininfo(LogininforRespDTO.builder()
                .userAgent(UserAgentUtils.getUserAgent(ServletUtils.getRequest()))
                .userIp(IpUtils.getIpAddr(ServletUtils.getRequest()))
                .username(body.getUsername())
                .message(e.getMessage())
                .status(String.valueOf(ACCOUNT_LOGIN_FAILURE.getValue()))
                .type("0")
                .accountLoginChannelEnum(ACCOUNT_EMAIL_MOBILE)
                .build());

        eventPushLoginTask(authToken,body.getUsername(),ACCOUNT_EMAIL_MOBILE,ACCOUNT_LOGIN_FAILURE);
        return e;
    }

    @Override
    public void afterCommitTask(AuthToken authToken) {
        eventPushLoginTask(authToken,authToken.getUser_name(),ACCOUNT_EMAIL_MOBILE,ACCOUNT_LOGIN_SUCCESS);
    }

}
