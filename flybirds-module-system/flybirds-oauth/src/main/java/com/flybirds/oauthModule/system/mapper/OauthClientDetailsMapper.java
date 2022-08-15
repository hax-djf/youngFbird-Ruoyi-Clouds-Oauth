package com.flybirds.oauthModule.system.mapper;

import java.util.List;
import com.flybirds.oauthModule.system.entity.OauthClientDetails;

/**
 * 客户端信息Mapper接口
 * 
 * @author flybirds
 * @date 2022-07-19
 */
public interface OauthClientDetailsMapper 
{
    /**
     * 查询客户端信息
     * 
     * @param clientId 客户端信息主键
     * @return 客户端信息
     */
    public OauthClientDetails selectOauthClientDetailsByClientId(String clientId);

    /**
     * 查询客户端信息列表
     * 
     * @param oauthClientDetails 客户端信息
     * @return 客户端信息集合
     */
    public List<OauthClientDetails> selectOauthClientDetailsList(OauthClientDetails oauthClientDetails);

    /**
     * 新增客户端信息
     * 
     * @param oauthClientDetails 客户端信息
     * @return 结果
     */
    public int insertOauthClientDetails(OauthClientDetails oauthClientDetails);

    /**
     * 修改客户端信息
     * 
     * @param oauthClientDetails 客户端信息
     * @return 结果
     */
    public int updateOauthClientDetails(OauthClientDetails oauthClientDetails);

    /**
     * 删除客户端信息
     * 
     * @param clientId 客户端信息主键
     * @return 结果
     */
    public int deleteOauthClientDetailsByClientId(String clientId);

    /**
     * 批量删除客户端信息
     * 
     * @param clientIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOauthClientDetailsByClientIds(String[] clientIds);
}
