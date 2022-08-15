package com.flybirds.system.sysOperlog.controller;

import com.flybirds.api.core.entity.SysOperLog;
import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.excel.utils.poi.POIExcelUtil;
import com.flybirds.log.annotation.Log;
import com.flybirds.log.enums.BusinessType;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.security.annotation.PreAuthorize;
import com.flybirds.system.sysOperlog.service.ISysOperLogService;
import com.flybirds.web.web.annotation.OauthServiceSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 操作日志记录
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/operlog")
public class SysOperlogController extends MpBaseController
{
    @Autowired
    private ISysOperLogService operLogService;

    @PreAuthorize(hasPermi = "system:operlog:list")
    @GetMapping("/list")
    public MpTableDataInfo list(SysOperLog operLog)
    {
        mpStartPage();
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        return getMpDataTable(list);
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PreAuthorize(hasPermi = "system:operlog:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysOperLog operLog) throws IOException
    {
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        POIExcelUtil<SysOperLog> util = new POIExcelUtil<SysOperLog>(SysOperLog.class);
        util.exportExcel(response, list, "操作日志");
    }

    @PreAuthorize(hasPermi = "system:operlog:remove")
    @DeleteMapping("/{operIds}")
    public AjaxResult remove(@PathVariable Long[] operIds)
    {
        return toAjax(operLogService.deleteOperLogByIds(operIds));
    }

    @PreAuthorize(hasPermi = "system:operlog:remove")
    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public AjaxResult clean() {

        operLogService.cleanOperLog();
        return AjaxResult.success();
    }

    /**
     * 保存操作日志
     *
     * @param operLog
     * @return
     */
    @PostMapping
    @OauthServiceSignature
    public AjaxResult add(@RequestBody SysOperLog operLog) {
        return toAjax(operLogService.insertOperlog(operLog));
    }
}
