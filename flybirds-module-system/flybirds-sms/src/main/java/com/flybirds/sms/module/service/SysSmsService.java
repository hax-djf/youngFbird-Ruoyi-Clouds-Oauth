package com.flybirds.sms.module.service;

import com.flybirds.sms.mq.message.SysSmsSendMessage;

import java.util.List;
import java.util.Map;

/**
 * 短信Service接口
 * 只支持异步，因此没有返回值
 *
 * @author zzf
 * @date 2021/1/25 9:24
 */
public interface SysSmsService {

    /**
     * 发送单条短信给用户（管理员）
     *
     * 在 mobile 为空时，使用 userId 加载对应管理员的手机号
     *
     * @param mobile 手机号
     * @param userId 用户编号
     * @param  userName
     * @param templateCode 短信模板编号
     * @param templateParams 短信模板参数
     * @return 发送日志编号
     */
    Long sendSingleSmsToAdmin(String mobile, Long userId,String userName,
                              String templateCode, Map<String, Object> templateParams);

    /**
     * 发送单条短信给用户（会员）
     *
     * 在 mobile 为空时，使用 userId 加载对应会员的手机号
     *
     * @param mobile 手机号
     * @param userId 用户编号
     * @param templateCode 短信模板编号
     * @param templateParams 短信模板参数
     * @return 发送日志编号
     */
    Long sendSingleSmsToMember(String mobile, Long userId,
                               String templateCode, Map<String, Object> templateParams);

    /**
     * 发送单条短信
     * @param mobile
     * @param userId
     * @param userType
     * @param templateCode
     * @param templateParams
     * @return
     */
    Long sendSingleSms(String mobile, Long userId,String userName, Integer userType,
                       String templateCode, Map<String, Object> templateParams);

    /**
     * 批量发送短信
     * @param mobiles
     * @param userIds
     * @param userType
     * @param templateCode
     * @param templateParams
     */
    void sendBatchSms(List<String> mobiles, List<Long> userIds, Integer userType,
                      String templateCode, Map<String, Object> templateParams);

    /**
     * 发送短信验证码
     * @param message
     */
    void doSendSms(SysSmsSendMessage message);

    /**
     * 接收短信的接收结果
     *
     * @param channelCode 渠道编码
     * @param text 结果内容
     * @throws Throwable 处理失败时，抛出异常
     */
    void receiveSmsStatus(String channelCode, String text) throws Throwable;

}
