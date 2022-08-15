package com.flybirds.oauth.factory;

import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.common.util.spring.SpringUtils;
import com.flybirds.oauth.exception.CustomOauthException;
import com.flybirds.oauth.manger.login.UserLoginHandler0;
import org.springframework.stereotype.Component;

/**
 * UserLoginFactory
 *
 * @author :flybirds
 */
@Component
public class UserLoginFactory {

    public static UserLoginHandler0 get0(AccountLoginChannelEnum channel){
        return SpringUtils.getBean(channel.getClassName());

    }

    public static UserLoginHandler0 get0(Integer value){
        AccountLoginChannelEnum channel = AccountLoginChannelEnum.get(value);
        AssertUtil.isNotNull(channel,new CustomOauthException("认证类型找不到 登录渠道 AccountLoginChannelEnum"));
        return SpringUtils.getBean(channel.getClassName());

    }
}
