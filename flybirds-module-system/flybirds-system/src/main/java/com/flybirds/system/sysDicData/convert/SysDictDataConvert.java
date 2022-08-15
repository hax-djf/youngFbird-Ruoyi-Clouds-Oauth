package com.flybirds.system.sysDicData.convert;

import com.flybirds.api.core.entity.SysDictData;
import com.flybirds.dict.core.dto.DictDataCacheDTO;
import com.flybirds.system.sysDicData.vo.SysDictDataSimpleRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface SysDictDataConvert {

    SysDictDataConvert INSTANCE = Mappers.getMapper(SysDictDataConvert.class);

    DictDataCacheDTO convert(SysDictData bean);

    List<DictDataCacheDTO> convertList(Collection<SysDictData> list);

    List<SysDictDataSimpleRespVO> convertList02(Collection<SysDictData> list);

    SysDictData convert01(DictDataCacheDTO bean);

    List<SysDictData> convertList01(Collection<DictDataCacheDTO> list);
}
