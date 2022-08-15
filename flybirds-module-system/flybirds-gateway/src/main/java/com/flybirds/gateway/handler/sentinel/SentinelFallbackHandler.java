package com.flybirds.gateway.handler.sentinel;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.flybirds.common.enums.sentinel.FallbackEnum;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static com.flybirds.common.enums.sentinel.FallbackEnum.*;

/**
 * @author ruoyi
 * @create :2021-01-14 14:06:00
 * @description Sentinel分组限流自定义网关异常适配
 *
 * 注意点：
 *       Sentinel全局限流降级结果返回->其中是包扣了限流和降级的异常处理信息
 *       实现BlockExceptionHandler接口，实现handle方法。
 *       旧版本的sentinel是实现UrlBlockHandler接口，新版本（1.8版本）是没有这个接口的。
 *fallback：
 *
 *  fallback 函数名称，可选项，用于在抛出异常的时候提供 fallback 处理逻辑。
 *  fallback 函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理。
 *  fallback 函数签名和位置要求：
 *  返回值类型必须与原函数返回值类型一致；
 *  方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
 *  fallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 fallbackClass 为对应的类的 Class 对象，
 *  注意对应的函数必需为 static 函数，否则无法解析
 *
 *  注：1.6.0 之前的版本 fallback 函数只针对降级异常（DegradeException）进行处理，不能针对业务异常进行处理。
 *   若 blockHandler 和 fallback 都进行了配置，则被限流降级而抛出 BlockException 时只会进入 blockHandler 处理逻辑。
 *   如果没有配置的话 ，fallback对限流和降级都会进行异常处理操作。
 */
public class SentinelFallbackHandler implements WebExceptionHandler {

    private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange, FallbackEnum fallbackEnum) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        //默认值
        byte[] datas= "{\"code\":429,\"msg\":\"网关异常处理未知限流降级，请稍后再试\"}".getBytes(StandardCharsets.UTF_8);
        switch (fallbackEnum){
            case FlowExceptionEnum:
                datas = "{\"code\":429,\"msg\":\"网关异常处理请求超过最大数，请稍后再试\"}".getBytes(StandardCharsets.UTF_8);
                break;
            case DegradeExceptionEnum:
                datas = "{\"code\":429,\"msg\":\"网关异常处理系统降级，请稍后再试\"}".getBytes(StandardCharsets.UTF_8);
                break;
            case ParamFlowExceptionEnum:
                datas = "{\"code\":429,\"msg\":\"网关异常处理热点参数限流，请稍后再试\"}".getBytes(StandardCharsets.UTF_8);
                break;
            case SystemBlockExceptionEnum:
                datas = "{\"code\":429,\"msg\":\"网关异常处理系统规则限流或降级，请稍后再试\"}".getBytes(StandardCharsets.UTF_8);
                break;
            case AuthorityExceptionEnum:
                datas = "{\"code\":429,\"msg\":\"网关异常处理系统授权规则不通过，请稍后再试\"}".getBytes(StandardCharsets.UTF_8);
                break;
        }
        DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(datas);
        return serverHttpResponse.writeWith(Mono.just(buffer));
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        if (!BlockException.isBlockException(ex)) {
            return Mono.error(ex);
        }
        //异常为BlockException
        if(BlockException.isBlockException(ex)){
            if (ex instanceof FlowException) {
                return handleBlockedRequest(exchange, ex).flatMap(response -> writeResponse(response, exchange,FlowExceptionEnum));

            } else if (ex instanceof DegradeException) {
                return handleBlockedRequest(exchange, ex).flatMap(response -> writeResponse(response, exchange,DegradeExceptionEnum));

            } else if (ex instanceof ParamFlowException) {
                return handleBlockedRequest(exchange, ex).flatMap(response -> writeResponse(response, exchange,ParamFlowExceptionEnum));

            } else if (ex instanceof SystemBlockException) {
                return handleBlockedRequest(exchange, ex).flatMap(response -> writeResponse(response, exchange,SystemBlockExceptionEnum));

            } else if (ex instanceof AuthorityException) {
                return handleBlockedRequest(exchange, ex).flatMap(response -> writeResponse(response, exchange,AuthorityExceptionEnum));

            }
        }
        return handleBlockedRequest(exchange, ex).flatMap(response -> writeResponse(response, exchange,FlowExceptionEnum));
    }

    private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
        return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
    }
}
