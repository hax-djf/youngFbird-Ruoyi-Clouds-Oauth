package com.flybirds.oauth.adapter.granter;

import com.flybirds.common.util.spring.SpringUtils;
import com.flybirds.oauth.core.Oauth2RemoteUserService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.flybirds.common.constant.SecurityConstant.OAUTH_PASSWORD;
import static com.flybirds.common.constant.SecurityConstant.OAUTH_USERNAME;


/**
 * 短信验证码登录,验证码的管理，对密码授权模式进行改造ResourceOwnerPasswordTokenGranter
 *
 * @author :flybirds
 */
public class ResourceOwnerSmscodeTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "sms";

    private final AuthenticationManager authenticationManager;

    public Oauth2RemoteUserService oauth2RemoteUserService = SpringUtils.getBean("Oauth2RemoteUserService");

    public ResourceOwnerSmscodeTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    protected ResourceOwnerSmscodeTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
    }

    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
        // 替换账号密码的操作，将账号替换为手机号，免去在加载的时候，再去进行手机号查询操作
        oauth2RemoteUserService.formatMobileTopassword(parameters);
        String username = parameters.get(OAUTH_USERNAME);
        String password = parameters.get(OAUTH_PASSWORD);
        parameters.remove(OAUTH_PASSWORD);

        Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);

        ((AbstractAuthenticationToken)userAuth).setDetails(parameters);
        try {
            userAuth = this.authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException var8) {
            throw new InvalidGrantException(var8.getMessage());
        } catch (BadCredentialsException var9) {
            throw new InvalidGrantException(var9.getMessage());
        }

        if(userAuth != null && userAuth.isAuthenticated()) {
            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, userAuth);
        } else {
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }
    }

}
