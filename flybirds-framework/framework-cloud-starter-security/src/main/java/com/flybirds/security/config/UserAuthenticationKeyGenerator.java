package com.flybirds.security.config;

import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * 自定义令牌创建规则生成器，作用在于实现单点登录操作
 *
 * @author :flybirds
 */
public class UserAuthenticationKeyGenerator extends DefaultAuthenticationKeyGenerator {

    private static final String CLIENT_ID = "client_id";

    private static final String SCOPE = "scope";

    private static final String USERNAME = "username";

    @Override
    public String extractKey(OAuth2Authentication authentication) {
        Map<String, String> values = new LinkedHashMap<String, String>();
        OAuth2Request authorizationRequest = authentication.getOAuth2Request();
        if (!authentication.isClientOnly())
        {
           //在用户名后面添加时间戳，使每次的key都不一样
            values.put(USERNAME, authentication.getName()+System.currentTimeMillis());
        }
        values.put(CLIENT_ID, authorizationRequest.getClientId());
        if (authorizationRequest.getScope() != null)
        {
            values.put(SCOPE, OAuth2Utils.formatParameterList(new TreeSet<String>(authorizationRequest.getScope())));
        }
        return generateKey(values);
    }

}
