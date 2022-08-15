package com.flybirds.api.factory;

import com.flybirds.api.RemoteLogService;
import com.flybirds.api.core.dto.LogininforRespDTO;
import com.flybirds.api.core.entity.SysOperLog;
import com.flybirds.common.util.result.Result;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 日志服务降级处理
 * 
 * @author flybirds
 */
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteLogFallbackFactory.class);

    @Override
    public RemoteLogService create(Throwable throwable)
    {
        log.error("日志服务调用失败:{}", throwable.getMessage());
        return new RemoteLogService()
        {
            @Override
            public Result<Boolean> saveLog(SysOperLog sysOperLog)
            {
                return null;
            }

            @Override
            public Result<Boolean> saveLogininfor(LogininforRespDTO dto) {
                return Result.fail(false);
            }

        };

    }
}
