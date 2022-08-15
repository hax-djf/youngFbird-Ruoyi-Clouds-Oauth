package com.flybirds.system.sysDicData.mq.producer;

import com.flybirds.mqredis.core.util.RedisMQTemplate;
import com.flybirds.system.sysDicData.mq.meesage.SysDictDataRefreshMessage;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * DictData 字典数据相关消息的 Producer
 */
@Component
public class SysDictDataProducer {

    @Resource
    private RedisMQTemplate redisMQTemplate;

    /**
     * 发送 {@link SysDictDataRefreshMessage} 消息
     */
    public void sendDictDataRefreshMessage() {
        SysDictDataRefreshMessage message = new SysDictDataRefreshMessage();
        redisMQTemplate.send(message);
    }

}
