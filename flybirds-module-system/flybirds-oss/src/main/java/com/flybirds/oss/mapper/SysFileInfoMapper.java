package com.flybirds.oss.mapper;

import com.flybirds.api.core.entity.SysFileInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : flybirds
 * @create :2021-06-07 15:23:00
 */
@Mapper
public interface SysFileInfoMapper {

    public void insert(SysFileInfo fileInfo);
}
