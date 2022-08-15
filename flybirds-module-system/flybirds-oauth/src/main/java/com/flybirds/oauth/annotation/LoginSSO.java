package com.flybirds.oauth.annotation;


import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import java.lang.annotation.*;

import static com.flybirds.common.enums.login.AccountLoginChannelEnum.ACCOUNT_EMAIL_MOBILE;

/**
 * 自定义登录处理器机制切面
 * 
 * @author flybirds
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginSSO {

    /**
     * 登录方式
     */
    public AccountLoginChannelEnum SSOChannel() default ACCOUNT_EMAIL_MOBILE;

}
