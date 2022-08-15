package com.flybirds.oauthModule.system.mapper;

import java.util.List;
import com.flybirds.oauthModule.system.entity.OauthAccessToken;

/**
 * 用户token信息Mapper接口
 * 
 * @author flybirds
 * @date 2022-07-19
 */
public interface OauthAccessTokenMapper 
{
    /**
     * 查询用户token信息
     * 
     * @param authenticationId 用户token信息主键
     * @return 用户token信息
     */
    public OauthAccessToken selectOauthAccessTokenByAuthenticationId(String authenticationId);

    /**
     * 查询用户token信息列表
     * 
     * @param oauthAccessToken 用户token信息
     * @return 用户token信息集合
     */
    public List<OauthAccessToken> selectOauthAccessTokenList(OauthAccessToken oauthAccessToken);

    /**
     * 新增用户token信息
     * 
     * @param oauthAccessToken 用户token信息
     * @return 结果
     */
    public int insertOauthAccessToken(OauthAccessToken oauthAccessToken);

    /**
     * 修改用户token信息
     * 
     * @param oauthAccessToken 用户token信息
     * @return 结果
     */
    public int updateOauthAccessToken(OauthAccessToken oauthAccessToken);

    /**
     * 删除用户token信息
     * 
     * @param authenticationId 用户token信息主键
     * @return 结果
     */
    public int deleteOauthAccessTokenByAuthenticationId(String authenticationId);

    /**
     * 批量删除用户token信息
     * 
     * @param authenticationIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOauthAccessTokenByAuthenticationIds(String[] authenticationIds);
}
