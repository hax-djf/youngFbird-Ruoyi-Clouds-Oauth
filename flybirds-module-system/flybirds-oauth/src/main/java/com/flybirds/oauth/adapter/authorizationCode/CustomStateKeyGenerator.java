package com.flybirds.oauth.adapter.authorizationCode;

import org.springframework.security.oauth2.client.filter.state.StateKeyGenerator;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;

/**
 * @author :flybirds
 */
public class CustomStateKeyGenerator implements StateKeyGenerator {

    private RandomValueStringGenerator generator = new RandomValueStringGenerator(32);

    public CustomStateKeyGenerator() {
    }

    @Override
    public String generateKey(OAuth2ProtectedResourceDetails resource) {
        return this.generator.generate();
    }
}
