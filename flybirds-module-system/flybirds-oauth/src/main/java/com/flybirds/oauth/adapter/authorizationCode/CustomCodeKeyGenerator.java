package com.flybirds.oauth.adapter.authorizationCode;

import com.flybirds.common.util.idtool.IdUtils;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;

/**
 * 自定义 state and code 生成器
 *
 * @author :flybirds
 */
public class CustomCodeKeyGenerator extends RandomValueStringGenerator {
    public CustomCodeKeyGenerator() {
        this(64);
    }

    public CustomCodeKeyGenerator(int length) {
        super(length);
    }

    @Override
    public String generate() {
        return super.generate();
    }

    public static String generateState(){
        return IdUtils.simpleUUID();
    }
}
