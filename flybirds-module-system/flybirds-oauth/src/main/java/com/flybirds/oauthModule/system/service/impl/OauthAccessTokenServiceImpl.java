package com.flybirds.oauthModule.system.service.impl;

import com.flybirds.common.util.date.DateUtils;
import com.flybirds.oauthModule.system.entity.OauthAccessToken;
import com.flybirds.oauthModule.system.mapper.OauthAccessTokenMapper;
import com.flybirds.oauthModule.system.service.IOauthAccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户token信息Service业务层处理
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@Service
public class OauthAccessTokenServiceImpl implements IOauthAccessTokenService 
{
    @Autowired
    private OauthAccessTokenMapper oauthAccessTokenMapper;

    /**
     * 查询用户token信息
     * 
     * @param authenticationId 用户token信息主键
     * @return 用户token信息
     */
    @Override
    public OauthAccessToken selectOauthAccessTokenByAuthenticationId(String authenticationId)
    {
        return oauthAccessTokenMapper.selectOauthAccessTokenByAuthenticationId(authenticationId);
    }

    /**
     * 查询用户token信息列表
     * 
     * @param oauthAccessToken 用户token信息
     * @return 用户token信息
     */
    @Override
    public List<OauthAccessToken> selectOauthAccessTokenList(OauthAccessToken oauthAccessToken)
    {
        return oauthAccessTokenMapper.selectOauthAccessTokenList(oauthAccessToken);
    }

    /**
     * 新增用户token信息
     * 
     * @param oauthAccessToken 用户token信息
     * @return 结果
     */
    @Override
    public int insertOauthAccessToken(OauthAccessToken oauthAccessToken)
    {
        oauthAccessToken.setCreateTime(DateUtils.getNowDate());
        return oauthAccessTokenMapper.insertOauthAccessToken(oauthAccessToken);
    }

    /**
     * 修改用户token信息
     * 
     * @param oauthAccessToken 用户token信息
     * @return 结果
     */
    @Override
    public int updateOauthAccessToken(OauthAccessToken oauthAccessToken)
    {
        return oauthAccessTokenMapper.updateOauthAccessToken(oauthAccessToken);
    }

    /**
     * 批量删除用户token信息
     * 
     * @param authenticationIds 需要删除的用户token信息主键
     * @return 结果
     */
    @Override
    public int deleteOauthAccessTokenByAuthenticationIds(String[] authenticationIds)
    {
        return oauthAccessTokenMapper.deleteOauthAccessTokenByAuthenticationIds(authenticationIds);
    }

    /**
     * 删除用户token信息信息
     * 
     * @param authenticationId 用户token信息主键
     * @return 结果
     */
    @Override
    public int deleteOauthAccessTokenByAuthenticationId(String authenticationId)
    {
        return oauthAccessTokenMapper.deleteOauthAccessTokenByAuthenticationId(authenticationId);
    }
}
