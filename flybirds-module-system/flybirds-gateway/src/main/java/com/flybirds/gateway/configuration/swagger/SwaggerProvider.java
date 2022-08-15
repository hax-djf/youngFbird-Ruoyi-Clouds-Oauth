package com.flybirds.gateway.configuration.swagger;

import com.flybirds.common.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 聚合系统接口
 *
 * @author ruoyi
 */
@Component
public class SwaggerProvider implements SwaggerResourcesProvider {

    // Swagger2默认的url后缀
    public static final String SWAGGER2URL = "/v2/api-docs";

    // 网关路由
    @Autowired
    private RouteLocator routeLocator;

    // 网关配置
    @Autowired
    private GatewayProperties gatewayProperties;

    /**
     * 聚合其他服务接口,排除了flybirds-oauth，该接口不对外开放
     * @return {@link List<SwaggerResource>}
     */
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resourceList = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        // 获取网关中配置的route
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        gatewayProperties.getRoutes().stream()
                .filter(routeDefinition -> routes
                        .contains(routeDefinition.getId()))
                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                        .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
                        .filter(predicateDefinition -> !Constant.CloudServiceName.FLYBIRDS_OAUTH.equalsIgnoreCase(routeDefinition.getId()))
                        .forEach(predicateDefinition -> resourceList
                                .add(swaggerResource(routeDefinition.getId(), predicateDefinition.getArgs()
                                        .get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", SWAGGER2URL)))));
        return resourceList;
    }
    //资源名称配置
    private SwaggerResource swaggerResource(String name, String location) {

        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
