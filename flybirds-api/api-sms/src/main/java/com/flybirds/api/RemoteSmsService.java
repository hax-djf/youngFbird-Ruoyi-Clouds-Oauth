package com.flybirds.api;

import com.flybirds.api.core.dto.SysSmsSendMessageDto;
import com.flybirds.api.factory.RemoteSmsFallbackFactory;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.util.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author flybirds
 * @create: 2021-07-17 14:06:00
 * @description: FeignClient-用户服务
 *
 * 注意点：这里降级服务引入的包是 feign.hystrix.FallbackFactory;
 *        但是我们是使用的sentinel是没问题的
 *        其实不管是Hystrix还是Sentinel对于Feign的支持，核心代码基本上是一致的，只需要修改依赖和配置文件即可。
 */
@FeignClient(contextId = "remoteSmsService", value = Constant.CloudServiceName.FLYBIRDS_SMS, fallbackFactory = RemoteSmsFallbackFactory.class)
public interface RemoteSmsService {
    /**
     * 通过用户名查询用户信息【权限信息】
     *
     * @param sysSmsSendMessageDto 用户名
     * @return 结果
     */
    @PostMapping(value = "/send/message")
    Result<?> sendSmsMessage(@RequestBody SysSmsSendMessageDto sysSmsSendMessageDto);

}
