package com.flybirds.oauth.controller;

import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.security.push.OutEventPush;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.flybirds.common.enums.login.AccountLoginOutChannelEnum.LOGIN_OUT;

/**
 * 用户登录 - 退出
 *
 * @author flybirds
 */
@RestController
@RequestMapping("/token")
@Slf4j
public class UserlogoutController0 {
    @Autowired
    private TokenStore tokenStore;

    @DeleteMapping("/logout")
    public Result<?> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader)
    {
        if (StringUtils.isEmpty(authHeader))
        {
            return Result.ok();
        }
        String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StringUtils.EMPTY).trim();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null || StringUtils.isEmpty(accessToken.getValue()))
        {
            return Result.ok();
        }
        //异步任务
        OutEventPush.EventpushUserExit(tokenValue,LOGIN_OUT);
        log.info("用户退出完成");
        return Result.ok("用户退出完成");
    }
}
