package com.flybirds.sms.module.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flybirds.common.constant.MsgConstant;
import com.flybirds.common.enums.CommonStatusEnum;
import com.flybirds.common.util.date.LocalDateTimeUtils;
import com.flybirds.common.util.result.Result;
import com.flybirds.excel.utils.poi.POIExcelUtil;
import com.flybirds.mybatis.core.condition.Params;
import com.flybirds.mybatis.core.page.PageResult;
import com.flybirds.sms.module.convert.SysSmsTemplateConvert;
import com.flybirds.sms.module.core.vo.template.SysSmsTemplateCreateReqVO;
import com.flybirds.sms.module.core.vo.template.SysSmsTemplateExportReqVO;
import com.flybirds.sms.module.core.vo.template.SysSmsTemplatePageReqVO;
import com.flybirds.sms.module.core.vo.template.SysSmsTemplateUpdateReqVO;
import com.flybirds.sms.module.entity.SysSmsChannel;
import com.flybirds.sms.module.entity.SysSmsTemplate;
import com.flybirds.sms.module.mapper.SysSmsTemplateMapper;
import com.flybirds.sms.module.service.SysSmsChannelService;
import com.flybirds.sms.module.service.SysSmsTemplateService;
import com.flybirds.sms.mq.producer.SysSmsProducer;
import com.flybirds.smsprovider.client.SmsClient;
import com.flybirds.smsprovider.client.core.SmsResult;
import com.flybirds.smsprovider.client.core.dto.SmsTemplateRespDTO;
import com.flybirds.smsprovider.factory.SmsClientFactory;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.regex.Pattern;

