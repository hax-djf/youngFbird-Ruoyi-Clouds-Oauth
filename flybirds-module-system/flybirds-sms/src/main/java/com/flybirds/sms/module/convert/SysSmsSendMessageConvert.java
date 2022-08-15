package com.flybirds.sms.module.convert;

import com.flybirds.api.core.dto.SysSmsSendMessageDto;
import com.flybirds.sms.mq.message.SysSmsSendMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 短信发送消息
 *
 * @author 芋道源码
 */
@Mapper
public interface SysSmsSendMessageConvert {

    SysSmsSendMessageConvert INSTANCE = Mappers.getMapper(SysSmsSendMessageConvert.class);

    SysSmsSendMessage convertByDto(SysSmsSendMessageDto dto);

}
