package com.flybirds.oauthModule.system.mapper;

import com.flybirds.oauthModule.system.entity.OauthCode;

import java.util.Date;
import java.util.List;

/**
 * 授权码信息Mapper接口
 * 
 * @author flybirds
 * @date 2022-07-19
 */
public interface OauthCodeMapper 
{
    /**
     * 查询授权码信息
     * 
     * @param createTime 授权码信息主键
     * @return 授权码信息
     */
    public OauthCode selectOauthCodeByCreateTime(Date createTime);

    /**
     * 查询授权码信息列表
     * 
     * @param oauthCode 授权码信息
     * @return 授权码信息集合
     */
    public List<OauthCode> selectOauthCodeList(OauthCode oauthCode);

    /**
     * 新增授权码信息
     * 
     * @param oauthCode 授权码信息
     * @return 结果
     */
    public int insertOauthCode(OauthCode oauthCode);

    /**
     * 修改授权码信息
     * 
     * @param oauthCode 授权码信息
     * @return 结果
     */
    public int updateOauthCode(OauthCode oauthCode);

    /**
     * 删除授权码信息
     * 
     * @param createTime 授权码信息主键
     * @return 结果
     */
    public int deleteOauthCodeByCreateTime(Date createTime);

    /**
     * 批量删除授权码信息
     * 
     * @param createTimes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOauthCodeByCreateTimes(Date[] createTimes);
}
