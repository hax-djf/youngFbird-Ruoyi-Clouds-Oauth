package com.flybirds.gateway.ignore;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 网关白名单配置
 *
 * @author :ruoyi
 */
@Component
@Configuration
@RefreshScope   //实时刷新
@ConfigurationProperties(prefix = "ignore")
@PropertySource("classpath:bootstrap.yml")  //设置默认指向 去nacos配置【flybirds-gateway-dev.yml】
public class IgnoreUserProperties {

    private String name;

    private List<String> whites;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getWhites() {
        return whites;
    }

    public void setWhites(List<String> whites) {
        this.whites = whites;
    }
}
