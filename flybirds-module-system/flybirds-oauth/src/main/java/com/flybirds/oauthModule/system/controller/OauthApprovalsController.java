package com.flybirds.oauthModule.system.controller;

import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.excel.utils.poi.POIExcelUtil;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.oauthModule.system.entity.OauthApprovals;
import com.flybirds.oauthModule.system.service.IOauthApprovalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 授权阈Controller
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@RestController
@RequestMapping("/oauth-system/approvals")
public class OauthApprovalsController extends MpBaseController
{
    @Autowired
    private IOauthApprovalsService oauthApprovalsService;

    /**
     * 查询授权阈列表
     */
    @GetMapping("/list")
    public MpTableDataInfo list(OauthApprovals oauthApprovals)
    {
        mpStartPage();
        List<OauthApprovals> list = oauthApprovalsService.selectOauthApprovalsList(oauthApprovals);
        return getMpDataTable(list);
    }

    /**
     * 导出授权阈列表
     */
    @GetMapping("/export")
    public AjaxResult export(OauthApprovals oauthApprovals)
    {
        List<OauthApprovals> list = oauthApprovalsService.selectOauthApprovalsList(oauthApprovals);
        POIExcelUtil<OauthApprovals> util = new POIExcelUtil<OauthApprovals>(OauthApprovals.class);
        return util.exportExcel(list, "授权阈数据");
    }

    /**
     * 新增授权阈
     */
    @PostMapping
    public AjaxResult add(@RequestBody OauthApprovals oauthApprovals)
    {
        return toAjax(oauthApprovalsService.insertOauthApprovals(oauthApprovals));
    }

    /**
     * 修改授权阈
     */
    @PutMapping
    public AjaxResult edit(@RequestBody OauthApprovals oauthApprovals)
    {
        return toAjax(oauthApprovalsService.updateOauthApprovals(oauthApprovals));
    }

    /**
     * 删除授权阈
     */
	@DeleteMapping("/{userids}")
    public AjaxResult remove(@PathVariable String[] userids)
    {
        return toAjax(oauthApprovalsService.deleteOauthApprovalsByUserids(userids));
    }
}
