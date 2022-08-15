package com.flybirds.system.sysDicData.controller;

import com.flybirds.api.core.entity.SysDictData;
import com.flybirds.common.util.result.AjaxResult;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.excel.utils.poi.POIExcelUtil;
import com.flybirds.log.annotation.Log;
import com.flybirds.log.enums.BusinessType;
import com.flybirds.mybatis.core.controller.MpBaseController;
import com.flybirds.mybatis.core.page.MpTableDataInfo;
import com.flybirds.security.annotation.PreAuthorize;
import com.flybirds.security.core.SecurityUtils;
import com.flybirds.security.model.LoginUser;
import com.flybirds.system.sysDicData.convert.SysDictDataConvert;
import com.flybirds.system.sysDicData.service.ISysDictDataService;
import com.flybirds.system.sysDicData.vo.SysDictDataSimpleRespVO;
import com.flybirds.system.sysDicType.service.ISysDictTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.flybirds.common.util.result.Result.ok;

/**
 * 数据字典信息
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/dict/data")
public class SysDictDataController extends MpBaseController {
    @Autowired
    private ISysDictDataService dictDataService;
    
    @Autowired
    private ISysDictTypeService dictTypeService;


    @PreAuthorize(hasPermi = "system:dict:list")
    @GetMapping("/list")
    public MpTableDataInfo list(SysDictData dictData) {
        mpStartPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getMpDataTable(list);
    }

    /**
     * 无需添加权限认证，因为前端全局都需要进行数据获取
     * @return
     */
    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获得全部字典数据列表", notes = "一般用于管理后台缓存字典数据在本地")
    public Result<List<SysDictDataSimpleRespVO>> getSimpleDictDatas() {
        List<SysDictData> list = dictDataService.getDictDatas();
        return ok(SysDictDataConvert.INSTANCE.convertList02(list));
    }

    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @PreAuthorize(hasPermi = "system:dict:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictData dictData) throws IOException {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        POIExcelUtil<SysDictData> util = new POIExcelUtil<SysDictData>(SysDictData.class);
        util.exportExcel(response, list, "字典数据");
    }

    /**
     * 查询字典数据详细
     */
    @PreAuthorize(hasPermi = "system:dict:query")
    @GetMapping(value = "/{dictCode}")
    public AjaxResult getInfo(@PathVariable Long dictCode) {
        return AjaxResult.success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType) {
        //可以获取用信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data))
        {
            data = new ArrayList<SysDictData>();
        }
        //数据装换精简显示
        return AjaxResult.success(SysDictDataConvert.INSTANCE.convertList02(data));
    }

    /**
     * 新增字典类型
     */
    @PreAuthorize(hasPermi = "system:dict:add")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDictData dict) {
        dict.setCreateName(SecurityUtils.getUsername());
        dict.setCreateUser(SecurityUtils.getUserId());
        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @PreAuthorize(hasPermi = "system:dict:edit")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDictData dict)
    {
        dict.setUpdateName(SecurityUtils.getUsername());
        dict.setUpdateUser(SecurityUtils.getUserId());
        return toAjax(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    @PreAuthorize(hasPermi = "system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    public AjaxResult remove(@PathVariable Long[] dictCodes)
    {
        return toAjax(dictDataService.deleteDictDataByIds(dictCodes));
    }
}
