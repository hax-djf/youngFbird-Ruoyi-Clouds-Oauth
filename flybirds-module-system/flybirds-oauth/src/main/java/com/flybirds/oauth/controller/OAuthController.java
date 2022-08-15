package com.flybirds.oauth.controller;


import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.core.model.AuthToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;
import java.util.stream.Collectors;

/* OAuth2控制器 重写/oauth/token 自定义接口数据 废除 */

@Deprecated
@RestController
//@RequestMapping("/oauth2")
@Slf4j
public class OAuthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @PostMapping("/token")
    public ResponseEntity postAccessToken(Principal principal, @RequestParam Map<String,String> parameters) throws HttpRequestMethodNotSupportedException {
        return customAccessToken(tokenEndpoint.postAccessToken(principal, parameters).getBody());
    }

    /* 自定义Token返回对象 */
    ResponseEntity customAccessToken(OAuth2AccessToken accessToken){
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        AuthToken authToken = AuthToken
                .builder()
                .accessToken(token.getValue())
                .expires_in(token.getExpiresIn())
                .scope(token.getScope().stream().collect(Collectors.joining(" ")))
                .user_name((String) token.getAdditionalInformation().get(SecurityConstant.DETAILS_USERNAME))
                .user_id((Long) token.getAdditionalInformation().get(SecurityConstant.DETAILS_USER_ID))
                .user_agent((String)token.getAdditionalInformation().getOrDefault(SecurityConstant.OAUTH_USER_AGENT,"用户登录地址数据读取失败"))
                .user_agent((String)token.getAdditionalInformation().getOrDefault(SecurityConstant.OAUTH_USER_IP,"用户登录ip数据读取失败"))
                .online_number((Integer) token.getAdditionalInformation().get(SecurityConstant.DETAILS_ONLINE_NUMBER))
                .refreshToken(token.getRefreshToken() != null ? token.getRefreshToken().getValue() : null)
                .build();
        return ResponseEntity.ok(authToken);
    }

}
