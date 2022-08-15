package com.flybirds.oauthModule.system.service;

import java.util.List;
import com.flybirds.oauthModule.system.entity.OauthAppDetails;

/**
 * 应用信息appService接口
 * 
 * @author flybirds
 * @date 2022-07-19
 */
public interface IOauthAppDetailsService 
{
    /**
     * 查询应用信息app
     * 
     * @param id 应用信息app主键
     * @return 应用信息app
     */
    public OauthAppDetails selectOauthAppDetailsById(Long id);

    /**
     * 查询应用信息app列表
     * 
     * @param oauthAppDetails 应用信息app
     * @return 应用信息app集合
     */
    public List<OauthAppDetails> selectOauthAppDetailsList(OauthAppDetails oauthAppDetails);

    /**
     * 新增应用信息app
     * 
     * @param oauthAppDetails 应用信息app
     * @return 结果
     */
    public int insertOauthAppDetails(OauthAppDetails oauthAppDetails);

    /**
     * 修改应用信息app
     * 
     * @param oauthAppDetails 应用信息app
     * @return 结果
     */
    public int updateOauthAppDetails(OauthAppDetails oauthAppDetails);

    /**
     * 批量删除应用信息app
     * 
     * @param ids 需要删除的应用信息app主键集合
     * @return 结果
     */
    public int deleteOauthAppDetailsByIds(Long[] ids);

    /**
     * 删除应用信息app信息
     * 
     * @param id 应用信息app主键
     * @return 结果
     */
    public int deleteOauthAppDetailsById(Long id);
}
