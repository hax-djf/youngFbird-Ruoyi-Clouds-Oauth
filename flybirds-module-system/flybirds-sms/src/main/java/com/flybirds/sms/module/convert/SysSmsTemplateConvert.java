package com.flybirds.sms.module.convert;


import com.flybirds.mybatis.core.page.PageResult;
import com.flybirds.sms.module.core.vo.template.SysSmsTemplateCreateReqVO;
import com.flybirds.sms.module.core.vo.template.SysSmsTemplateExcelVO;
import com.flybirds.sms.module.core.vo.template.SysSmsTemplateRespVO;
import com.flybirds.sms.module.core.vo.template.SysSmsTemplateUpdateReqVO;
import com.flybirds.sms.module.entity.SysSmsTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysSmsTemplateConvert {

    SysSmsTemplateConvert INSTANCE = Mappers.getMapper(SysSmsTemplateConvert.class);

    SysSmsTemplate convert(SysSmsTemplateCreateReqVO bean);

    SysSmsTemplate convert(SysSmsTemplateUpdateReqVO bean);

    SysSmsTemplateRespVO convert(SysSmsTemplate bean);

    List<SysSmsTemplateRespVO> convertList(List<SysSmsTemplate> list);

    PageResult<SysSmsTemplateRespVO> convertPage(PageResult<SysSmsTemplate> page);

    List<SysSmsTemplateExcelVO> convertList02(List<SysSmsTemplate> list);

}
