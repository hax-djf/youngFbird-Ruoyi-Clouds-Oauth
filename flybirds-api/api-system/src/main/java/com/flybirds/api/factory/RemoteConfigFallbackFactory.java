package com.flybirds.api.factory;

import com.flybirds.api.RemoteConfigService;
import com.flybirds.common.util.result.AjaxResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteConfigFallbackFactory implements FallbackFactory<RemoteConfigService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteConfigFallbackFactory.class);

    @Override
    public RemoteConfigService create(Throwable throwable) {

        log.error("系统配置调用服务降级失败:{}", throwable.getMessage());
        return new RemoteConfigService()
        {
            @Override
            public AjaxResult getConfigKey(String configKey) {
                return AjaxResult.error("通过key获取系统配置失败");
            }
        };
    }
}
