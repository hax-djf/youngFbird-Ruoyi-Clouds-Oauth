package com.flybirds.sms.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flybirds.common.util.result.Result;
import com.flybirds.mybatis.core.condition.Params;
import com.flybirds.mybatis.core.page.PageResult;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelCreateReqVO;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelPageReqVO;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelUpdateReqVO;
import com.flybirds.sms.module.entity.SysSmsChannel;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 短信渠道Service接口
 *
 * @author zzf
 * @date 2021/1/25 9:24
 */
public interface SysSmsChannelService {

    /**
     * 初始化短信客户端
     */
    void initSmsClients();

    /**
     * 创建短信渠道
     *
     * @param sysSmsChannel 创建信息
     * @return 编号
     */
    Result<?> saveSmsChannel(@Valid SysSmsChannel sysSmsChannel);


    /**
     * 创建短信渠道
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSmsChannel(@Valid SysSmsChannelCreateReqVO createReqVO);

    /**
     * 更新短信渠道
     *
     * @param updateReqVO 更新信息
     */
    Result<?> updateSmsChannel(@Valid SysSmsChannelUpdateReqVO updateReqVO);

    /**
     * 删除短信渠道
     *
     * @param id 编号
     */
    Result<?> deleteSmsChannel(Long id);

    /**
     * 获得短信渠道
     *
     * @param id 编号
     * @return 短信渠道
     */
    SysSmsChannel getSmsChannel(Long id);

    /**
     * 获得短信渠道列表
     *
     * @param ids 编号
     * @return 短信渠道列表
     */
    List<SysSmsChannel> getSmsChannelList(Collection<Long> ids);

    /**
     * 获得所有短信渠道列表
     *
     * @return 短信渠道列表
     */
    List<SysSmsChannel> getSmsChannelList();

    /**
     * 获得短信渠道分页
     *
     * @param params 分页查询
     * @return 短信渠道分页
     */
    IPage<SysSmsChannel> getSmsChannelPage(Params params);

    /**
     * 获取短信渠道
     * @param pageVO
     * @return
     */
    PageResult<SysSmsChannel> getSmsChannelPage(@Valid SysSmsChannelPageReqVO pageVO);
}
