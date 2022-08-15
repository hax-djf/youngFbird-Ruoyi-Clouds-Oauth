package com.flybirds.api;

import com.flybirds.api.core.dto.LogininforRespDTO;
import com.flybirds.api.core.entity.SysOperLog;
import com.flybirds.api.factory.RemoteLogFallbackFactory;
import com.flybirds.common.constant.Constant;
import com.flybirds.common.util.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志服务
 * 
 * @author flybirds
 */
@FeignClient(contextId = "remoteLogService", value = Constant.CloudServiceName.FLYBIRDS_SYSTEM, fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {
    /**
     * 保存系统日志
     *
     * @param sysOperLog 日志实体
     * @return 结果
     */
    @PostMapping("/operlog")
    Result<Boolean> saveLog(@RequestBody SysOperLog sysOperLog);

//    /**
//     * 保存访问记录(用户的其他操作状态)
//     *
//     * @param username 用户名称
//     * @param status 状态
//     * @param message 消息
//     * @return 结果
//     */
//    @PostMapping("/logininfor/add/other")
//    Result<Boolean> saveLogininfor(@RequestBody UserAgentEntity userAgentEntity, @RequestParam("username") String username, @RequestParam("status") Integer status,
//                                   @RequestParam("message") String message);
//
//    /**
//     * 保存访问记录(用户登录成功)
//     * @param userAgentEntity
//     * @param username
//     * @param status
//     * @param message
//     * @param loginChannelEnum
//     * @return
//     */
//    @PostMapping("/logininfor/add/login")
//    Result<Boolean> saveLogininforByLogin(@RequestBody UserAgentEntity userAgentEntity, @RequestParam("username") String username, @RequestParam("status") Integer status,
//                                          @RequestParam("message") String message, @RequestParam(value = "loginChannelEnum") AccountLoginChannelEnum loginChannelEnum);



    @PostMapping("/logininfor/add/loginInfo")
    Result<Boolean> saveLogininfor(@RequestBody LogininforRespDTO dto);
}
