package com.flybirds.sms.module.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flybirds.common.constant.MsgConstant;
import com.flybirds.common.util.result.Result;
import com.flybirds.excel.utils.poi.POIExcelUtil;
import com.flybirds.mybatis.core.condition.Params;
import com.flybirds.mybatis.core.page.PageResult;
import com.flybirds.sms.enums.SysSmsReceiveStatusEnum;
import com.flybirds.sms.enums.SysSmsSendStatusEnum;
import com.flybirds.sms.module.core.vo.log.SysSmsLogExportReqVO;
import com.flybirds.sms.module.core.vo.log.SysSmsLogPageReqVO;
import com.flybirds.sms.module.entity.SysSmsLog;
import com.flybirds.sms.module.entity.SysSmsTemplate;
import com.flybirds.sms.module.mapper.SysSmsLogMapper;
import com.flybirds.sms.module.service.SysSmsLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 芋道源码
 * 短信日志 Service 实现类
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class SysSmsLogServiceImpl implements SysSmsLogService {

    @Autowired
    private SysSmsLogMapper smsLogMapper;

    @Override
    public Long createSmsLog(String mobile, Long userId,String userName, Integer userType, Boolean isSend,
                             SysSmsTemplate template, String templateContent, Map<String, Object> templateParams) {
        SysSmsLog log = new SysSmsLog();
        // 根据是否要发送，设置状态
        log.setSendStatus(Objects.equals(isSend, true) ? SysSmsSendStatusEnum.INIT.getStatus()
                : SysSmsSendStatusEnum.IGNORE.getStatus())
                .setMobile(mobile)
                .setUserId(userId)
                .setCreateName(userName)
                .setCreateTime(LocalDateTime.now())
                .setUserType(userType)
                // 设置模板相关字段
                .setTemplateId(template.getId())
                .setTemplateCode(template.getCode())
                .setTemplateType(template.getType())
                .setTemplateContent(templateContent)
                .setTemplateParams(templateParams)
                .setApiTemplateId(template.getApiTemplateId())
                // 设置渠道相关字段
                .setChannelId(template.getChannelId())
                .setChannelCode(template.getChannelCode())
                // 设置接收相关字段
                .setReceiveStatus(SysSmsReceiveStatusEnum.INIT.getStatus());
        // 插入数据库
        smsLogMapper.insert(log);
        return log.getId();
    }

    @Override
    public void updateSmsSendResult(Long id, Integer sendCode, String sendMsg,
                                    String apiSendCode, String apiSendMsg, String apiRequestId, String apiSerialNo) {
        SysSmsSendStatusEnum sendStatus = Result.onSuccess(sendCode) ? SysSmsSendStatusEnum.SUCCESS
                : SysSmsSendStatusEnum.FAILURE;
        smsLogMapper.updateById(new SysSmsLog().setId(id)
                    .setSendStatus(sendStatus.getStatus())
                    .setSendTime(new Date())
                    .setSendCode(sendCode).setSendMsg(sendMsg).setApiSendCode(apiSendCode).
                    setApiSendMsg(apiSendMsg)
                    .setApiRequestId(apiRequestId)
                    .setApiSerialNo(apiSerialNo));
    }

    @Override
    public void updateSmsReceiveResult(Long id, Boolean success, Date receiveTime, String apiReceiveCode, String apiReceiveMsg) {
        SysSmsReceiveStatusEnum receiveStatus = Objects.equals(success, true) ? SysSmsReceiveStatusEnum.SUCCESS
                : SysSmsReceiveStatusEnum.FAILURE;
        smsLogMapper.updateById(new SysSmsLog().setId(id)
                .setReceiveStatus(receiveStatus.getStatus())
                .setReceiveTime(receiveTime)
                .setApiReceiveCode(apiReceiveCode).setApiReceiveMsg(apiReceiveMsg));

    }

    @Override
    public Result<?> exportSmsLogExcel(HttpServletResponse response, @Valid Params params) {
        try {
            IPage<SysSmsLog> genDataModelIPage = smsLogMapper.selectPage(params.getPage(), params.getConditions());

            POIExcelUtil<SysSmsLog> util = new POIExcelUtil<SysSmsLog>(SysSmsLog.class);
            util.exportExcel(response, genDataModelIPage.getRecords(), "短信日志");
        }catch (RuntimeException e){
            log.error("数据导出失败");
            return Result.fail(MsgConstant.Business.EXPORT_FAILED);
        }

        return Result.ok(MsgConstant.Business.EXPORT_SUCCESS);
    }

    @Override
    public PageResult<SysSmsLog> getSmsLogPage(@Valid SysSmsLogPageReqVO pageVO) {
        return smsLogMapper.selectPage(pageVO);
    }

    @Override
    public IPage<SysSmsLog> getSmsLogPage(Params params) {
        return smsLogMapper.selectPage(params.getPage(),params.getConditions());
    }

    @Override
    public List<SysSmsLog> getSmsLogList(Params params) {
        return smsLogMapper.selectList(params.getConditions());
    }

    @Override
    public List<SysSmsLog> getSmsLogList(SysSmsLogExportReqVO exportReqVO) {
        return smsLogMapper.selectList(exportReqVO);
    }

}
