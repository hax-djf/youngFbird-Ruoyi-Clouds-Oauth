package com.flybirds.sms.module.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.flybirds.common.enums.CommonStatusEnum;
import com.flybirds.common.enums.user.UserTypeEnum;
import com.flybirds.common.util.core.KeyValue;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.redis.manger.RedisCacheManger;
import com.flybirds.sms.module.entity.SysSmsTemplate;
import com.flybirds.sms.module.service.SysSmsLogService;
import com.flybirds.sms.module.service.SysSmsService;
import com.flybirds.sms.module.service.SysSmsTemplateService;
import com.flybirds.sms.mq.message.SysSmsSendMessage;
import com.flybirds.sms.mq.producer.SysSmsProducer;
import com.flybirds.smsprovider.client.SmsClient;
import com.flybirds.smsprovider.client.core.SmsResult;
import com.flybirds.smsprovider.client.core.dto.SmsReceiveRespDTO;
import com.flybirds.smsprovider.client.core.dto.SmsSendRespDTO;
import com.flybirds.smsprovider.factory.SmsClientFactory;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.flybirds.common.exception.enums.SysErrorCodeConstants.SMS_SEND_MOBILE_NOT_EXISTS;
import static com.flybirds.common.exception.enums.SysErrorCodeConstants.SMS_SEND_MOBILE_TEMPLATE_PARAM_MISS;
import static com.flybirds.common.exception.enums.SysErrorCodeConstants.SMS_TEMPLATE_NOT_EXISTS;
import static com.flybirds.common.exception.util.ServiceExceptionUtil.exception;


/**
 * 短信日志Service实现类
 *
 * @author zzf
 * @date 2021/1/25 9:25
 */
@Service
@Slf4j
public class SysSmsServiceImpl implements SysSmsService {

    @Resource
    private SysSmsTemplateService smsTemplateService;

    @Resource
    private SysSmsLogService smsLogService;

    @Resource
    private SysSmsProducer smsProducer;

    @Resource
    private SmsClientFactory smsClientFactory;

    @Autowired
    RedisCacheManger redisCacheManger;


    @Override
    public Long sendSingleSmsToAdmin(String mobile, Long userId,String userName, String templateCode, Map<String, Object> templateParams) {
        return this.sendSingleSms(mobile, userId,userName,UserTypeEnum.ADMIN.getValue(), templateCode, templateParams);
    }

    @Override
    public Long sendSingleSmsToMember(String mobile, Long userId, String templateCode, Map<String, Object> templateParams) {
        throw new UnsupportedOperationException("暂时不支持该操作，感兴趣可以实现该功能哟！");
    }

    @Override
    public Long sendSingleSms(String mobile, Long userId,String userName, Integer userType,
                              String templateCode, Map<String, Object> templateParams) {
        // 校验短信模板是否合法
        SysSmsTemplate template = this.checkSmsTemplateValid(templateCode);
        // 校验手机号码是否存在
        mobile = this.checkMobile(mobile);
        // 构建有序的模板参数。为什么放在这个位置，是提前保证模板参数的正确性，而不是到了插入发送日志
        List<KeyValue<String, Object>> newTemplateParams = this.buildTemplateParams(template, templateParams);

        // 创建发送日志
        Boolean isSend = CommonStatusEnum.ENABLE.getStatus().equals(template.getStatus()); // 如果模板被禁用，则不发送短信，只记录日志
        String content = smsTemplateService.formatSmsTemplateContent(template.getContent(), templateParams);
        Long sendLogId = smsLogService.createSmsLog(mobile,userId,userName, userType, isSend, template, content, templateParams);

        // 发送 MQ 消息，异步执行发送短信
        if (isSend) {
            smsProducer.sendSmsSendMessage(sendLogId, mobile, template.getChannelId(), template.getApiTemplateId(), newTemplateParams);
        }
        return sendLogId;
    }

