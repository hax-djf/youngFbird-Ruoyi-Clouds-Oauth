package com.flybirds.oauthModule.system.controller;

import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.excel.utils.poi.POIExcelUtil;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.oauthModule.system.entity.OauthAccessToken;
import com.flybirds.oauthModule.system.service.IOauthAccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户token信息Controller
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@RestController
@RequestMapping("/oauth-system/accessToken")
public class OauthAccessTokenController extends MpBaseController
{
    @Autowired
    private IOauthAccessTokenService oauthAccessTokenService;

    /**
     * 查询用户token信息列表
     */
    @GetMapping("/list")
    public MpTableDataInfo list(OauthAccessToken oauthAccessToken)
    {
        mpStartPage();
        List<OauthAccessToken> list = oauthAccessTokenService.selectOauthAccessTokenList(oauthAccessToken);
        return getMpDataTable(list);
    }

    /**
     * 导出用户token信息列表
     */
    @GetMapping("/export")
    public AjaxResult export(OauthAccessToken oauthAccessToken)
    {
        List<OauthAccessToken> list = oauthAccessTokenService.selectOauthAccessTokenList(oauthAccessToken);
        POIExcelUtil<OauthAccessToken> util = new POIExcelUtil<OauthAccessToken>(OauthAccessToken.class);
        return util.exportExcel(list, "用户token信息数据");
    }

    /**
     * 获取用户token信息详细信息
     */
    @GetMapping(value = "/{authenticationId}")
    public AjaxResult getInfo(@PathVariable("authenticationId") String authenticationId)
    {
        return AjaxResult.success(oauthAccessTokenService.selectOauthAccessTokenByAuthenticationId(authenticationId));
    }

    /**
     * 删除用户token信息
     */
	@DeleteMapping("/{authenticationIds}")
    public AjaxResult remove(@PathVariable String[] authenticationIds)
    {
        return toAjax(oauthAccessTokenService.deleteOauthAccessTokenByAuthenticationIds(authenticationIds));
    }
}
