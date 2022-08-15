package com.flybirds.log.service;

import com.flybirds.api.RemoteLogService;
import com.flybirds.api.core.entity.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用日志服务
 * 
 * @author flybirds
 */
@Service
@SuppressWarnings("all")
public class AsyncLogService {

    @Autowired
    private RemoteLogService remoteLogService;

    /**
     * 保存系统日志记录 api不能拦截操作
     */
    @Async("asyncTaskExecutor")
    public void saveSysLog(SysOperLog sysOperLog)
    {
        remoteLogService.saveLog(sysOperLog);
    }
}
