package com.flybirds.oauthModule.system.mapper;

import java.util.List;
import com.flybirds.oauthModule.system.entity.OauthRefreshToken;

/**
 * token刷新Mapper接口
 * 
 * @author flybirds
 * @date 2022-07-19
 */
public interface OauthRefreshTokenMapper 
{
    /**
     * 查询token刷新
     * 
     * @param tokenId token刷新主键
     * @return token刷新
     */
    public OauthRefreshToken selectOauthRefreshTokenByTokenId(String tokenId);

    /**
     * 查询token刷新列表
     * 
     * @param oauthRefreshToken token刷新
     * @return token刷新集合
     */
    public List<OauthRefreshToken> selectOauthRefreshTokenList(OauthRefreshToken oauthRefreshToken);

    /**
     * 新增token刷新
     * 
     * @param oauthRefreshToken token刷新
     * @return 结果
     */
    public int insertOauthRefreshToken(OauthRefreshToken oauthRefreshToken);

    /**
     * 修改token刷新
     * 
     * @param oauthRefreshToken token刷新
     * @return 结果
     */
    public int updateOauthRefreshToken(OauthRefreshToken oauthRefreshToken);

    /**
     * 删除token刷新
     * 
     * @param tokenId token刷新主键
     * @return 结果
     */
    public int deleteOauthRefreshTokenByTokenId(String tokenId);

    /**
     * 批量删除token刷新
     * 
     * @param tokenIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOauthRefreshTokenByTokenIds(String[] tokenIds);
}
