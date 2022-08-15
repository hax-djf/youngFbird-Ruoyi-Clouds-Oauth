package com.flybirds.sms.module.controller;

import com.flybirds.api.core.dto.SysSmsSendMessageDto;
import com.flybirds.common.constant.MsgConstant;
import com.flybirds.common.exception.BaseException;
import com.flybirds.common.util.result.Result;
import com.flybirds.common.util.str.StringUtils;
import com.flybirds.sms.module.convert.SysSmsSendMessageConvert;
import com.flybirds.sms.mq.message.SysSmsSendMessage;
import com.flybirds.sms.mq.producer.SysSmsProducer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author :芋道源码
 */
@Component
@RestController
@RequestMapping("/send")
public class SmsSendController {


    @Autowired
    private SysSmsProducer SysSmsProducer;

    @PostMapping("/message")
    @ApiOperation("发送短信信息")
    public Result<?> sendSmsMessage(@Valid @RequestBody SysSmsSendMessageDto sysSmsSendMessageDto) {

        if (StringUtils.isNull(sysSmsSendMessageDto)) {
            throw new BaseException(MsgConstant.Business.NOT_FOUND_ENTITY);
        }
        SysSmsSendMessage sysSmsSendMessage = SysSmsSendMessageConvert.INSTANCE.convertByDto(sysSmsSendMessageDto);
        //短信发送服务
        SysSmsProducer.sendSmsSendMessage(sysSmsSendMessage);
        return Result.ok();
    }
}
