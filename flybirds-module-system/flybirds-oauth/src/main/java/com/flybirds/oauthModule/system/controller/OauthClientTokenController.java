package com.flybirds.oauthModule.system.controller;

import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.excel.utils.poi.POIExcelUtil;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.oauthModule.system.entity.OauthClientToken;
import com.flybirds.oauthModule.system.service.IOauthClientTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户端tokenController
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@RestController
@RequestMapping("/oauth-system/clientToken")
public class OauthClientTokenController extends MpBaseController
{
    @Autowired
    private IOauthClientTokenService oauthClientTokenService;

    /**
     * 查询客户端token列表
     */
    @GetMapping("/list")
    public MpTableDataInfo list(OauthClientToken oauthClientToken)
    {
        mpStartPage();
        List<OauthClientToken> list = oauthClientTokenService.selectOauthClientTokenList(oauthClientToken);
        return getMpDataTable(list);
    }

    /**
     * 导出客户端token列表
     */
    @GetMapping("/export")
    public AjaxResult export(OauthClientToken oauthClientToken)
    {
        List<OauthClientToken> list = oauthClientTokenService.selectOauthClientTokenList(oauthClientToken);
        POIExcelUtil<OauthClientToken> util = new POIExcelUtil<OauthClientToken>(OauthClientToken.class);
        return util.exportExcel(list, "客户端token数据");
    }


    /**
     * 删除客户端token
     */
	@DeleteMapping("/{authenticationIds}")
    public AjaxResult remove(@PathVariable String[] authenticationIds)
    {
        return toAjax(oauthClientTokenService.deleteOauthClientTokenByAuthenticationIds(authenticationIds));
    }
}
