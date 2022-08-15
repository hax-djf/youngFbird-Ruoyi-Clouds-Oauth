package com.flybirds.system.sysDicData.mq.consumer;

import com.flybirds.mqredis.core.pubsub.AbstractChannelMessageListener;
import com.flybirds.system.sysDicData.mq.meesage.SysDictDataRefreshMessage;
import com.flybirds.system.sysDicData.service.ISysDictDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link SysDictDataRefreshMessage} 的消费者
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class SysDictDataRefreshConsumer extends AbstractChannelMessageListener<SysDictDataRefreshMessage> {

    @Autowired
    private ISysDictDataService dictDataService;

    @Override
    public void onMessage(SysDictDataRefreshMessage message) {
        log.info("[onMessage][收到 DictData 刷新消息]");
        dictDataService.initRedisCache();
    }

}
