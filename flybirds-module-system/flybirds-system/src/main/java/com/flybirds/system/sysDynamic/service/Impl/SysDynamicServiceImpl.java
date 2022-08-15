package com.flybirds.system.sysDynamic.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flybirds.system.sysDynamic.entity.SysDynamic;
import com.flybirds.system.sysDynamic.mapper.SysDynamicMapper;
import com.flybirds.system.sysDynamic.service.ISysDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 动态圈 服务层实现
 * 
 * @author ruoyi
 */
@Service
@SuppressWarnings("all")
public class SysDynamicServiceImpl extends ServiceImpl<SysDynamicMapper,SysDynamic> implements ISysDynamicService
{
    @Autowired
    private SysDynamicMapper dynamicMapper;

    /**
     * 查询动态圈信息
     * 
     * @param dynamicId 动态圈ID
     * @return 动态圈信息
     */
    @Override
    public SysDynamic selectDynamicById(Long dynamicId)
    {
        return dynamicMapper.selectDynamicById(dynamicId);
    }

    /**
     * 查询动态圈列表
     * 
     * @param dynamic 动态圈信息
     * @return 动态圈集合
     */
    @Override
    public List<SysDynamic> selectDynamicList(SysDynamic dynamic)
    {
        return dynamicMapper.selectDynamicList(dynamic);
    }

    /**
     * 新增动态圈
     * 
     * @param dynamic 动态圈信息
     * @return 结果
     */
    @Override
    public int insertDynamic(SysDynamic dynamic)
    {
        return dynamicMapper.insertDynamic(dynamic);
    }

    /**
     * 修改动态圈
     * 
     * @param dynamic 动态圈信息
     * @return 结果
     */
    @Override
    public int updateDynamic(SysDynamic dynamic)
    {
        return dynamicMapper.updateDynamic(dynamic);
    }

    /**
     * 删除动态圈对象
     * 
     * @param dynamicId 动态圈ID
     * @return 结果
     */
    @Override
    public int deleteDynamicById(Long dynamicId)
    {
        return dynamicMapper.deleteDynamicById(dynamicId);
    }

    /**
     * 批量删除动态圈信息
     * 
     * @param dynamicIds 需要删除的动态圈ID
     * @return 结果
     */
    @Override
    public int deleteDynamicByIds(Long[] dynamicIds)
    {
        return dynamicMapper.deleteDynamicByIds(dynamicIds);
    }
}
