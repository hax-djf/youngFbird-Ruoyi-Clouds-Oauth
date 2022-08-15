package com.flybirds.security.factory;

import com.flybirds.common.enums.login.AccountLoginOutChannelEnum;
import com.flybirds.common.util.spring.SpringUtils;
import com.flybirds.security.mange.LoginOutHandler0;
import org.springframework.stereotype.Component;

/**
 * Oauth2OutFactory
 *
 * @author :flybirds
 */
@Component
public class Oauth2OutFactory {

    public static LoginOutHandler0 get0(AccountLoginOutChannelEnum channel){
        return SpringUtils.getBean(channel.getClassName());

    }
}
