package com.flybirds.oauthModule.system.service.impl;

import com.flybirds.common.util.date.DateUtils;
import com.flybirds.oauthModule.system.entity.OauthClientToken;
import com.flybirds.oauthModule.system.mapper.OauthClientTokenMapper;
import com.flybirds.oauthModule.system.service.IOauthClientTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户端tokenService业务层处理
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@Service
public class OauthClientTokenServiceImpl implements IOauthClientTokenService 
{
    @Autowired
    private OauthClientTokenMapper oauthClientTokenMapper;

    /**
     * 查询客户端token
     * 
     * @param authenticationId 客户端token主键
     * @return 客户端token
     */
    @Override
    public OauthClientToken selectOauthClientTokenByAuthenticationId(String authenticationId)
    {
        return oauthClientTokenMapper.selectOauthClientTokenByAuthenticationId(authenticationId);
    }

    /**
     * 查询客户端token列表
     * 
     * @param oauthClientToken 客户端token
     * @return 客户端token
     */
    @Override
    public List<OauthClientToken> selectOauthClientTokenList(OauthClientToken oauthClientToken)
    {
        return oauthClientTokenMapper.selectOauthClientTokenList(oauthClientToken);
    }

    /**
     * 新增客户端token
     * 
     * @param oauthClientToken 客户端token
     * @return 结果
     */
    @Override
    public int insertOauthClientToken(OauthClientToken oauthClientToken)
    {
        oauthClientToken.setCreateTime(DateUtils.getNowDate());
        return oauthClientTokenMapper.insertOauthClientToken(oauthClientToken);
    }

    /**
     * 修改客户端token
     * 
     * @param oauthClientToken 客户端token
     * @return 结果
     */
    @Override
    public int updateOauthClientToken(OauthClientToken oauthClientToken)
    {
        return oauthClientTokenMapper.updateOauthClientToken(oauthClientToken);
    }

    /**
     * 批量删除客户端token
     * 
     * @param authenticationIds 需要删除的客户端token主键
     * @return 结果
     */
    @Override
    public int deleteOauthClientTokenByAuthenticationIds(String[] authenticationIds)
    {
        return oauthClientTokenMapper.deleteOauthClientTokenByAuthenticationIds(authenticationIds);
    }

    /**
     * 删除客户端token信息
     * 
     * @param authenticationId 客户端token主键
     * @return 结果
     */
    @Override
    public int deleteOauthClientTokenByAuthenticationId(String authenticationId)
    {
        return oauthClientTokenMapper.deleteOauthClientTokenByAuthenticationId(authenticationId);
    }
}
