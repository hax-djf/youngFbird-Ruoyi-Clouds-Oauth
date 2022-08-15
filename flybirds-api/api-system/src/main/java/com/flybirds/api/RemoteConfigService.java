package com.flybirds.api;

import com.flybirds.api.factory.RemoteUserFallbackFactory;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.util.result.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * FeignClient-用户服务
 *
 * @author flybirds
 *
 * 注意点：这里降级服务引入的包是 feign.hystrix.FallbackFactory;
 *        但是我们是使用的sentinel是没问题的
 *        其实不管是Hystrix还是Sentinel对于Feign的支持，核心代码基本上是一致的，只需要修改依赖和配置文件即可。
 */
@FeignClient(contextId = "remoteConfigService", value = Constant.CloudServiceName.FLYBIRDS_SYSTEM, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteConfigService {
    /**
     *
     *通过key 查询系统配置
     * @param configKey 用户名
     * @return 结果
     */
    @GetMapping(value = "/config/configKey/{configKey}")
    public AjaxResult getConfigKey(@PathVariable(value = "configKey") String configKey);

}
