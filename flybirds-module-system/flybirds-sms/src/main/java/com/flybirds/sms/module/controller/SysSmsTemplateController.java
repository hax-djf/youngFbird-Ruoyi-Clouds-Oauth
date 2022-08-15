package com.flybirds.sms.module.controller;

import com.flybirds.common.util.result.Result;
import com.flybirds.excel.utils.easyexcel.EASYExcelUtils;
import com.flybirds.log.annotation.Log;
import com.flybirds.log.enums.BusinessType;
import com.flybirds.mybatis.core.condition.Params;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.mybatis.core.page.PageResult;
import com.flybirds.security.annotation.PreAuthorize;
import com.flybirds.sms.module.convert.SysSmsTemplateConvert;
import com.flybirds.sms.module.core.vo.template.*;
import com.flybirds.sms.module.entity.SysSmsTemplate;
import com.flybirds.sms.module.service.SysSmsService;
import com.flybirds.sms.module.service.SysSmsTemplateService;
import com.flybirds.sms.module.vo.SysSmsTemplateSendReqVO;
import com.flybirds.web.web.annotation.Login;
import com.flybirds.web.web.core.util.WebFrameworkUtils;
import com.flybirds.web.web.core.util.user.UserIdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.flybirds.common.util.result.Result.ok;


@Api(tags = "短信模板")
@RestController
@RequestMapping("template")
public class SysSmsTemplateController  extends MpBaseController<SysSmsTemplate> {

    @Autowired
    private SysSmsTemplateService smsTemplateService;

    @Autowired
    private SysSmsService smsService;

    @PostMapping("/save")
    @ApiOperation("创建短信模板")
    @PreAuthorize(hasPermi = "sms:template:add")
    public Result<?> saveSmsTemplate(@Valid @RequestBody SysSmsTemplate sysSmsTemplate) {

        return smsTemplateService.saveSmsTemplate(sysSmsTemplate);
    }

    @PostMapping("/create")
    @ApiOperation("创建短信模板")
    @PreAuthorize(hasPermi = "sms:template:add")
    public Result<Long> createSmsTemplate(@Valid @RequestBody SysSmsTemplateCreateReqVO createReqVO) {
        return ok(smsTemplateService.createSmsTemplate(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新短信模板")
    @PreAuthorize(hasPermi = "sms:template:edit")

    public Result<?> updateSmsTemplate(@Valid @RequestBody SysSmsTemplateUpdateReqVO updateReqVO) {
       return smsTemplateService.updateSmsTemplate(updateReqVO);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除短信模板")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @PreAuthorize(hasPermi = "sms:template:delete")
    public Result<?> deleteSmsTemplate(@RequestParam("id") Long id) {

       return smsTemplateService.deleteSmsTemplate(id);
    }

    @GetMapping("/get")
    @ApiOperation("获得短信模板")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize(hasPermi = "sms:template:query")
    public Result<SysSmsTemplate> getSmsTemplate(@RequestParam("id") Long id) {

        return ok(smsTemplateService.getSmsTemplate(id));
    }

    @GetMapping("/queryParams")
    @ApiOperation("获得短信模板分页")
    @PreAuthorize(hasPermi = "sms:template:query")
    public MpTableDataInfo getSmsTemplatePage(@Valid @RequestBody Params params) {

        initParameter(params);
        return getMpDataTable(smsTemplateService.getSmsTemplatePage(params));

    }

    @GetMapping("/page")
    @ApiOperation("获得短信模板分页")
    @PreAuthorize(hasPermi = "sms:template:query")
    public Result<PageResult<SysSmsTemplateRespVO>> getSmsTemplatePage(@Valid SysSmsTemplatePageReqVO pageVO) {
        PageResult<SysSmsTemplate> pageResult = smsTemplateService.getSmsTemplatePage(pageVO);
        return ok(SysSmsTemplateConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export")
    @ApiOperation("导出短信模板 Excel")
    @PreAuthorize(hasPermi = "sms:template:export")
    @Log(title = "导出短信模板",businessType = BusinessType.EXPORT)
    public void exportSmsTemplateExcel(@Valid @RequestBody  Params params,
                                       HttpServletResponse response) throws IOException {

        smsTemplateService.exportSmsTemplateExcel(response,params);
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出短信模板 Excel")
    @PreAuthorize(hasPermi = "sms:template:export")
    @Log(title = "导出短信模板",businessType = BusinessType.EXPORT)
    public void exportSmsTemplateExcel(@Valid SysSmsTemplateExportReqVO exportReqVO,
                                       HttpServletResponse response) throws IOException {
        List<SysSmsTemplate> list = smsTemplateService.getSmsTemplateList(exportReqVO);
        // 导出 Excel
        List<SysSmsTemplateExcelVO> datas = SysSmsTemplateConvert.INSTANCE.convertList02(list);
        try {
            EASYExcelUtils.writeExcel(response, datas,"短信模板", "数据", SysSmsTemplateExcelVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 短信测试
     * @param sendReqVO
     * @return
     */
    @PostMapping("/send-sms")
    @ApiOperation("发送短信")
    @PreAuthorize(hasPermi = "sms:template:sendSms")
    @Login
    public Result<?> sendSms(@Valid @RequestBody SysSmsTemplateSendReqVO sendReqVO, HttpServletRequest request) {
        return ok(smsService.sendSingleSms(sendReqVO.getMobile(), UserIdUtil.get(),UserIdUtil.getUserName(), WebFrameworkUtils.getUserType(request),
                sendReqVO.getTemplateCode(), sendReqVO.getTemplateParams()));
    }

}
