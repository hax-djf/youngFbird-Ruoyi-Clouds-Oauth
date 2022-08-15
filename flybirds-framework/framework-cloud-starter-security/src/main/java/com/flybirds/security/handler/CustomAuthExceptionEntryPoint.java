package com.flybirds.security.handler;

import com.alibaba.fastjson.JSON;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.servlet.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.flybirds.common.exception.enums.SysErrorCodeConstants.AUTH_TOKEN_EXPIRED;

/**
 * 无效token异常处理类
 *
 * @author :flybirds
 */
@Component
public class CustomAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(CustomAuthExceptionEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException
    {
        log.info(AUTH_TOKEN_EXPIRED.getMsg() + "{}", request.getRequestURI());
        Throwable cause = authException.getCause();
        ServletUtils.renderString(response, JSON.toJSONString(Result.fail(AUTH_TOKEN_EXPIRED)));
    }
}
