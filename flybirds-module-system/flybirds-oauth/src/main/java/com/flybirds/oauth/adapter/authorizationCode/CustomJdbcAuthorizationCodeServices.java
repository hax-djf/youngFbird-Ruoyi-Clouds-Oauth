package com.flybirds.oauth.adapter.authorizationCode;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;

import javax.sql.DataSource;

/**
 * 自定义生成 state and code码 基于{@link JdbcAuthorizationCodeServices}
 * 如果需要去基于Redis的操作，是要实现来进适配 {@link org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices}
 *
 * @author :flybirds
 */
public class CustomJdbcAuthorizationCodeServices extends JdbcAuthorizationCodeServices{

    public CustomJdbcAuthorizationCodeServices(DataSource dataSource) {
        super(dataSource);
    }
    private CustomCodeKeyGenerator generator = new CustomCodeKeyGenerator();

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = this.generator.generate();
        this.store(code, authentication);
        return code;
    }

}
