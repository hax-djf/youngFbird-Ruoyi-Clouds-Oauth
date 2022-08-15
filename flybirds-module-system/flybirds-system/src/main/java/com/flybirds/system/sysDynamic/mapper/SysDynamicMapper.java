package com.flybirds.system.sysDynamic.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flybirds.system.sysDynamic.entity.SysDynamic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 通知动态表 数据层
 * 
 * @author ruoyi
 */
@Mapper
public interface SysDynamicMapper extends BaseMapper<SysDynamic>
{
    /**
     * 查询动态信息
     * 
     * @param dynamicId 动态ID
     * @return 动态信息
     */
    public SysDynamic selectDynamicById(Long dynamicId);

    /**
     * 查询动态列表
     * 
     * @param dynamic 动态信息
     * @return 动态集合
     */
    public List<SysDynamic> selectDynamicList(SysDynamic dynamic);

    /**
     * 新增动态
     * 
     * @param dynamic 动态信息
     * @return 结果
     */
    public int insertDynamic(SysDynamic dynamic);

    /**
     * 修改动态
     * 
     * @param dynamic 动态信息
     * @return 结果
     */
    public int updateDynamic(SysDynamic dynamic);

    /**
     * 批量删除动态
     * 
     * @param dynamicId 动态ID
     * @return 结果
     */
    public int deleteDynamicById(Long dynamicId);

    /**
     * 批量删除动态信息
     * 
     * @param dynamicIds 需要删除的动态ID
     * @return 结果
     */
    public int deleteDynamicByIds(Long[] dynamicIds);
}