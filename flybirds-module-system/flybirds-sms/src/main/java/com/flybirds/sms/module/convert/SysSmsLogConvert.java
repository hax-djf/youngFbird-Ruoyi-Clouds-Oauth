package com.flybirds.sms.module.convert;

import com.flybirds.mybatis.core.page.PageResult;
import com.flybirds.sms.module.core.vo.log.SysSmsLogExcelVO;
import com.flybirds.sms.module.core.vo.log.SysSmsLogRespVO;
import com.flybirds.sms.module.entity.SysSmsLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 短信日志 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface SysSmsLogConvert {

    SysSmsLogConvert INSTANCE = Mappers.getMapper(SysSmsLogConvert.class);

    SysSmsLogRespVO convert(SysSmsLog bean);

    List<SysSmsLogRespVO> convertList(List<SysSmsLog> list);

    PageResult<SysSmsLogRespVO> convertPage(PageResult<SysSmsLog> page);

    List<SysSmsLogExcelVO> convertList02(List<SysSmsLog> list);

}
