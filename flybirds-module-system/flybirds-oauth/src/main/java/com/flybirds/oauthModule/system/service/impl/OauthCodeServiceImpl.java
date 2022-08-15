package com.flybirds.oauthModule.system.service.impl;

import com.flybirds.common.util.date.DateUtils;
import com.flybirds.oauthModule.system.entity.OauthCode;
import com.flybirds.oauthModule.system.mapper.OauthCodeMapper;
import com.flybirds.oauthModule.system.service.IOauthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 授权码信息Service业务层处理
 * 
 * @author flybirds
 * @date 2022-07-19
 */
@Service
public class OauthCodeServiceImpl implements IOauthCodeService 
{
    @Autowired
    private OauthCodeMapper oauthCodeMapper;

    /**
     * 查询授权码信息
     * 
     * @param createTime 授权码信息主键
     * @return 授权码信息
     */
    @Override
    public OauthCode selectOauthCodeByCreateTime(Date createTime)
    {
        return oauthCodeMapper.selectOauthCodeByCreateTime(createTime);
    }

    /**
     * 查询授权码信息列表
     * 
     * @param oauthCode 授权码信息
     * @return 授权码信息
     */
    @Override
    public List<OauthCode> selectOauthCodeList(OauthCode oauthCode)
    {
        return oauthCodeMapper.selectOauthCodeList(oauthCode);
    }

    /**
     * 新增授权码信息
     * 
     * @param oauthCode 授权码信息
     * @return 结果
     */
    @Override
    public int insertOauthCode(OauthCode oauthCode)
    {
        oauthCode.setCreateTime(DateUtils.getNowDate());
        return oauthCodeMapper.insertOauthCode(oauthCode);
    }

    /**
     * 修改授权码信息
     * 
     * @param oauthCode 授权码信息
     * @return 结果
     */
    @Override
    public int updateOauthCode(OauthCode oauthCode)
    {
        return oauthCodeMapper.updateOauthCode(oauthCode);
    }

    /**
     * 批量删除授权码信息
     * 
     * @param createTimes 需要删除的授权码信息主键
     * @return 结果
     */
    @Override
    public int deleteOauthCodeByCreateTimes(Date[] createTimes)
    {
        return oauthCodeMapper.deleteOauthCodeByCreateTimes(createTimes);
    }

    /**
     * 删除授权码信息信息
     * 
     * @param createTime 授权码信息主键
     * @return 结果
     */
    @Override
    public int deleteOauthCodeByCreateTime(Date createTime)
    {
        return oauthCodeMapper.deleteOauthCodeByCreateTime(createTime);
    }
}
