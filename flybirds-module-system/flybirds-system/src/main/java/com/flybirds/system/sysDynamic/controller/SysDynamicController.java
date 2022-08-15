package com.flybirds.system.sysDynamic.controller;

import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.log.annotation.Log;
import com.flybirds.log.enums.BusinessType;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.security.annotation.PreAuthorize;
import com.flybirds.security.core.SecurityUtils;
import com.flybirds.system.sysDynamic.entity.SysDynamic;
import com.flybirds.system.sysDynamic.service.ISysDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户动态
 * 
 * @author flybirds
 */
@RestController
@RequestMapping("/dynamic")
public class SysDynamicController extends MpBaseController
{
    @Autowired
    private ISysDynamicService dynamicService;

    /**
     * 获取动态圈列表
     */
    @PreAuthorize(hasPermi = "system:dynamic:list")
    @GetMapping("/list")
    public MpTableDataInfo list(SysDynamic dynamic)
    {
        mpStartPage();
        List<SysDynamic> list = dynamicService.selectDynamicList(dynamic);
        return getMpDataTable(list);
    }

    /**
     * 根据动态圈编号获取详细信息
     */
    @PreAuthorize(hasPermi = "system:dynamic:query")
    @GetMapping(value = "/{dynamicId}")
    public AjaxResult getInfo(@PathVariable Long dynamicId)
    {
        return AjaxResult.success(dynamicService.selectDynamicById(dynamicId));
    }

    /**
     * 新增动态圈
     */
    @PreAuthorize(hasPermi = "system:dynamic:add")
    @Log(title = "动态圈", businessType = BusinessType.UPDATE)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDynamic dynamic)
    {
        dynamic.setCreateName(SecurityUtils.getUsername());
        dynamic.setCreateUser(SecurityUtils.getUserId());
        return toAjax(dynamicService.insertDynamic(dynamic));
    }

    /**
     * 修改动态圈
     */
    @PreAuthorize(hasPermi = "system:dynamic:edit")
    @Log(title = "动态圈", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDynamic dynamic)
    {
        dynamic.setCreateName(SecurityUtils.getUsername());
        dynamic.setCreateUser(SecurityUtils.getUserId());
        dynamic.setAvatar(SecurityUtils.getSysUser().getAvatar());
        return toAjax(dynamicService.updateDynamic(dynamic));
    }

    /**
     * 删除动态圈
     */
    @PreAuthorize(hasPermi = "system:dynamic:remove")
    @Log(title = "动态圈", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dynamicIds}")
    public AjaxResult remove(@PathVariable Long[] dynamicIds)
    {
        return toAjax(dynamicService.deleteDynamicByIds(dynamicIds));
    }

    /**
     * 用户获取动态圈数据
     */
    @GetMapping("/userList")
    public MpTableDataInfo userlist(SysDynamic sysDynamic)
    {
        mpStartPage();
        //获取公开的数据
        sysDynamic.setDynamicType("0");
        List<SysDynamic> list = dynamicService.selectDynamicList(sysDynamic);
        return getMpDataTable(list);
    }
}
