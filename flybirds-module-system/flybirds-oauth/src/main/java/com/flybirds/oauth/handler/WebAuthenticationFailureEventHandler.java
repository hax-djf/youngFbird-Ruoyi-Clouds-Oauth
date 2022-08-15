package com.flybirds.oauth.handler;

import com.flybirds.api.RemoteLogService;
import com.flybirds.api.core.dto.LogininforRespDTO;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.security.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureExpiredEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static com.flybirds.common.enums.login.AccountLoginStatusEnum.LOGIN_FAILURE;

/**
 * 认证失败事件处理
 *
 * @author :flybirds
 */
@Component
@SuppressWarnings("all")
public class WebAuthenticationFailureEventHandler implements ApplicationListener<AuthenticationFailureExpiredEvent> {

    @Autowired
    private RemoteLogService remoteLogService;

    @Override
    public void onApplicationEvent(AuthenticationFailureExpiredEvent event) {


        Authentication authentication = (Authentication) event.getSource();

        if (StringUtils.isNotEmpty(authentication.getAuthorities())
                && authentication.getPrincipal() instanceof LoginUser) {
            LoginUser user = (LoginUser) authentication.getPrincipal();
            String username = user.getUsername();
            AccountLoginChannelEnum lastLoginChannel = user.getSysUser().getLastLoginChannel();
            remoteLogService.saveLogininfor( LogininforRespDTO.builder()
                    .userAgent(user.getUserAgent())
                    .userIp(user.getUserIp())
                    .username(username)
                    .message(LOGIN_FAILURE.getLabel())
                    .status(String.valueOf(LOGIN_FAILURE.getValue()))
                    .type("0")
                    .accountLoginChannelEnum(lastLoginChannel)
                    .build());
        }
    }
}
