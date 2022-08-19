package com.flybirds.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flybirds.common.constant.ApiConstant;
import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.gateway.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 验证码过滤器
 *
 * @author flybirds
 */
@Order(0)
@Component
public class VerificationCodeFilter extends AbstractGatewayFilterFactory<Object> {

    @Autowired
    private VerificationCodeService verificationCodeService;

    private static final String CODE = "code";

    private static final String UUID = "uuid";

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!StringUtils.matches(request.getURI().getPath(), ApiConstant.API_LOGIN)) {
                return chain.filter(exchange);
            }
            try {
                String rspStr = resolveBodyFromRequest(request);
                JSONObject obj = JSONObject.parseObject(rspStr);
                verificationCodeService.checkCapcha(obj.getString(CODE), obj.getString(UUID));
            }
            catch (Exception e) {
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return exchange.getResponse().writeWith(
                        Mono.just(response.bufferFactory().wrap(JSON.toJSONBytes(AjaxResult.error(e.getMessage())))));
            }
            return chain.filter(exchange);
        };
    }

    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        // 获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        return bodyRef.get();
    }
}
