package com.flybirds.oauthModule.system.controller;

import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.excel.utils.poi.POIExcelUtil;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.oauthModule.system.entity.OauthRefreshToken;
import com.flybirds.oauthModule.system.service.IOauthRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * token刷新Controller
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@RestController
@RequestMapping("/oauth-system/refreshToken")
public class OauthRefreshTokenController extends MpBaseController
{
    @Autowired
    private IOauthRefreshTokenService oauthRefreshTokenService;

    /**
     * 查询token刷新列表
     */
    @GetMapping("/list")
    public MpTableDataInfo list(OauthRefreshToken oauthRefreshToken)
    {
        mpStartPage();
        List<OauthRefreshToken> list = oauthRefreshTokenService.selectOauthRefreshTokenList(oauthRefreshToken);
        return getMpDataTable(list);
    }

    /**
     * 导出token刷新列表
     */
    @GetMapping("/export")
    public AjaxResult export(OauthRefreshToken oauthRefreshToken)
    {
        List<OauthRefreshToken> list = oauthRefreshTokenService.selectOauthRefreshTokenList(oauthRefreshToken);
        POIExcelUtil<OauthRefreshToken> util = new POIExcelUtil<OauthRefreshToken>(OauthRefreshToken.class);
        return util.exportExcel(list, "token刷新数据");
    }

    /**
     * 获取token刷新详细信息
     */
    @GetMapping(value = "/{tokenId}")
    public AjaxResult getInfo(@PathVariable("tokenId") String tokenId)
    {
        return AjaxResult.success(oauthRefreshTokenService.selectOauthRefreshTokenByTokenId(tokenId));
    }

    /**
     * 新增token刷新
     */
    @PostMapping
    public AjaxResult add(@RequestBody OauthRefreshToken oauthRefreshToken)
    {
        return toAjax(oauthRefreshTokenService.insertOauthRefreshToken(oauthRefreshToken));
    }

    /**
     * 修改token刷新
     */
    @PutMapping
    public AjaxResult edit(@RequestBody OauthRefreshToken oauthRefreshToken)
    {
        return toAjax(oauthRefreshTokenService.updateOauthRefreshToken(oauthRefreshToken));
    }

    /**
     * 删除token刷新
     */
	@DeleteMapping("/{tokenIds}")
    public AjaxResult remove(@PathVariable String[] tokenIds)
    {
        return toAjax(oauthRefreshTokenService.deleteOauthRefreshTokenByTokenIds(tokenIds));
    }
}
