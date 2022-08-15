package com.flybirds.web.web.core.interceptor;

import cn.hutool.crypto.SecureUtil;
import com.flybirds.redis.manger.RedisCacheManger;
import com.flybirds.web.web.annotation.NoRepeatSubmit;
import com.flybirds.web.web.core.filter.XssRequestWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 不允许重复提交[接口幂等性]
 *
 * @author: flybirds
 **/
@Component
@Order
@Slf4j
@AllArgsConstructor
public class NoRepeatSubmitInterceptor implements HandlerInterceptor {

    private final RedisCacheManger redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!handler.getClass().equals(HandlerMethod.class)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        boolean repeatSubmit = handlerMethod.getMethod().isAnnotationPresent(NoRepeatSubmit.class);
        if (repeatSubmit) {
            XssRequestWrapper wrapper = null;
            if (request instanceof XssRequestWrapper) {
                wrapper = (XssRequestWrapper) request;
            } else {
                return true;
            }
            String body = wrapper.getContent();
            String md5 = SecureUtil.md5(body);
            String requestURI = wrapper.getRequestURI();
            String key = new StringBuffer("repeat:").append(requestURI).append(":").append(md5).toString();
            //存在key认为是重复提交
            synchronized (key) {
                if (redisUtils.exists(key)) {
                    log.info("无效重复提交key:{} body:{}", key, body);
                    return false;
                } else {
                    redisUtils.setCacheObject(key, "", 2L, TimeUnit.SECONDS);
                }
            }
        }
        return true;
    }

}
