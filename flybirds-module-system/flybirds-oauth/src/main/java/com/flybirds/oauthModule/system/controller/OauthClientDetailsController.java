package com.flybirds.oauthModule.system.controller;

import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.excel.utils.poi.POIExcelUtil;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.oauthModule.system.entity.OauthClientDetails;
import com.flybirds.oauthModule.system.service.IOauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户端信息Controller
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@RestController
@RequestMapping("/oauth-system/client")
public class OauthClientDetailsController extends MpBaseController
{
    @Autowired
    private IOauthClientDetailsService oauthClientDetailsService;

    /**
     * 查询客户端信息列表
     */
    @GetMapping("/list")
    public MpTableDataInfo list(OauthClientDetails oauthClientDetails)
    {
        mpStartPage();
        List<OauthClientDetails> list = oauthClientDetailsService.selectOauthClientDetailsList(oauthClientDetails);
        return getMpDataTable(list);
    }

    /**
     * 导出客户端信息列表
     */
    @GetMapping("/export")
    public AjaxResult export(OauthClientDetails oauthClientDetails)
    {
        List<OauthClientDetails> list = oauthClientDetailsService.selectOauthClientDetailsList(oauthClientDetails);
        POIExcelUtil<OauthClientDetails> util = new POIExcelUtil<OauthClientDetails>(OauthClientDetails.class);
        return util.exportExcel(list, "客户端信息数据");
    }

    /**
     * 获取客户端信息详细信息
     */
    @GetMapping(value = "/{clientId}")
    public AjaxResult getInfo(@PathVariable("clientId") String clientId)
    {
        return AjaxResult.success(oauthClientDetailsService.selectOauthClientDetailsByClientId(clientId));
    }

    /**
     * 新增客户端信息
     */
    @PostMapping
    public AjaxResult add(@RequestBody OauthClientDetails oauthClientDetails)
    {
        return toAjax(oauthClientDetailsService.insertOauthClientDetails(oauthClientDetails));
    }

    /**
     * 修改客户端信息
     */
    @PutMapping
    public AjaxResult edit(@RequestBody OauthClientDetails oauthClientDetails)
    {
        return toAjax(oauthClientDetailsService.updateOauthClientDetails(oauthClientDetails));
    }

    /**
     * 删除客户端信息
     */
	@DeleteMapping("/{clientIds}")
    public AjaxResult remove(@PathVariable String[] clientIds)
    {
        return toAjax(oauthClientDetailsService.deleteOauthClientDetailsByClientIds(clientIds));
    }
}
