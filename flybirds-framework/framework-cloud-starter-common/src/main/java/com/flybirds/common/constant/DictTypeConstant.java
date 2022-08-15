package com.flybirds.common.constant;

/**
 * System 字典类型的枚举类
 *
 * @author 芋道源码
 */
public interface DictTypeConstant {

    String USER_TYPE = "user_type"; // 用户类型
    String COMMON_STATUS = "sys_common_status"; // 系统状态

    String USER_SEX = "sys_user_sex"; // 用户性别
    String OPERATE_TYPE = "sys_operate_type"; // 操作类型
    String LOGIN_RESULT = "sys_login_result"; // 登陆结果
    String CONFIG_TYPE = "sys_config_type"; // 参数配置类型
    String BOOLEAN_STRING = "sys_boolean_string"; // Boolean 是否类型

    String SMS_CHANNEL_CODE = "sys_sms_channel_code"; // 短信渠道编码
    String SMS_TEMPLATE_TYPE = "sys_sms_template_type"; // 短信模板类型
    String SMS_SEND_STATUS = "sys_sms_send_status"; // 短信发送状态
    String SMS_RECEIVE_STATUS = "sys_sms_receive_status"; // 短信接收状态

    String REDIS_TIMEOUT_TYPE = "inf_redis_timeout_type"; // Redis 超时类型
    String JOB_STATUS = "inf_job_status"; // 定时任务状态的枚举
    String JOB_LOG_STATUS = "inf_job_log_status"; // 定时任务日志状态的枚举
    String API_ERROR_LOG_PROCESS_STATUS = "inf_api_error_log_process_status"; // API 错误日志的处理状态的枚举
    String ERROR_CODE_TYPE = "sys_error_code_type"; // 错误码的类型枚举
}
