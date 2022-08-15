package com.flybirds.system.sysMonitor.controller;

import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.security.annotation.PreAuthorize;
import com.flybirds.system.sysMonitor.entity.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/server")
public class SysServerController
{
    @PreAuthorize(hasPermi = "monitor:server:list")
    @GetMapping()
    public AjaxResult getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }
}
