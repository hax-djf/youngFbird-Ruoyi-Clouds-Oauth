package com.flybirds.oauth.handler;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

/**
 * Oauth的SentinelrestTemplate的实现类
 *
 * @author :flybirds
 */
public class SentinelExceptionHandler {

    // 服务流量控制处理
    public static ClientHttpResponse handleException(HttpRequest request, byte[] body,
                                                     ClientHttpRequestExecution execution, BlockException exception) {
        exception.printStackTrace();
        return new SentinelClientHttpResponse("{\"code\":\"500\",\"msg\": \"oauth服务流量控制处理\"}");
    }

    // 服务熔断降级处理
    public static ClientHttpResponse fallback(HttpRequest request, byte[] body, ClientHttpRequestExecution execution,
                                              BlockException exception) {
        exception.printStackTrace();
        return new SentinelClientHttpResponse("{\"code\":\"500\",\"msg\": \"oauth服务熔断降级处理\"}");
    }
}
