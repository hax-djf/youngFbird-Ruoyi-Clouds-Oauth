package com.flybirds.oauth.handler;

import com.flybirds.api.RemoteLogService;
import com.flybirds.api.core.dto.LogininforRespDTO;
import com.flybirds.common.enums.login.AccountLoginChannelEnum;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.security.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static com.flybirds.common.enums.login.AccountLoginStatusEnum.LOGIN_SUCCESS;

/**
 * 认证成功事件处理
 *
 * @author :flybirds
 */
@Component
@SuppressWarnings("all")
public class WebAuthenticationSuccessEventHandler implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private RemoteLogService remoteLogService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

        Authentication authentication = (Authentication) event.getSource();
        if (StringUtils.isNotEmpty(authentication.getAuthorities())
                && authentication.getPrincipal() instanceof LoginUser) {
            LoginUser user = (LoginUser) authentication.getPrincipal();
            String username = user.getUsername();
            AccountLoginChannelEnum lastLoginChannel = user.getSysUser().getLastLoginChannel();
            remoteLogService.saveLogininfor(LogininforRespDTO.builder()
                    .userAgent(user.getUserAgent())
                    .userIp(user.getUserIp())
                    .username(username)
                    .message(LOGIN_SUCCESS.getLabel())
                    .status(String.valueOf(LOGIN_SUCCESS.getValue()))
                    .type("0")
                    .accountLoginChannelEnum(lastLoginChannel)
                    .build());
        }
    }
}
