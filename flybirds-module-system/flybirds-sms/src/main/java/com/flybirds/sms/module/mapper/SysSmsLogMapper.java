package com.flybirds.sms.module.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flybirds.mybatis.core.basemapper.MpBaseMapper;
import com.flybirds.mybatis.core.basequery.MpQueryWrapper;
import com.flybirds.mybatis.core.page.PageResult;
import com.flybirds.sms.module.core.vo.log.SysSmsLogExportReqVO;
import com.flybirds.sms.module.core.vo.log.SysSmsLogPageReqVO;
import com.flybirds.sms.module.entity.SysSmsLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysSmsLogMapper extends MpBaseMapper<SysSmsLog> {

    default IPage<SysSmsLog> selectPage(Page<SysSmsLog> page, Map<String,Object> params) {
        return selectPage(page, new MpQueryWrapper<SysSmsLog>()
                .eqIfPresent("channel_id", params.get("channelId"))
                .eqIfPresent("template_id", params.get("templateId"))
                .likeIfPresent("mobile", params.get("mobile"))
                .eqIfPresent("send_status", params.get("sendStatus"))
                .betweenIfPresent("send_time",params.get("sendStartTime"), params.get("sendEndTime"))
                .eqIfPresent("receive_status", params.get("receiveStatus"))
                .betweenIfPresent("receive_time", params.get("receiveStartTime"),params.get("receiveEndTime"))
                .orderByDesc("id"));
    }

    default PageResult<SysSmsLog> selectPage(SysSmsLogPageReqVO reqVO) {
        return selectPage(reqVO, new MpQueryWrapper<SysSmsLog>()
                .eqIfPresent("channel_id", reqVO.getChannelId())
                .eqIfPresent("template_id", reqVO.getTemplateId())
                .likeIfPresent("mobile", reqVO.getMobile())
                .eqIfPresent("send_status", reqVO.getSendStatus())
                .betweenIfPresent("send_time", reqVO.getBeginSendTime(), reqVO.getEndSendTime())
                .eqIfPresent("receive_status", reqVO.getReceiveStatus())
                .betweenIfPresent("receive_time", reqVO.getBeginReceiveTime(), reqVO.getEndReceiveTime())
                .orderByDesc("id"));
    }

    default List<SysSmsLog> selectList( Map<String,Object> params) {
        return selectList(new MpQueryWrapper<SysSmsLog>()
                .eqIfPresent("channel_id", params.get("channelId"))
                .eqIfPresent("template_id", params.get("templateId"))
                .likeIfPresent("mobile", params.get("mobile"))
                .eqIfPresent("send_status", params.get("sendStatus"))
                .betweenIfPresent("send_time",params.get("sendStartTime"), params.get("sendEndTime"))
                .eqIfPresent("receive_status", params.get("receiveStatus"))
                .betweenIfPresent("receive_time", params.get("receiveStartTime"),params.get("receiveEndTime"))
                .orderByDesc("id"));
    }

    default List<SysSmsLog> selectList(SysSmsLogExportReqVO reqVO) {
        return selectList(new MpQueryWrapper<SysSmsLog>()
                .eqIfPresent("channel_id", reqVO.getChannelId())
                .eqIfPresent("template_id", reqVO.getTemplateId())
                .likeIfPresent("mobile", reqVO.getMobile())
                .eqIfPresent("send_status", reqVO.getSendStatus())
                .betweenIfPresent("send_time", reqVO.getBeginSendTime(), reqVO.getEndSendTime())
                .eqIfPresent("receive_status", reqVO.getReceiveStatus())
                .betweenIfPresent("receive_time", reqVO.getBeginReceiveTime(), reqVO.getEndReceiveTime())
                .orderByDesc("id"));
    }

}
