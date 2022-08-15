package com.flybirds.sms.module.controller;

import com.flybirds.common.constant.MsgConstant;
import com.flybirds.common.exception.BaseException;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.excel.utils.easyexcel.EASYExcelUtils;
import com.flybirds.log.annotation.Log;
import com.flybirds.log.enums.BusinessType;
import com.flybirds.mybatis.core.condition.Params;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.mybatis.core.page.PageResult;
import com.flybirds.security.annotation.PreAuthorize;
import com.flybirds.sms.module.convert.SysSmsLogConvert;
import com.flybirds.sms.module.core.vo.log.SysSmsLogExcelVO;
import com.flybirds.sms.module.core.vo.log.SysSmsLogExportReqVO;
import com.flybirds.sms.module.core.vo.log.SysSmsLogPageReqVO;
import com.flybirds.sms.module.core.vo.log.SysSmsLogRespVO;
import com.flybirds.sms.module.entity.SysSmsLog;
import com.flybirds.sms.module.service.SysSmsLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.flybirds.common.util.result.Result.ok;

@Api(tags = "短信日志")
@RestController
@RequestMapping("log")
@Validated
public class SysSmsLogController extends MpBaseController<SysSmsLog> {

    @Autowired
    private SysSmsLogService smsLogService;

    @GetMapping("/queryByParams")
    @ApiOperation("获得短信日志分页")
    @PreAuthorize(hasPermi = "sms:log:query")
    public MpTableDataInfo getSmsLogPage(@Valid @RequestBody Params params) {

        if (StringUtils.isNull(params)) {
            throw new BaseException(MsgConstant.Business.NOT_FOUND_PARAMS);
        }
        initParameter(params);
        return getMpDataTable(smsLogService.getSmsLogPage(params));
    }


    @GetMapping("/page")
    @ApiOperation("获得短信日志分页")
    @PreAuthorize(hasPermi = "sms:log:query")
    public Result<PageResult<SysSmsLogRespVO>> getSmsLogPage(@Valid SysSmsLogPageReqVO pageVO) {
        PageResult<SysSmsLog> pageResult = smsLogService.getSmsLogPage(pageVO);
        return ok(SysSmsLogConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export")
    @ApiOperation("导出短信日志 Excel")
    @PreAuthorize(hasPermi = "sms:log:export")
    @Log(title = "导出短信日志", businessType = BusinessType.EXPORT)
    public void exportSmsLogExcel(@Valid @RequestBody Params params, HttpServletResponse response) throws IOException {
        // 导出 Excel
        if (StringUtils.isNull(params)) {
            throw new BaseException(MsgConstant.Business.NOT_FOUND_PARAMS);
        }
        smsLogService.exportSmsLogExcel(response,params);
    }


    @GetMapping("/export-excel")
    @ApiOperation("导出短信日志 Excel")
    @PreAuthorize(hasPermi = "sms:log:export")
    @Log(title = "导出短信日志", businessType = BusinessType.EXPORT)
    public void exportSmsLogExcel(@Valid SysSmsLogExportReqVO exportReqVO,
                                  HttpServletResponse response) throws IOException {
        List<SysSmsLog> list = smsLogService.getSmsLogList(exportReqVO);
        // 导出 Excel
        List<SysSmsLogExcelVO> datas = SysSmsLogConvert.INSTANCE.convertList02(list);
        try {
            EASYExcelUtils.writeExcel(response, datas,"短信日志.xls", "数据", SysSmsLogExcelVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
