package com.flybirds.smsprovider.factory;
import com.flybirds.smsprovider.client.AbstractSmsClient;
import com.flybirds.smsprovider.client.SmsClient;
import com.flybirds.smsprovider.client.enums.SmsChannelEnum;
import com.flybirds.smsprovider.client.impl.aliyun.AliyunSmsClient;
import com.flybirds.smsprovider.client.impl.dingtalk.DebugDingTalkSmsClient;
import com.flybirds.smsprovider.client.impl.yunpian.YunpianSmsClient;
import com.flybirds.smsprovider.client.property.SmsChannelProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 短信客户端工厂接口
 *
 * @author 芋道源码
 */
@Validated
@Slf4j
public class SmsClientFactoryImpl implements SmsClientFactory {

    /**
     * 短信客户端 Map
     * key：渠道编号，使用 {@link SmsChannelProperties#getId()}
     */
    private final ConcurrentMap<Long, AbstractSmsClient> channelIdClients = new ConcurrentHashMap<>();

    /**
     * 短信客户端 Map
     * key：渠道编码，使用 {@link SmsChannelProperties#getCode()} ()}
     *
     * 注意，一些场景下，需要获得某个渠道类型的客户端，所以需要使用它。
     * 例如说，解析短信接收结果，是相对通用的，不需要使用某个渠道编号的 {@link #channelIdClients}
     */
    private final ConcurrentMap<String, AbstractSmsClient> channelCodeClients = new ConcurrentHashMap<>();

    /**
     * 初始化将所有的短信渠道汇总到一个map集合中
     * 解释： 通过code直接获取到短信客户端数据，数据适配到页面，可以创建一个新的枚举类中包含 api-key He api-secret数据
     *       将sms客户端存入缓存中，通过code直接获取，进行短信发送
     */
    public SmsClientFactoryImpl() {
        // 初始化 channelCodeClients 集合
        Arrays.stream(SmsChannelEnum.values()).forEach(channel -> {
            // 创建一个空的 SmsChannelProperties 对象
            SmsChannelProperties properties = new SmsChannelProperties().setCode(channel.getCode())
                    .setApiKey("default").setApiSecret("default");
            // 创建 Sms 客户端
            AbstractSmsClient smsClient = createSmsClient(properties);
            channelCodeClients.put(channel.getCode(), smsClient);
        });
    }

    @Override
    public SmsClient getSmsClient(Long channelId) {
        return channelIdClients.get(channelId);
    }

    @Override
    public SmsClient getSmsClient(String channelCode) {
        return channelCodeClients.get(channelCode);
    }

    /**
     * sms的客户端 页面数据适配，通过初始化加载数据库中渠道的数据，初始化各渠道sms的客户端，通过渠道id 来获取smsClient客户端
     * @param properties 配置对象
     */
    @Override
    public void createOrUpdateSmsClient(SmsChannelProperties properties) {
        AbstractSmsClient client = channelIdClients.get(properties.getId());
        if (client == null) {
            client = this.createSmsClient(properties);
            //初始化客户端数据
            client.init();
            channelIdClients.put(client.getId(), client);
        } else {
            client.refresh(properties);
        }
    }

    /**
     * 创建短信渠道
     * @param properties
     * @return
     */
    private AbstractSmsClient createSmsClient(SmsChannelProperties properties) {
        SmsChannelEnum channelEnum = SmsChannelEnum.getByCode(properties.getCode());
        Assert.notNull(channelEnum, String.format("渠道类型(%s) 为空", channelEnum));
        // 创建客户端
        switch (channelEnum) {
            case ALIYUN: return new AliyunSmsClient(properties);
            case YUN_PIAN: return new YunpianSmsClient(properties);
            case DEBUG_DING_TALK: return new DebugDingTalkSmsClient(properties);
        }
        // 创建失败，错误日志 + 抛出异常
        log.error("[createSmsClient][配置({}) 找不到合适的客户端实现]", properties);
        throw new IllegalArgumentException(String.format("配置(%s) 找不到合适的客户端实现", properties));
    }

}
