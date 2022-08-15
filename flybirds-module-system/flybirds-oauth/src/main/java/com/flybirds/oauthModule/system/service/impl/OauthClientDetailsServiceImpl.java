package com.flybirds.oauthModule.system.service.impl;

import com.flybirds.common.util.date.DateUtils;
import com.flybirds.oauthModule.system.entity.OauthClientDetails;
import com.flybirds.oauthModule.system.mapper.OauthClientDetailsMapper;
import com.flybirds.oauthModule.system.service.IOauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户端信息Service业务层处理
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@Service
public class OauthClientDetailsServiceImpl implements IOauthClientDetailsService 
{
    @Autowired
    private OauthClientDetailsMapper oauthClientDetailsMapper;

    /**
     * 查询客户端信息
     * 
     * @param clientId 客户端信息主键
     * @return 客户端信息
     */
    @Override
    public OauthClientDetails selectOauthClientDetailsByClientId(String clientId)
    {
        return oauthClientDetailsMapper.selectOauthClientDetailsByClientId(clientId);
    }

    /**
     * 查询客户端信息列表
     * 
     * @param oauthClientDetails 客户端信息
     * @return 客户端信息
     */
    @Override
    public List<OauthClientDetails> selectOauthClientDetailsList(OauthClientDetails oauthClientDetails)
    {
        return oauthClientDetailsMapper.selectOauthClientDetailsList(oauthClientDetails);
    }

    /**
     * 新增客户端信息
     * 
     * @param oauthClientDetails 客户端信息
     * @return 结果
     */
    @Override
    public int insertOauthClientDetails(OauthClientDetails oauthClientDetails)
    {
        oauthClientDetails.setCreateTime(DateUtils.getNowDate());
        return oauthClientDetailsMapper.insertOauthClientDetails(oauthClientDetails);
    }

    /**
     * 修改客户端信息
     * 
     * @param oauthClientDetails 客户端信息
     * @return 结果
     */
    @Override
    public int updateOauthClientDetails(OauthClientDetails oauthClientDetails)
    {
        return oauthClientDetailsMapper.updateOauthClientDetails(oauthClientDetails);
    }

    /**
     * 批量删除客户端信息
     * 
     * @param clientIds 需要删除的客户端信息主键
     * @return 结果
     */
    @Override
    public int deleteOauthClientDetailsByClientIds(String[] clientIds)
    {
        return oauthClientDetailsMapper.deleteOauthClientDetailsByClientIds(clientIds);
    }

    /**
     * 删除客户端信息信息
     * 
     * @param clientId 客户端信息主键
     * @return 结果
     */
    @Override
    public int deleteOauthClientDetailsByClientId(String clientId)
    {
        return oauthClientDetailsMapper.deleteOauthClientDetailsByClientId(clientId);
    }
}
