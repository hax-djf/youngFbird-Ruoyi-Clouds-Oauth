package com.flybirds.sms.module.convert;

import com.flybirds.mybatis.core.page.PageResult;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelCreateReqVO;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelRespVO;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelSimpleRespVO;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelUpdateReqVO;
import com.flybirds.sms.module.entity.SysSmsChannel;
import com.flybirds.smsprovider.client.property.SmsChannelProperties;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 短信渠道 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface SysSmsChannelConvert {

    SysSmsChannelConvert INSTANCE = Mappers.getMapper(SysSmsChannelConvert.class);

    SysSmsChannel convert(SysSmsChannelCreateReqVO bean);

    SysSmsChannel convert(SysSmsChannelUpdateReqVO bean);

    SysSmsChannelRespVO convert(SysSmsChannel bean);

    List<SysSmsChannelRespVO> convertList(List<SysSmsChannel> list);

    PageResult<SysSmsChannelRespVO> convertPage(PageResult<SysSmsChannel> page);

    List<SmsChannelProperties> convertList02(List<SysSmsChannel> list);

    List<SysSmsChannelSimpleRespVO> convertList03(List<SysSmsChannel> list);

}
