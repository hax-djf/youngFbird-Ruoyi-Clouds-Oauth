package com.flybirds.web.web.core.resolver;

import com.flybirds.api.RemoteUserService;
import com.flybirds.api.model.UserInfo;
import com.flybirds.common.util.result.Result;
import com.flybirds.web.web.annotation.LoginUser;
import com.flybirds.web.web.core.interceptor.AuthorizationInterceptor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 *
 * @author flybirds
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final RemoteUserService userService;

    public LoginUserHandlerMethodArgumentResolver(RemoteUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserInfo.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //获取用户ID
        Object object = request.getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if (object == null) {
            return null;
        }
        //获取用户信息
        Result<UserInfo> result = userService.getUserInfoById(Long.valueOf(String.valueOf(object)));
        UserInfo user = result.getData();
        return user;
    }
}
