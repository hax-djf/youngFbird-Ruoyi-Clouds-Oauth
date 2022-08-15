package com.flybirds.oauthModule.system.service.impl;

import com.flybirds.common.util.date.DateUtils;
import com.flybirds.oauthModule.system.entity.OauthApprovals;
import com.flybirds.oauthModule.system.mapper.OauthApprovalsMapper;
import com.flybirds.oauthModule.system.service.IOauthApprovalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 授权阈Service业务层处理
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@Service
public class OauthApprovalsServiceImpl implements IOauthApprovalsService 
{
    @Autowired
    private OauthApprovalsMapper oauthApprovalsMapper;

    /**
     * 查询授权阈
     * 
     * @param userid 授权阈主键
     * @return 授权阈
     */
    @Override
    public OauthApprovals selectOauthApprovalsByUserid(String userid)
    {
        return oauthApprovalsMapper.selectOauthApprovalsByUserid(userid);
    }

    /**
     * 查询授权阈列表
     * 
     * @param oauthApprovals 授权阈
     * @return 授权阈
     */
    @Override
    public List<OauthApprovals> selectOauthApprovalsList(OauthApprovals oauthApprovals)
    {
        return oauthApprovalsMapper.selectOauthApprovalsList(oauthApprovals);
    }

    /**
     * 新增授权阈
     * 
     * @param oauthApprovals 授权阈
     * @return 结果
     */
    @Override
    public int insertOauthApprovals(OauthApprovals oauthApprovals)
    {
        oauthApprovals.setCreateTime(DateUtils.getNowDate());
        return oauthApprovalsMapper.insertOauthApprovals(oauthApprovals);
    }

    /**
     * 修改授权阈
     * 
     * @param oauthApprovals 授权阈
     * @return 结果
     */
    @Override
    public int updateOauthApprovals(OauthApprovals oauthApprovals)
    {
        return oauthApprovalsMapper.updateOauthApprovals(oauthApprovals);
    }

    /**
     * 批量删除授权阈
     * 
     * @param userids 需要删除的授权阈主键
     * @return 结果
     */
    @Override
    public int deleteOauthApprovalsByUserids(String[] userids)
    {
        return oauthApprovalsMapper.deleteOauthApprovalsByUserids(userids);
    }

    /**
     * 删除授权阈信息
     * 
     * @param userid 授权阈主键
     * @return 结果
     */
    @Override
    public int deleteOauthApprovalsByUserid(String userid)
    {
        return oauthApprovalsMapper.deleteOauthApprovalsByUserid(userid);
    }
}
