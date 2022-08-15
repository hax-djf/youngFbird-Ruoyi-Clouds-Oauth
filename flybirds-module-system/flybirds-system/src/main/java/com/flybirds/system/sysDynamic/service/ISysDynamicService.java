package com.flybirds.system.sysDynamic.service;


import com.flybirds.system.sysDynamic.entity.SysDynamic;

import java.util.List;

/**
 * 动态圈 服务层
 * 
 * @author flybirds
 */
public interface ISysDynamicService
{
    /**
     * 查询动态圈信息
     * 
     * @param dynamicId 动态圈ID
     * @return 动态圈信息
     */
     SysDynamic selectDynamicById(Long dynamicId);

    /**
     * 查询动态圈列表
     * 
     * @param dynamic 动态圈信息
     * @return 动态圈集合
     */
     List<SysDynamic> selectDynamicList(SysDynamic dynamic);

    /**
     * 新增动态圈
     * 
     * @param dynamic 动态圈信息
     * @return 结果
     */
     int insertDynamic(SysDynamic dynamic);

    /**
     * 修改动态圈
     * 
     * @param dynamic 动态圈信息
     * @return 结果
     */
     int updateDynamic(SysDynamic dynamic);

    /**
     * 删除动态圈信息
     * 
     * @param dynamicId 动态圈ID
     * @return 结果
     */
     int deleteDynamicById(Long dynamicId);
    
    /**
     * 批量删除动态圈信息
     * 
     * @param dynamicIds 需要删除的动态圈ID
     * @return 结果
     */
     int deleteDynamicByIds(Long[] dynamicIds);

}
