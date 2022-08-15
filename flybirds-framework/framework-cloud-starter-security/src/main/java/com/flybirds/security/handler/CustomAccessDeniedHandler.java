package com.flybirds.security.handler;

import com.alibaba.fastjson.JSON;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.servlet.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.flybirds.common.exception.enums.SysErrorCodeConstants.AUTH_NOT_PERMISSIONS;

/**
 * 自定义访问无权限资源时的异常
 *
 * @author flybirds
 */
@Component
public class CustomAccessDeniedHandler extends OAuth2AccessDeniedHandler
{
    private final Logger log = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException)
    {
        log.info("权限不足，地址 {}", request.getRequestURI());
        log.info("权限不足，异常信息 {}", authException.getMessage());
        ServletUtils.renderString(response, JSON.toJSONString(Result.fail(AUTH_NOT_PERMISSIONS)));
    }
}