import static com.flybirds.common.exception.enums.SysErrorCodeConstants.*;
import static com.flybirds.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 短信模板Service实现类
 *
 * @author zzf
 * @date 2021/1/25 9:25
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class SysSmsTemplateServiceImpl implements SysSmsTemplateService {

    /**
     * 正则表达式，匹配 {} 中的变量
     */
    private static final Pattern PATTERN_PARAMS = Pattern.compile("\\{(.*?)}");

    /**
     * 定时执行 {@link #schedulePeriodicRefresh()} 的周期
     * 因为已经通过 Redis Pub/Sub 机制，所以频率不需要高
     */
    private static final long SCHEDULER_PERIOD = 5 * 60 * 1000L;

    /**
     * 短信模板缓存
     * key：短信模板编码 {@link SysSmsTemplateDO#getCode()}
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    private volatile Map<String, SysSmsTemplate> smsTemplateCache;
    /**
     * 缓存短信模板的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    private volatile Date maxUpdateTime;

    @Autowired
    private SysSmsTemplateMapper smsTemplateMapper;

    @Autowired
    private SysSmsChannelService smsChannelService;

    @Autowired
    private SmsClientFactory smsClientFactory;

    @Autowired
    private SysSmsProducer smsProducer;

    /**
     * 初始化 {@link #smsTemplateCache} 缓存
     */
    @Override
    @PostConstruct
    public void initLocalCache() {
        // 获取短信模板列表，如果有更新
        List<SysSmsTemplate> smsTemplateList = this.loadSmsTemplateIfUpdate(maxUpdateTime);
        if (CollUtil.isEmpty(smsTemplateList)) {
            return;
        }

        // 写入缓存 ->google 工具类ImmutableMap
        ImmutableMap.Builder<String, SysSmsTemplate> builder = ImmutableMap.builder();
        smsTemplateList.forEach(sysSmsTemplateDO -> builder.put(sysSmsTemplateDO.getCode(), sysSmsTemplateDO));
        smsTemplateCache = builder.build();
        assert smsTemplateList.size() > 0; // 断言，避免告警
        maxUpdateTime = LocalDateTimeUtils.getDateFromLocalDateTime(smsTemplateList.stream().max(
                Comparator.comparing(data ->{
                    return  Optional.ofNullable(data.getUpdateTime())
                            .orElseGet(data::getCreateTime);
                })
        ).get().getUpdateTime());

        log.info("[initLocalCache][初始化 SmsTemplate 数量为 {}]", smsTemplateList.size());
    }

    @Scheduled(fixedDelay = SCHEDULER_PERIOD, initialDelay = SCHEDULER_PERIOD)
    public void schedulePeriodicRefresh() {
        initLocalCache();
    }

    /**
     * 如果短信模板发生变化，从数据库中获取最新的全量短信模板。
     * 如果未发生变化，则返回空
     *
     * @param maxUpdateTime 当前短信模板的最大更新时间
     * @return 短信模板列表
     */
    private List<SysSmsTemplate> loadSmsTemplateIfUpdate(Date maxUpdateTime) {
        // 第一步，判断是否要更新。
        if (maxUpdateTime == null) { // 如果更新时间为空，说明 DB 一定有新数据
            log.info("[loadSmsTemplateIfUpdate][首次加载全量短信模板]");
        } else { // 判断数据库中是否有更新的短信模板
            if (smsTemplateMapper.selectExistsByUpdateTimeAfter(maxUpdateTime) == null) {
                return null;
            }
            log.info("[loadSmsTemplateIfUpdate][增量加载全量短信模板]");
        }
        // 第二步，如果有更新，则从数据库加载所有短信模板
        return smsTemplateMapper.selectList();
    }

    @Override
    public SysSmsTemplate getSmsTemplateByCode(String code) {
        return smsTemplateMapper.selectByCode(code);
    }

    @Override
    public SysSmsTemplate getSmsTemplateByCodeFromCache(String code) {
        return smsTemplateCache.get(code);
    }

    @Override
    public String formatSmsTemplateContent(String content, Map<String, Object> params) {
        return StrUtil.format(content, params);
    }

    @VisibleForTesting
    public List<String> parseTemplateContentParams(String content) {
        return ReUtil.findAllGroup1(PATTERN_PARAMS, content);
    }

    @Override
    public Result<?> saveSmsTemplate(SysSmsTemplate template) {
        // 校验短信渠道
        SysSmsChannel channelDO = checkSmsChannel(template.getChannelId());
        // 校验短信编码是否重复
        checkSmsTemplateCodeDuplicate(null, template.getCode());
        // 校验短信模板
        checkApiTemplate(template.getChannelId(), template.getApiTemplateId());
        // 插入
        template.setParams(parseTemplateContentParams(template.getContent()));
        template.setChannelCode(channelDO.getCode());
        smsTemplateMapper.insert(template);
        // 发送刷新消息
        smsProducer.sendSmsTemplateRefreshMessage();
        // 返回
        return Result.ok(MsgConstant.Business.SAVE_SUCCESS);
    }

    @Override
    public Long createSmsTemplate(SysSmsTemplateCreateReqVO createReqVO) {
        // 校验短信渠道
        SysSmsChannel channelDO = checkSmsChannel(createReqVO.getChannelId());
        // 校验短信编码是否重复
        checkSmsTemplateCodeDuplicate(null, createReqVO.getCode());
        // 校验短信模板
        checkApiTemplate(createReqVO.getChannelId(), createReqVO.getApiTemplateId());
        // 插入
        SysSmsTemplate template = SysSmsTemplateConvert.INSTANCE.convert(createReqVO);
        template.setParams(parseTemplateContentParams(template.getContent()));
        template.setChannelCode(channelDO.getCode());
        smsTemplateMapper.insert(template);
        // 发送刷新消息
        smsProducer.sendSmsTemplateRefreshMessage();
        // 返回
        return template.getId();
    }

    @Override
    public PageResult<SysSmsTemplate> getSmsTemplatePage(@Valid SysSmsTemplatePageReqVO pageVO) {
        return smsTemplateMapper.selectPage(pageVO);
    }

    @Override
    public List<SysSmsTemplate> getSmsTemplateList(@Valid SysSmsTemplateExportReqVO exportReqVO) {
        return smsTemplateMapper.selectList(exportReqVO);
    }

    @Override
    public Result<?> updateSmsTemplate(SysSmsTemplateUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateSmsTemplateExists(updateReqVO.getId());
        // 校验短信渠道
        SysSmsChannel channelDO = checkSmsChannel(updateReqVO.getChannelId());
        // 校验短信编码是否重复
        checkSmsTemplateCodeDuplicate(updateReqVO.getId(), updateReqVO.getCode());
        // 校验短信模板
        checkApiTemplate(updateReqVO.getChannelId(), updateReqVO.getApiTemplateId());
        // 更新
        SysSmsTemplate sysSmsTemplate = SysSmsTemplateConvert.INSTANCE.convert(updateReqVO);
        sysSmsTemplate.setParams(parseTemplateContentParams(sysSmsTemplate.getContent()));
        sysSmsTemplate.setChannelCode(channelDO.getCode());
        smsTemplateMapper.updateById(sysSmsTemplate);
        // 发送刷新消息
        smsProducer.sendSmsTemplateRefreshMessage();

        return Result.ok(MsgConstant.Business.UPDATE_SUCCESS);
    }

    @Override
    public Result<?> deleteSmsTemplate(Long id) {
        // 校验存在
        this.validateSmsTemplateExists(id);
        // 更新
        smsTemplateMapper.deleteById(id);
        // 发送刷新消息
        smsProducer.sendSmsTemplateRefreshMessage();
        return Result.ok(MsgConstant.Business.DELETE_LOGIC_SUCCESS);
    }

    private void validateSmsTemplateExists(Long id) {
        if (smsTemplateMapper.selectById(id) == null) {
            throw exception(SMS_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public SysSmsTemplate getSmsTemplate(Long id) {
        return smsTemplateMapper.selectById(id);
    }

    @Override
    public List<SysSmsTemplate> getSmsTemplateList(Collection<Long> ids) {
        return smsTemplateMapper.selectBatchIds(ids);
    }

    @Override
    public IPage<SysSmsTemplate> getSmsTemplatePage(Params params) {

        return smsTemplateMapper.selectPage(params.getPage(),params.getConditions());
    }

    @Override
    public List<SysSmsTemplate> getSmsTemplateList(Params params) {

        return smsTemplateMapper.selectList(params.getConditions());
    }

    @Override
    public Integer countByChannelId(Long channelId) {
        return smsTemplateMapper.selectCountByChannelId(channelId);
    }

    @Override
    public Result exportSmsTemplateExcel(HttpServletResponse response, @Valid Params params) {

        try {
            IPage<SysSmsTemplate> genDataModelIPage = smsTemplateMapper.selectPage(params.getPage(), params.getConditions());

            POIExcelUtil<SysSmsTemplate> util = new POIExcelUtil<SysSmsTemplate>(SysSmsTemplate.class);
            util.exportExcel(response, genDataModelIPage.getRecords(), "短信模板");
        }catch (RuntimeException e){
            log.error("数据导出失败");
            return Result.fail(MsgConstant.Business.EXPORT_FAILED);
        }

        return Result.ok(MsgConstant.Business.EXPORT_SUCCESS);
    }


    @VisibleForTesting
    public SysSmsChannel checkSmsChannel(Long channelId) {
        SysSmsChannel channelDO = smsChannelService.getSmsChannel(channelId);
        if (channelDO == null) {
            throw exception(SMS_CHANNEL_NOT_EXISTS);
        }
        if (!Objects.equals(channelDO.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw exception(SMS_CHANNEL_DISABLE);
        }
        return channelDO;
    }

    @VisibleForTesting
    public void checkSmsTemplateCodeDuplicate(Long id, String code) {
        SysSmsTemplate template = smsTemplateMapper.selectByCode(code);
        if (template == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw exception(SMS_TEMPLATE_CODE_DUPLICATE, code);
        }
        if (!template.getId().equals(id)) {
            throw exception(SMS_TEMPLATE_CODE_DUPLICATE, code);
        }
    }

    /**
     * 校验 API 短信平台的模板是否有效
     *
     * @param channelId 渠道编号
     * @param apiTemplateId API 模板编号
     */
    @VisibleForTesting
    public void checkApiTemplate(Long channelId, String apiTemplateId) {
        // 获得短信模板
        SmsClient smsClient = smsClientFactory.getSmsClient(channelId);
        Assert.notNull(smsClient, String.format("短信客户端(%d) 不存在", channelId));
        SmsResult<SmsTemplateRespDTO> templateResult = smsClient.getSmsTemplate(apiTemplateId);
        // 校验短信模板是否正确
        templateResult.checkError();
    }

}
