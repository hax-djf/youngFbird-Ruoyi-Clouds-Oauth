package com.flybirds.oauthModule.system.controller;

import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.excel.utils.poi.POIExcelUtil;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.oauthModule.system.entity.OauthAppDetails;
import com.flybirds.oauthModule.system.service.IOauthAppDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应用信息appController
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@RestController
@RequestMapping("/oauth-system/appDetails")
public class OauthAppDetailsController extends MpBaseController
{
    @Autowired
    private IOauthAppDetailsService oauthAppDetailsService;

    /**
     * 查询应用信息app列表
     */
    @GetMapping("/list")
    public MpTableDataInfo list(OauthAppDetails oauthAppDetails)
    {
        mpStartPage();
        List<OauthAppDetails> list = oauthAppDetailsService.selectOauthAppDetailsList(oauthAppDetails);
        return getMpDataTable(list);
    }

    /**
     * 导出应用信息app列表
     */
    @GetMapping("/export")
    public AjaxResult export(OauthAppDetails oauthAppDetails)
    {
        List<OauthAppDetails> list = oauthAppDetailsService.selectOauthAppDetailsList(oauthAppDetails);
        POIExcelUtil<OauthAppDetails> util = new POIExcelUtil<OauthAppDetails>(OauthAppDetails.class);
        return util.exportExcel(list, "应用信息app数据");
    }

    /**
     * 获取应用信息app详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(oauthAppDetailsService.selectOauthAppDetailsById(id));
    }

    /**
     * 新增应用信息app
     */
    @PostMapping
    public AjaxResult add(@RequestBody OauthAppDetails oauthAppDetails)
    {
        return toAjax(oauthAppDetailsService.insertOauthAppDetails(oauthAppDetails));
    }

    /**
     * 修改应用信息app
     */
    @PutMapping
    public AjaxResult edit(@RequestBody OauthAppDetails oauthAppDetails)
    {
        return toAjax(oauthAppDetailsService.updateOauthAppDetails(oauthAppDetails));
    }

    /**
     * 删除应用信息app
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(oauthAppDetailsService.deleteOauthAppDetailsByIds(ids));
    }
}
