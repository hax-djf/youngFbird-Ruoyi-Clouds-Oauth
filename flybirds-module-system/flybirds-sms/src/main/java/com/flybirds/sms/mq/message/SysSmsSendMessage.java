package com.flybirds.sms.mq.message;

import com.flybirds.common.util.core.KeyValue;
import com.flybirds.mqredis.core.pubsub.ChannelMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 短信发送消息
 *
 * @author 芋道源码
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysSmsSendMessage extends ChannelMessage {

    /**
     * 短信日志编号
     */
    @NotNull(message = "短信日志编号不能为空")
    private Long logId;
    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    private String mobile;
    /**
     * 短信渠道编号
     */
    @NotNull(message = "短信渠道编号不能为空")
    private Long channelId;
    /**
     * 短信 API 的模板编号
     */
    @NotNull(message = "短信 API 的模板编号不能为空")
    private String apiTemplateId;
    /**
     * 短信模板参数
     */
    private List<KeyValue<String, Object>> templateParams;
    /**
     * 短信缓存key
     */
    private List<KeyValue<String, String>> smsCacheKeyParams;


//    @Override
//    public String getStreamKey() {
//
//    }
    @Override
    public String getChannel() {
        return "system.sms.send";
    }
}
