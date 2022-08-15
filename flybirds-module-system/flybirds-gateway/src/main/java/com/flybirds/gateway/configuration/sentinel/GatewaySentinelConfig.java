package com.flybirds.gateway.configuration.sentinel;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.flybirds.gateway.handler.sentinel.SentinelFallbackHandler;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**

 * @author flybirds
 * @create : 2021/1/31 14:06:00
 * @description : sentinel和gateway 适配分组限流的规则
 */
@Configuration
public class GatewaySentinelConfig {

    //自定义限流异常处理
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelFallbackHandler sentinelGatewayExceptionHandler() {
        return new SentinelFallbackHandler();
    }

    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @PostConstruct
    public void doInit() {
        // 加载网关限流规则
        initGatewayRules();
    }

    //网关限流规则
    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        rules.add(new GatewayFlowRule("flybirds-system")
                .setCount(100) // 限流阈值
                .setIntervalSec(60)); // 统计时间窗口，单位是秒，默认是 1 秒
        rules.add(new GatewayFlowRule("flybirds-api")
                .setCount(100) // 限流阈值
                .setIntervalSec(60));
        // 加载网关限流规则
        GatewayRuleManager.loadRules(rules);
        // 加载限流分组
        initCustomizedApis();
    }


    //限流分组
    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        // flybirds-system 组
        ApiDefinition api1 = new ApiDefinition("flybirds-system").setPredicateItems(new HashSet<ApiPredicateItem>()
        {
            private static final long serialVersionUID = 1L;
            {
                // 匹配 /user 以及其子路径的所有请求
                add(new ApiPathPredicateItem().setPattern("/system/user/**")
                        .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
            }
        });
        // flybirds-api 组
        ApiDefinition api2 = new ApiDefinition("flybirds-api").setPredicateItems(new HashSet<ApiPredicateItem>()
        {
            private static final long serialVersionUID = 1L;
            {
                // 只匹配 /job/list
                add(new ApiPathPredicateItem().setPattern("/code/gen/list"));
            }
        });
        definitions.add(api1);
        definitions.add(api2);
        // 加载限流分组
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

}
