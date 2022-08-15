package com.flybirds.oauth.factory;

import com.flybirds.common.enums.login.AccountLoginStatusEnum;
import com.flybirds.common.util.spring.SpringUtils;
import com.flybirds.oauth.manger.task.LoginTaskHandler0;
import org.springframework.stereotype.Component;

/**
 * LoginTaskFactory
 *
 * @author :flybirds
 */
@Component
public class LoginTaskFactory {


    public static LoginTaskHandler0 get0(AccountLoginStatusEnum status){
        return SpringUtils.getBean(status.getClassName());

    }
}
