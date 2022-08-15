package com.flybirds.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.flybirds.common.util.result.AjaxResult;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 黑名单过滤器
 *
 * @author ruoyi
 *
 * 注意点 :
 *      继承实现 AbstractGatewayFilterFactory工厂过滤器，是需要进行制定路由规则的
 *      见文档 flybirds-gateway.md
 *      进入黑名单的时候,此时的请求接口地址已经是StripPrefix=**操作 真实的地址,所有黑名单的设置操作，需要进行此步操作。
 *      列如 :/oauth2/token/login -> /token/login
 */
@Order(1)
@Component
public class BlackListUrlFilter extends AbstractGatewayFilterFactory<BlackListUrlFilter.Config> {

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String url = exchange.getRequest().getURI().getPath();
            if (config.matchBlacklist(url)) {
                ServerHttpResponse response = exchange.getResponse();
                return exchange.getResponse().writeWith(
                        Mono.just(response.bufferFactory().wrap(JSON.toJSONBytes(AjaxResult.error("服务拒绝访问")))));
            }

            return chain.filter(exchange);
        };
    }

    public BlackListUrlFilter()
    {
        super(Config.class);
    }

    public static class Config {

        /** blacklistUrl 来自于nacos路由配置黑名单的 blacklistUrl */

        private List<String> blacklistUrl;

        private List<Pattern> blacklistUrlPattern = new ArrayList<>();

        public boolean matchBlacklist(String url) {
            return blacklistUrlPattern.isEmpty() ? false : blacklistUrlPattern.stream().filter(p -> p.matcher(url).find()).findAny().isPresent();
        }

        public List<String> getBlacklistUrl()
        {
            return blacklistUrl;
        }

        public void setBlacklistUrl(List<String> blacklistUrl) {
            this.blacklistUrl = blacklistUrl;
            this.blacklistUrlPattern.clear();
            this.blacklistUrl.forEach(url -> {
                this.blacklistUrlPattern.add(Pattern.compile(url.replaceAll("\\*\\*", "(.*?)"), Pattern.CASE_INSENSITIVE));
            });
        }
    }

}
