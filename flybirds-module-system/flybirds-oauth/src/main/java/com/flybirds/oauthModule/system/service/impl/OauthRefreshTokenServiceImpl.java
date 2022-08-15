package com.flybirds.oauthModule.system.service.impl;

import com.flybirds.common.util.date.DateUtils;
import com.flybirds.oauthModule.system.entity.OauthRefreshToken;
import com.flybirds.oauthModule.system.mapper.OauthRefreshTokenMapper;
import com.flybirds.oauthModule.system.service.IOauthRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * token刷新Service业务层处理
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@Service
public class OauthRefreshTokenServiceImpl implements IOauthRefreshTokenService 
{
    @Autowired
    private OauthRefreshTokenMapper oauthRefreshTokenMapper;

    /**
     * 查询token刷新
     * 
     * @param tokenId token刷新主键
     * @return token刷新
     */
    @Override
    public OauthRefreshToken selectOauthRefreshTokenByTokenId(String tokenId)
    {
        return oauthRefreshTokenMapper.selectOauthRefreshTokenByTokenId(tokenId);
    }

    /**
     * 查询token刷新列表
     * 
     * @param oauthRefreshToken token刷新
     * @return token刷新
     */
    @Override
    public List<OauthRefreshToken> selectOauthRefreshTokenList(OauthRefreshToken oauthRefreshToken)
    {
        return oauthRefreshTokenMapper.selectOauthRefreshTokenList(oauthRefreshToken);
    }

    /**
     * 新增token刷新
     * 
     * @param oauthRefreshToken token刷新
     * @return 结果
     */
    @Override
    public int insertOauthRefreshToken(OauthRefreshToken oauthRefreshToken)
    {
        oauthRefreshToken.setCreateTime(DateUtils.getNowDate());
        return oauthRefreshTokenMapper.insertOauthRefreshToken(oauthRefreshToken);
    }

    /**
     * 修改token刷新
     * 
     * @param oauthRefreshToken token刷新
     * @return 结果
     */
    @Override
    public int updateOauthRefreshToken(OauthRefreshToken oauthRefreshToken)
    {
        return oauthRefreshTokenMapper.updateOauthRefreshToken(oauthRefreshToken);
    }

    /**
     * 批量删除token刷新
     * 
     * @param tokenIds 需要删除的token刷新主键
     * @return 结果
     */
    @Override
    public int deleteOauthRefreshTokenByTokenIds(String[] tokenIds)
    {
        return oauthRefreshTokenMapper.deleteOauthRefreshTokenByTokenIds(tokenIds);
    }

    /**
     * 删除token刷新信息
     * 
     * @param tokenId token刷新主键
     * @return 结果
     */
    @Override
    public int deleteOauthRefreshTokenByTokenId(String tokenId)
    {
        return oauthRefreshTokenMapper.deleteOauthRefreshTokenByTokenId(tokenId);
    }
}
