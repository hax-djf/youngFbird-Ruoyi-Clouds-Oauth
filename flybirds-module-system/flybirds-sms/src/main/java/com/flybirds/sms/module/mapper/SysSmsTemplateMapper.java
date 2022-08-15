package com.flybirds.sms.module.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flybirds.mybatis.core.basemapper.MpBaseMapper;
import com.flybirds.mybatis.core.basequery.MpQueryWrapper;
import com.flybirds.mybatis.core.page.PageResult;
import com.flybirds.sms.module.core.vo.template.SysSmsTemplateExportReqVO;
import com.flybirds.sms.module.core.vo.template.SysSmsTemplatePageReqVO;
import com.flybirds.sms.module.entity.SysSmsTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface SysSmsTemplateMapper extends MpBaseMapper<SysSmsTemplate> {

    default SysSmsTemplate selectByCode(String code) {
        return selectOne("code", code);
    }

    default IPage<SysSmsTemplate> selectPage(Page<SysSmsTemplate> page, Map<String,Object> params) {
        return selectPage(page, new MpQueryWrapper<SysSmsTemplate>()
                .eqIfPresent("type", params.get("type"))
                .eqIfPresent("status",params.get("status"))
                .likeIfPresent("code", params.get("code"))
                .likeIfPresent("content", params.get("content"))
                .likeIfPresent("api_template_id", params.get("apiTemplateId"))
                .eqIfPresent("channel_id", params.get("channelId"))
                .betweenIfPresent("create_time", params.get("beginTime"), params.get("endTime"))
                .orderByDesc("id"));
    }

    default List<SysSmsTemplate> selectList(Map<String,Object> params) {
        return selectList(new MpQueryWrapper<SysSmsTemplate>()
                .eqIfPresent("type", params.get("type"))
                .eqIfPresent("status",params.get("status"))
                .likeIfPresent("code", params.get("code"))
                .likeIfPresent("content", params.get("content"))
                .likeIfPresent("api_template_id", params.get("apiTemplateId"))
                .eqIfPresent("channel_id", params.get("channelId"))
                .betweenIfPresent("create_time", params.get("beginTime"), params.get("endTime"))
                .orderByDesc("id"));
    }


    default PageResult<SysSmsTemplate> selectPage(SysSmsTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new MpQueryWrapper<SysSmsTemplate>()
                .eqIfPresent("type", reqVO.getType())
                .eqIfPresent("status", reqVO.getStatus())
                .likeIfPresent("code", reqVO.getCode())
                .likeIfPresent("content", reqVO.getContent())
                .likeIfPresent("api_template_id", reqVO.getApiTemplateId())
                .eqIfPresent("channel_id", reqVO.getChannelId())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id"));
    }

    default List<SysSmsTemplate> selectList(SysSmsTemplateExportReqVO reqVO) {
        return selectList(new MpQueryWrapper<SysSmsTemplate>()
                .eqIfPresent("type", reqVO.getType())
                .eqIfPresent("status", reqVO.getStatus())
                .likeIfPresent("code", reqVO.getCode())
                .likeIfPresent("content", reqVO.getContent())
                .likeIfPresent("api_template_id", reqVO.getApiTemplateId())
                .eqIfPresent("channel_id", reqVO.getChannelId())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id"));
    }

    default Integer selectCountByChannelId(Long channelId) {
        return Integer.valueOf(String.valueOf(selectCount("channel_id", channelId)));
    }

    @Select("SELECT id FROM sys_sms_template WHERE update_time > #{maxUpdateTime} LIMIT 1")
    Long selectExistsByUpdateTimeAfter(Date maxUpdateTime);

}
