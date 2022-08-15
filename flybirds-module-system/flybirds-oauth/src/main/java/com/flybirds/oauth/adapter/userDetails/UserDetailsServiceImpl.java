package com.flybirds.oauth.adapter.userDetails;

import cn.hutool.core.util.StrUtil;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.common.util.servlet.ServletUtils;
import com.flybirds.oauth.exception.CustomOauthException;
import com.flybirds.oauth.factory.UserLoginFactory;
import com.flybirds.oauth.manger.login.UserLoginHandler0;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 *  覆写 loadUserByUsername
 *
 * @author: flybirds
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StrUtil.isBlank(username)) {
            return null;
        }
        String channel = ServletUtils.getRequest().getParameter(AccountLoginChannelEnum.ACCOUNT_LOGIN_CHANNEL);
        AssertUtil.isNotNull(channel,new CustomOauthException("AccountLoginChannel Is Null To System Error，请联系管理员！"));
        UserLoginHandler0 service = UserLoginFactory.get0(Integer.valueOf(channel));
        return service.aroundUserDetails(username);
    }
}