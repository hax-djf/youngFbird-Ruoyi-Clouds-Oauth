package com.flybirds.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.flybirds.common.constant.SecurityConstant;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.gateway.ignore.IgnoreUserProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

import static com.flybirds.common.util.token.TokenUtils.getToken;
import static com.flybirds.common.util.token.TokenUtils.getTokenRedisKey;

/**
 * 自定义网关全[oauth]鉴权局过滤器
 *
 * @author flybirds
 */
@Component
public class OauthFilter implements GlobalFilter,Ordered {

    private static final Logger log = LoggerFactory.getLogger(OauthFilter.class);

    // 排除过滤的 uri 地址，nacos自行添加
    @Autowired
    private IgnoreUserProperties ignoreWhite;

    @SuppressWarnings("all")
    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, Object> redissops;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String url = exchange.getRequest().getURI().getPath();

        log.info("system白名单 {} ,路径地址{}",ignoreWhite.getName(),ignoreWhite.getWhites());

        // 跳过不需要验证的路径
        if (StringUtils.matches(url, ignoreWhite.getWhites())) {

            return chain.filter(exchange);
        }

        String token = getToken(exchange.getRequest());
        if (StringUtils.isBlank(token)) {
            // todo 将非法登录的地址信息存入到日志中
            return setUnauthorizedResponse(exchange, "令牌不能为空，非法登录！");
        }
        //根据令牌信息从redis中获取用户的信息
        if (StringUtils.isNull(redissops.get(getTokenRedisKey(token)))) {
            return setUnauthorizedResponse(exchange, "登录状态已过期");
        }
        //设置用户信息到请求--传递请求头数据
        ServerHttpRequest mutableReq = exchange.getRequest().mutate()
                .header(SecurityConstant.HEADER,new StringBuffer(SecurityConstant.TOKEN_PREFIX).append(token).toString())
                .build();
        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();

        return chain.filter(mutableExchange);
    }

    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange, String msg) {
        ServerHttpResponse response = exchange.getResponse();

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        response.setStatusCode(HttpStatus.OK);

        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSON.toJSONBytes((Result.fail(HttpStatus.UNAUTHORIZED,msg))));
        }));
    }

    //优先级
    @Override
    public int getOrder() {
        return -1;
    }
}
