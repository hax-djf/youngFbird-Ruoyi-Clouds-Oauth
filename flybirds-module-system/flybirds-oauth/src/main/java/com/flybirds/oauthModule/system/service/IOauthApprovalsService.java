package com.flybirds.oauthModule.system.service;

import java.util.List;
import com.flybirds.oauthModule.system.entity.OauthApprovals;

/**
 * 授权阈Service接口
 * 
 * @author flybirds
 * @date 2022-07-19
 */
public interface IOauthApprovalsService 
{
    /**
     * 查询授权阈
     * 
     * @param userid 授权阈主键
     * @return 授权阈
     */
    public OauthApprovals selectOauthApprovalsByUserid(String userid);

    /**
     * 查询授权阈列表
     * 
     * @param oauthApprovals 授权阈
     * @return 授权阈集合
     */
    public List<OauthApprovals> selectOauthApprovalsList(OauthApprovals oauthApprovals);

    /**
     * 新增授权阈
     * 
     * @param oauthApprovals 授权阈
     * @return 结果
     */
    public int insertOauthApprovals(OauthApprovals oauthApprovals);

    /**
     * 修改授权阈
     * 
     * @param oauthApprovals 授权阈
     * @return 结果
     */
    public int updateOauthApprovals(OauthApprovals oauthApprovals);

    /**
     * 批量删除授权阈
     * 
     * @param userids 需要删除的授权阈主键集合
     * @return 结果
     */
    public int deleteOauthApprovalsByUserids(String[] userids);

    /**
     * 删除授权阈信息
     * 
     * @param userid 授权阈主键
     * @return 结果
     */
    public int deleteOauthApprovalsByUserid(String userid);
}
