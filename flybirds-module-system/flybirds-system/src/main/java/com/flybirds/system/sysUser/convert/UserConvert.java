package com.flybirds.system.sysUser.convert;

import com.flybirds.api.core.entity.SysUser;
import com.flybirds.system.sysUser.vo.UserCreateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    SysUser convert(UserCreateReqVO bean);

}
