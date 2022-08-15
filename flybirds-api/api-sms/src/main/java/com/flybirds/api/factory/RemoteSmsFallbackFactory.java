package com.flybirds.api.factory;

import com.flybirds.api.RemoteSmsService;
import com.flybirds.api.core.dto.SysSmsSendMessageDto;
import com.flybirds.common.util.result.Result;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 短信服务降级处理
 * 
 * @author flybirds
 */
@Component
public class RemoteSmsFallbackFactory implements FallbackFactory<RemoteSmsService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteSmsFallbackFactory.class);

    @Override
    public RemoteSmsService create(Throwable throwable)
    {

        log.error("断行服务调用失败:{}", throwable.getMessage());

        return new RemoteSmsService()
        {
            @Override
            public Result<?> sendSmsMessage(SysSmsSendMessageDto sysSmsSendMessageDto) {
                return Result.fail("短信发送失败:" + throwable.getMessage());
            }
        };
    }
}
