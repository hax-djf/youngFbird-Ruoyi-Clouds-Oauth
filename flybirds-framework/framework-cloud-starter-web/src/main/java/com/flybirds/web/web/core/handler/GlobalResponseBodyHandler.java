package com.flybirds.web.web.core.handler;

import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.common.util.result.Result;
import com.flybirds.web.web.core.util.WebFrameworkUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局响应结果（ResponseBody）处理器
 * 不同于在网上看到的很多文章，会选择自动将 Controller 返回结果包上 {@link com.flybirds.common.util.result.Result}，
 * 在 onemall 中，是 Controller 在返回时，主动自己包上 {@link com.flybirds.common.util.result.Result}。
 * 原因是，GlobalResponseBodyHandler 本质上是 AOP，它不应该改变 Controller 返回的数据结构
 *
 * 目前，GlobalResponseBodyHandler 的主要作用是，记录 Controller 的返回结果，
 *
 * @author flybirds
 */
@ControllerAdvice
public class GlobalResponseBodyHandler implements ResponseBodyAdvice {

    @Override
    @SuppressWarnings("NullableProblems") // 避免 IDEA 警告
    public boolean supports(MethodParameter returnType, Class converterType) {
        if (returnType.getMethod() == null) {
            return false;
        }

        // 只拦截返回结果为 Result或者是AjaxResult 类型
        return returnType.getMethod().getReturnType() == Result.class
                || returnType.getMethod().getReturnType() == AjaxResult.class;
    }

    @Override
    @SuppressWarnings("NullableProblems") // 避免 IDEA 警告
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 记录 Controller 结果
        WebFrameworkUtils.setObjectResult(((ServletServerHttpRequest) request).getServletRequest(), body);
        return body;
    }

}
