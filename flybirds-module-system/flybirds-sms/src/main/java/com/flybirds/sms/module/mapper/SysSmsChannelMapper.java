package com.flybirds.sms.module.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flybirds.mybatis.core.basemapper.MpBaseMapper;
import com.flybirds.mybatis.core.basequery.MpQueryWrapper;
import com.flybirds.mybatis.core.page.PageResult;
import com.flybirds.sms.module.core.vo.channel.SysSmsChannelPageReqVO;
import com.flybirds.sms.module.entity.SysSmsChannel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.Map;

@Mapper
public interface SysSmsChannelMapper extends MpBaseMapper<SysSmsChannel> {

    default IPage<SysSmsChannel> selectPage(Page<SysSmsChannel> page, Map<String,Object> condition) {
        return selectPage(page, new MpQueryWrapper<SysSmsChannel>()
                .likeIfPresent("signature", condition.getOrDefault("signature",""))
                .eqIfPresent("status", condition.getOrDefault("status",""))
                .betweenIfPresent("create_time", condition.getOrDefault("beginTime",""), condition.getOrDefault("endTime",""))
                .orderByDesc("id"));
    }

    default PageResult<SysSmsChannel> selectPage(SysSmsChannelPageReqVO reqVO) {
        return selectPage(reqVO, new MpQueryWrapper<SysSmsChannel>()
                .likeIfPresent("signature", reqVO.getSignature())
                .eqIfPresent("status", reqVO.getStatus())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id"));
    }

    @Select("SELECT id FROM sys_sms_channel WHERE update_time > #{maxUpdateTime} LIMIT 1")
    Long selectExistsByUpdateTimeAfter(Date maxUpdateTime);

}
