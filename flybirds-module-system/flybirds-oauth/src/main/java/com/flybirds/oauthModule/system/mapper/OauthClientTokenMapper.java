package com.flybirds.oauthModule.system.mapper;

import java.util.List;
import com.flybirds.oauthModule.system.entity.OauthClientToken;

/**
 * 客户端tokenMapper接口
 * 
 * @author flybirds
 * @date 2022-07-19
 */
public interface OauthClientTokenMapper 
{
    /**
     * 查询客户端token
     * 
     * @param authenticationId 客户端token主键
     * @return 客户端token
     */
    public OauthClientToken selectOauthClientTokenByAuthenticationId(String authenticationId);

    /**
     * 查询客户端token列表
     * 
     * @param oauthClientToken 客户端token
     * @return 客户端token集合
     */
    public List<OauthClientToken> selectOauthClientTokenList(OauthClientToken oauthClientToken);

    /**
     * 新增客户端token
     * 
     * @param oauthClientToken 客户端token
     * @return 结果
     */
    public int insertOauthClientToken(OauthClientToken oauthClientToken);

    /**
     * 修改客户端token
     * 
     * @param oauthClientToken 客户端token
     * @return 结果
     */
    public int updateOauthClientToken(OauthClientToken oauthClientToken);

    /**
     * 删除客户端token
     * 
     * @param authenticationId 客户端token主键
     * @return 结果
     */
    public int deleteOauthClientTokenByAuthenticationId(String authenticationId);

    /**
     * 批量删除客户端token
     * 
     * @param authenticationIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOauthClientTokenByAuthenticationIds(String[] authenticationIds);
}
