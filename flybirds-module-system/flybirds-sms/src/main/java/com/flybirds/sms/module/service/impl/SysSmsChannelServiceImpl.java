package com.flybirds.sms.module.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flybirds.common.constant.MsgConstant;
import com.flybirds.common.util.aes.AESUtil;
import com.flybirds.common.util.result.Result;
import com.flybirds.mybatis.core.basepojo.MpBaseEntity;
import com.flybirds.mybatis.core.condition.Params;
import com.flybirds.mybatis.core.page.PageResult;
import com.flybirds.sms.module.convert.SysSmsChannelConvert;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelCreateReqVO;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelPageReqVO;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelUpdateReqVO;
import com.flybirds.sms.module.entity.SysSmsChannel;
import com.flybirds.sms.module.mapper.SysSmsChannelMapper;
import com.flybirds.sms.module.service.SysSmsChannelService;
import com.flybirds.sms.module.service.SysSmsTemplateService;
import com.flybirds.sms.mq.producer.SysSmsProducer;
import com.flybirds.smsprovider.client.property.SmsChannelProperties;
import com.flybirds.smsprovider.factory.SmsClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.flybirds.common.exception.enums.SysErrorCodeConstants.SMS_CHANNEL_HAS_CHILDREN;
import static com.flybirds.common.exception.enums.SysErrorCodeConstants.SMS_CHANNEL_NOT_EXISTS;
import static com.flybirds.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 短信渠道Service实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class SysSmsChannelServiceImpl implements SysSmsChannelService {

    /**
     * 定时执行 {@link #schedulePeriodicRefresh()} 的周期
     * 因为已经通过 Redis Pub/Sub 机制，所以频率不需要高
     */
    private static final long SCHEDULER_PERIOD = 5 * 60 * 1000L;

    private static final String SMS_AES_SECRET_KEY = "f67bbb6b62f6d22650ea5ef3";

    /**
     * 缓存菜单的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    private volatile Date maxUpdateTime;

    @Resource
    private SmsClientFactory smsClientFactory;

    @Resource
    private SysSmsChannelMapper smsChannelMapper;

    @Resource
    private SysSmsTemplateService smsTemplateService;

    @Resource
    private SysSmsProducer smsProducer;

    @Override
    @PostConstruct
    public void initSmsClients() {
        // 获取短信渠道，如果有更新
        List<SysSmsChannel> smsChannels = this.loadSmsChannelIfUpdate(maxUpdateTime);
        if (CollUtil.isEmpty(smsChannels)) {
            return;
        }

        // 创建或更新短信 Client
        List<SmsChannelProperties> propertiesList = SysSmsChannelConvert.INSTANCE.convertList02(smsChannels);
        propertiesList.forEach(properties -> {
            //解密处理
            properties.setApiKey(AESUtil.decrypt(properties.getApiKey(),SMS_AES_SECRET_KEY));
            properties.setApiSecret(AESUtil.decrypt(properties.getApiSecret(),SMS_AES_SECRET_KEY));
            smsClientFactory.createOrUpdateSmsClient(properties);
        });

        // 写入缓存
        assert smsChannels.size() > 0; // 断言，避免告警
        maxUpdateTime = smsChannels.stream().max(Comparator.comparing(MpBaseEntity::getUpdateTime)).get().getUpdateTime();
        log.info("[initSmsClients][初始化 SmsChannel 数量为 {}]", smsChannels.size());
    }

    @Scheduled(fixedDelay = SCHEDULER_PERIOD, initialDelay = SCHEDULER_PERIOD)
    public void schedulePeriodicRefresh() {
        initSmsClients();
    }

    /**
     * 如果短信渠道发生变化，从数据库中获取最新的全量短信渠道。
     * 如果未发生变化，则返回空
     *
     * @param maxUpdateTime 当前短信渠道的最大更新时间
     * @return 短信渠道列表
     */
    private List<SysSmsChannel> loadSmsChannelIfUpdate(Date maxUpdateTime) {
        // 第一步，判断是否要更新。
        if (maxUpdateTime == null) { // 如果更新时间为空，说明 DB 一定有新数据
            log.info("[loadSmsChannelIfUpdate][首次加载全量短信渠道]");
        } else { // 判断数据库中是否有更新的短信渠道
            if (smsChannelMapper.selectExistsByUpdateTimeAfter(maxUpdateTime) == null) {
                return null;
            }
            log.info("[loadSmsChannelIfUpdate][增量加载全量短信渠道]");
        }
        // 第二步，如果有更新，则从数据库加载所有短信渠道
        return smsChannelMapper.selectList();
    }

    /**
     * todo 未使用
     * @param saveSmsChannel
     * @return
     */
    @Override
    public Result<String> saveSmsChannel(SysSmsChannel saveSmsChannel) {
        // 插入
        smsChannelMapper.insert(saveSmsChannel);
        // 发送刷新消息
        smsProducer.sendSmsChannelRefreshMessage();
        // 返回
        return Result.ok(MsgConstant.Business.SAVE_SUCCESS);
    }

    /**
     * @param createReqVO 创建信息
     * @return
     */
    @Override
    public Long createSmsChannel(@Valid SysSmsChannelCreateReqVO createReqVO) {
        // 插入
        SysSmsChannel smsChannel = SysSmsChannelConvert.INSTANCE.convert(createReqVO);
        //加密
        smsChannel.setApiKey(AESUtil.encrypt(smsChannel.getApiKey(),SMS_AES_SECRET_KEY));
        smsChannel.setApiSecret(AESUtil.encrypt(smsChannel.getApiSecret(),SMS_AES_SECRET_KEY));
        smsChannelMapper.insert(smsChannel);
        // 发送刷新消息
        smsProducer.sendSmsChannelRefreshMessage();
        // 返回
        return smsChannel.getId();
    }

    @Override
    public Result<String> updateSmsChannel(SysSmsChannelUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateSmsChannelExists(updateReqVO.getId());
        // 更新
        SysSmsChannel updateObj = SysSmsChannelConvert.INSTANCE.convert(updateReqVO);
        smsChannelMapper.updateById(updateObj);
        // 发送刷新消息
        smsProducer.sendSmsChannelRefreshMessage();
        return Result.ok(MsgConstant.Business.UPDATE_SUCCESS);
    }

    @Override
    public Result<?> deleteSmsChannel(Long id) {
        // 校验存在
        this.validateSmsChannelExists(id);
        // 校验是否有字典数据
        if (smsTemplateService.countByChannelId(id) > 0) {
            throw exception(SMS_CHANNEL_HAS_CHILDREN);
        }
        // 删除
        smsChannelMapper.deleteById(id);
        // 发送刷新消息
        smsProducer.sendSmsChannelRefreshMessage();

        return Result.ok(MsgConstant.Business.DELETE_LOGIC_SUCCESS);
    }

    private void validateSmsChannelExists(Long id) {
        if (smsChannelMapper.selectById(id) == null) {
            throw exception(SMS_CHANNEL_NOT_EXISTS);
        }
    }

    @Override
    public SysSmsChannel getSmsChannel(Long id) {
        return smsChannelMapper.selectById(id);
    }

    @Override
    public List<SysSmsChannel> getSmsChannelList(Collection<Long> ids) {
        return smsChannelMapper.selectBatchIds(ids);
    }

    @Override
    public List<SysSmsChannel> getSmsChannelList() {
        return smsChannelMapper.selectList();
    }

    @Override
    public IPage<SysSmsChannel> getSmsChannelPage(Params params) {
        return smsChannelMapper.selectPage(params.getPage(),params.getConditions());
    }

    @Override
    public PageResult<SysSmsChannel> getSmsChannelPage(@Valid SysSmsChannelPageReqVO pageVO) {
        return smsChannelMapper.selectPage(pageVO);
    }

}
