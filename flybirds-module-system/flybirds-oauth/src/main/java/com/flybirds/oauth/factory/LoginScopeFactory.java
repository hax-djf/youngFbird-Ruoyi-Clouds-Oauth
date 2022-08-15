package com.flybirds.oauth.factory;

import com.flybirds.common.enums.login.AccountLoginScopeEnum;
import com.flybirds.common.util.spring.SpringUtils;
import com.flybirds.oauth.manger.scope.LoginScopeHandler0;
import org.springframework.stereotype.Component;

/**
 * LoginTaskFactory
 *
 * @author :flybirds
 */
@Component
public class LoginScopeFactory {


    public static LoginScopeHandler0 get0(AccountLoginScopeEnum scope){
        return SpringUtils.getBean(scope.getClassName());

    }
}