    @Override
    public void sendBatchSms(List<String> mobiles, List<Long> userIds, Integer userType,
                             String templateCode, Map<String, Object> templateParams) {
        throw new UnsupportedOperationException("暂时不支持该操作，感兴趣可以实现该功能哟！");
    }

    @VisibleForTesting
    public SysSmsTemplate checkSmsTemplateValid(String templateCode) {
        // 获得短信模板。考虑到效率，从缓存中获取
        SysSmsTemplate template = smsTemplateService.getSmsTemplateByCodeFromCache(templateCode);
        // 短信模板不存在
        if (template == null) {
            throw exception(SMS_TEMPLATE_NOT_EXISTS);
        }
        return template;
    }

    /**
     * 将参数模板，处理成有序的 KeyValue 数组
     *
     * 原因是，部分短信平台并不是使用 key 作为参数，而是数组下标，例如说腾讯云 https://cloud.tencent.com/document/product/382/39023
     *
     * @param template 短信模板
     * @param templateParams 原始参数
     * @return 处理后的参数
     */
    @VisibleForTesting
    public List<KeyValue<String, Object>> buildTemplateParams(SysSmsTemplate template, Map<String, Object> templateParams) {
        return template.getParams().stream().map(key -> {
            Object value = templateParams.get(key);
            if (value == null) {
                throw exception(SMS_SEND_MOBILE_TEMPLATE_PARAM_MISS, key);
            }
            return new KeyValue<>(key, value);
        }).collect(Collectors.toList());
    }

    @VisibleForTesting
    public String checkMobile(String mobile) {
        if (StrUtil.isEmpty(mobile)) {
            throw exception(SMS_SEND_MOBILE_NOT_EXISTS);
        }
        return mobile;
    }

    @Override
    public void doSendSms(SysSmsSendMessage message) {
        // 获得渠道对应的 SmsClient 客户端
        SmsClient smsClient = smsClientFactory.getSmsClient(message.getChannelId());
        Assert.notNull(smsClient, String.format("短信客户端(%d) 不存在", message.getChannelId()));
        //发送短信前key值存储
        doSaveSmsKey(message);
        // 发送短信
        SmsResult<SmsSendRespDTO> sendResult = smsClient.sendSms(message.getLogId(), message.getMobile(),
                message.getApiTemplateId(), message.getTemplateParams());

        smsLogService.updateSmsSendResult(message.getLogId(), sendResult.getCode(), sendResult.getMsg(),
                sendResult.getApiCode(), sendResult.getApiMsg(), sendResult.getApiRequestId(),
                sendResult.getData() != null ? sendResult.getData().getSerialNo() : null);
    }

    /**
     * 短信key值存储
     */
    protected void doSaveSmsKey(SysSmsSendMessage message){
        List<KeyValue<String, String>> smsCacheKeyParams = message.getSmsCacheKeyParams();
        if(StringUtils.isNotNull(smsCacheKeyParams) && StringUtils.isNotEmpty(smsCacheKeyParams)){
            smsCacheKeyParams.stream().forEach(keyParams->{
                redisCacheManger.setCacheObject(keyParams.getKey(),keyParams.getValue(),5l*60, TimeUnit.SECONDS);
            });
        }
    }

    @Override
    public void receiveSmsStatus(String channelCode, String text) throws Throwable {
        // 获得渠道对应的 SmsClient 客户端
        SmsClient smsClient = smsClientFactory.getSmsClient(channelCode);
        Assert.notNull(smsClient, String.format("短信客户端(%s) 不存在", channelCode));
        // 解析内容
        List<SmsReceiveRespDTO> receiveResults = smsClient.parseSmsReceiveStatus(text);
        if (CollUtil.isEmpty(receiveResults)) {
            return;
        }
        // 更新短信日志的接收结果. 因为量一般不打，所以先使用 for 循环更新
        receiveResults.forEach(result -> {
            smsLogService.updateSmsReceiveResult(result.getLogId(), result.getSuccess(), result.getReceiveTime(),
                    result.getErrorCode(), result.getErrorCode());
        });
    }

}
