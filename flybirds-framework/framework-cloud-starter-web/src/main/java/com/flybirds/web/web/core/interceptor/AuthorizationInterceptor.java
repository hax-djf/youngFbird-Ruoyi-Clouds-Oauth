package com.flybirds.web.web.core.interceptor;

import cn.hutool.core.util.StrUtil;
import com.flybirds.common.util.assertions.AssertUtil;
import com.flybirds.web.web.annotation.Login;
import com.flybirds.web.web.core.util.jwt.JwtUtils;
import com.flybirds.web.web.core.util.user.UserIdUtil;
import com.flybirds.web.web.core.util.vo.FlybirdsClaims;
import com.flybirds.web.web.exception.AuthorizationException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qing
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    public static final String USER_KEY = "user_id";

    private final JwtUtils jwtUtils;

    public AuthorizationInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        } else {
            return true;
        }
        if (annotation == null) {
            return true;
        }
        //获取用户凭证
        String token = request.getHeader(jwtUtils.getHeader());

        token = StrUtil.isBlank(token) ? request.getParameter(jwtUtils.getHeader()) : token;

        //凭证为空
        AssertUtil.isNotNull(token,new AuthorizationException(jwtUtils.getHeader() + "不能为空"));

        FlybirdsClaims claims = jwtUtils.getClaimByToken(token);

        //todo 这里存在数据问题
//        if (claims == null || jwtUtils.isTokenExpired( Long.valueOf(String.valueOf(claims.get(FlybirdsClaims.EXPIRATION))))) {
//            throw new AuthorizationException(jwtUtils.getHeader() + "失效，请重新登录");
//        }
        //将数据存在当前的线程中
        UserIdUtil.setId(Long.valueOf(claims.get(FlybirdsClaims.USER_ID).toString()));
        UserIdUtil.setUserName(String.valueOf(claims.get(FlybirdsClaims.USER_NAME)));
        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY,claims.get(FlybirdsClaims.USER_ID));
        return true;
    }
}
