package com.flybirds.oauthModule.system.controller;

import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.excel.utils.poi.POIExcelUtil;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.oauthModule.system.entity.OauthCode;
import com.flybirds.oauthModule.system.service.IOauthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 授权码信息Controller
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@RestController
@RequestMapping("/oauth-system/code")
public class OauthCodeController extends MpBaseController
{
    @Autowired
    private IOauthCodeService oauthCodeService;

    /**
     * 查询授权码信息列表
     */
    @GetMapping("/list")
    public MpTableDataInfo list(OauthCode oauthCode)
    {
        mpStartPage();
        List<OauthCode> list = oauthCodeService.selectOauthCodeList(oauthCode);
        return getMpDataTable(list);
    }

    /**
     * 导出授权码信息列表
     */
    @GetMapping("/export")
    public AjaxResult export(OauthCode oauthCode)
    {
        List<OauthCode> list = oauthCodeService.selectOauthCodeList(oauthCode);
        POIExcelUtil<OauthCode> util = new POIExcelUtil<OauthCode>(OauthCode.class);
        return util.exportExcel(list, "授权码信息数据");
    }

}
