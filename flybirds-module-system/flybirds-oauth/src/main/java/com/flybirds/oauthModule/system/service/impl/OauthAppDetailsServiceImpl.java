package com.flybirds.oauthModule.system.service.impl;

import com.flybirds.common.util.date.DateUtils;
import com.flybirds.oauthModule.system.entity.OauthAppDetails;
import com.flybirds.oauthModule.system.entity.OauthClientDetails;
import com.flybirds.oauthModule.system.mapper.OauthAppDetailsMapper;
import com.flybirds.oauthModule.system.service.IOauthAppDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 应用信息appService业务层处理
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@Service
public class OauthAppDetailsServiceImpl implements IOauthAppDetailsService 
{
    @Autowired
    private OauthAppDetailsMapper oauthAppDetailsMapper;

    /**
     * 查询应用信息app
     * 
     * @param id 应用信息app主键
     * @return 应用信息app
     */
    @Override
    public OauthAppDetails selectOauthAppDetailsById(Long id)
    {
        return oauthAppDetailsMapper.selectOauthAppDetailsById(id);
    }

    /**
     * 查询应用信息app列表
     * 
     * @param oauthAppDetails 应用信息app
     * @return 应用信息app
     */
    @Override
    public List<OauthAppDetails> selectOauthAppDetailsList(OauthAppDetails oauthAppDetails)
    {
        return oauthAppDetailsMapper.selectOauthAppDetailsList(oauthAppDetails);
    }

    /**
     * 新增应用信息app
     * 
     * @param oauthAppDetails 应用信息app
     * @return 结果
     */
    @Override
    public int insertOauthAppDetails(OauthAppDetails oauthAppDetails)
    {
        //应用名称不能重复
        //生成 clent_id + clent_id
        oauthAppDetails.setCreateTime(DateUtils.getNowDate());
        return oauthAppDetailsMapper.insertOauthAppDetails(oauthAppDetails);
    }

    /**
     * 修改应用信息app
     * 
     * @param oauthAppDetails 应用信息app
     * @return 结果
     */
    @Override
    public int updateOauthAppDetails(OauthAppDetails oauthAppDetails)
    {
        oauthAppDetails.setUpdateTime(DateUtils.getNowDate());
        return oauthAppDetailsMapper.updateOauthAppDetails(oauthAppDetails);
    }

    /**
     * 批量删除应用信息app
     * 
     * @param ids 需要删除的应用信息app主键
     * @return 结果
     */
    @Override
    public int deleteOauthAppDetailsByIds(Long[] ids)
    {
        return oauthAppDetailsMapper.deleteOauthAppDetailsByIds(ids);
    }

    /**
     * 删除应用信息app信息
     * 
     * @param id 应用信息app主键
     * @return 结果
     */
    @Override
    public int deleteOauthAppDetailsById(Long id)
    {
        return oauthAppDetailsMapper.deleteOauthAppDetailsById(id);
    }
}
