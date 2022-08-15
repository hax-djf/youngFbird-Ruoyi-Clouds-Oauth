package com.flybirds.web.web.core.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.web.web.annotation.OauthServiceSignature;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务认证签名 [微服务之间，存在一些没有token数据的情况，使用signature鉴权一下]
 *
 * @author: flybirds
 **/
@Component
@Order
@Slf4j
@AllArgsConstructor
public class OauthServiceSignatureInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!handler.getClass().equals(HandlerMethod.class)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        boolean repeatSubmit = handlerMethod.getMethod().isAnnotationPresent(OauthServiceSignature.class);
        if (repeatSubmit) {

            //todo 目前简单处理 后续动态扩展
            return ObjectUtil.equals(request.getHeader(SecurityConstant.SERVICE_SIGNATURE),SecurityConstant.SERVICE_SIGNATURE_VALUE);
        }
        return true;
    }

}
