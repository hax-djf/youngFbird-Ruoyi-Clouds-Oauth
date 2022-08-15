package com.flybirds.oauth.controller;

import com.flybirds.common.core.model.AccountLoginReqVo0;
import com.flybirds.common.core.model.AuthToken;
import com.flybirds.common.core.model.SmsLoginReqVo0;
import com.flybirds.common.core.model.SocialLoginReqVo0;
import com.flybirds.common.exception.util.ServiceExceptionUtil;
import com.flybirds.common.util.result.Result;
import com.flybirds.oauth.annotation.LoginSSO;
import com.flybirds.oauth.manger.login.impl.AccountLoginServiceImpl0;
import com.flybirds.oauth.manger.login.impl.SmsLoginServiceImpl0;
import com.flybirds.oauth.manger.login.impl.SocialLoginServiceImpl0;
import com.flybirds.oauth.manger.login.impl.SocialLoginServiceImpl2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.flybirds.common.enums.login.AccountLoginChannelEnum.*;
import static com.flybirds.common.exception.enums.SysErrorCodeConstants.AUTH_THIRD_LOGIN_BIND_FAIL;

/**
 * 用户登录 - ouath2.0 授权
 *
 * @author flybirds
 */
@RestController
@RequestMapping("/token")
@Validated
@Slf4j
public class UserLoginController0 {


    @PostMapping("/login-password")
    @LoginSSO(SSOChannel = ACCOUNT_EMAIL_MOBILE)
    public Result<AuthToken> login(@RequestBody AccountLoginReqVo0 reqVo0,
                                   AccountLoginServiceImpl0 loginService0)
    {
        AuthToken authToken = loginService0.login0(reqVo0);
        return Result.ok(authToken.Target(),"账号，令牌生成成功");
    }

    @PostMapping("/login-mobile")
    @LoginSSO(SSOChannel = SMS)
    public Result<AuthToken> loginByMobile(@RequestBody SmsLoginReqVo0 reqVo0,
                                           SmsLoginServiceImpl0 loginService0)
    {
        AuthToken  authToken = loginService0.login0(reqVo0);
        return Result.ok(authToken.Target(),"手机号，令牌生成成功");
    }


    @PostMapping("/login-social")
    @LoginSSO(SSOChannel = SOCIAL)
    public Result<AuthToken> loginBySocial(@RequestBody @Valid SocialLoginReqVo0 reqVo0,
                                           SocialLoginServiceImpl0 loginService0)
    {
        AuthToken authToken = loginService0.login0(reqVo0);
        return Result.ok(authToken.Target(),"(已绑定)社交登录，令牌生成成功");
    }

    @PostMapping("/login-social2")
    @LoginSSO(SSOChannel = SOCIAL2)
    public Result<AuthToken> loginBySocial2(@RequestBody @Valid SocialLoginReqVo0 reqVO,
                                            SocialLoginServiceImpl2 loginService0)
    {
        AuthToken authToken = loginService0.login0(reqVO);
        Boolean aBoolean = loginService0.socialLoginBind2(reqVO, Long.valueOf(authToken.getUser_id()));
        if(!aBoolean){
            throw ServiceExceptionUtil.exception(AUTH_THIRD_LOGIN_BIND_FAIL);
        }
        return Result.ok(authToken.Target(),"(未绑定)社交登录，令牌生成成功");
    }


}
