package com.flybirds.common.constant;

/**
 * MsgConstant 消息通用常量
 *
 * @author flybirds
 */
public interface MsgConstant {

    String ERROR_MESSAGE = "system erro";

    String SUCCESS =  "操作成功";

    String FAILURE = "操作失败";

    /* 验证码 */
    interface Captcha{

       String CAPTCHA_NOT_NULL = "验证码不能为空";

       String CAPTCHA_LOSS = "验证码已失效";

       String CAPTCHA_ERROR =  "验证码错误";
    }

    /* 业务消息 */
    interface Business{

        String SAVE_SUCCESS = "保存成功";

        String SAVE_FAILED = "保存失败";

        String SAVE_FAILED_BY_ENTITY = "保存失败,{}";

        String SAVE_SUCCESS_BY_ENTITY = "保存成功,{}";

        String UPDATE_SUCCESS = "更新成功";

        String QUERY_SUCCESS = "查询成功";

        String QUERY_FAILED = "查询失败,找不到数据";

        String UPDATE_FAILED = "更新失败";

        String DELETE_SUCCESS = "删除成功";

        String DELETE_FAILED = "删除失败";

        String DELETE_LOGIC_SUCCESS = "删除成功";

        String DELETE_LOGIC_FAILED = "删除失败";

        String HAS_REFERENCE = "该数据已在别处引用，不能删除";

        String HAS_UPDATE = "该数据已在别处引用，不能更新";

        String CODE_REPETITION = "编码重复，请重试";

        String NAME_REPETITION = "名称重复，请重新输入";

        String COLUMN_REPEAT="已存在，请重新输入";

        String NOT_FOUND_PARAMS= "PARAMS不能为空";

        String NOT_FOUND_ID = "ID不能为空";

        String NOT_FOUND_ENTITY= "ENTITY不能为空";

        String QUERY_SUCCESS_BY_ID = "查询成功,id = {}";

        String QUERY_FAILED_BY_ID = "查询失败,数据为空,id = {}";

        String QUERY_SUCCESS_BY_PARAMS = "查询成功,params = {}";

        String QUERY_FAILED_BY_PARAMS = "查询失败,数据为空,params = {}";

        String UPDATE_FAILED_BY_ID = "更新失败,数据为空,id = {}";

        String UPDATE_SUCCESS_BY_ID = "更新成功,id = {}";

        String DELETE_FAILED_BY_ID = "删除失败,数据为空,id = {}";

        String DELETE_SUCCESS_BY_ID = "删除成功,id = {}";

        String DELETE_LOGIC_FAILED_BY_ID = "逻辑删除失败,数据为空,id = {}";

        String DELETE_LOGIC_SUCCESS_BY_ID = "逻辑删除成功,id = {}";

        String NOT_FOUND_DATA_= "数据为空";

        String SUCCESS ="操作成功";

        String FAILED ="操作失败";

        String SYNC_SUCCESS ="同步成功";

        String SYNC_FAILED ="同步失败";

        String EXPORT_SUCCESS ="导出成功";

        String EXPORT_FAILED ="导出失败";

        String EXPORT_SUCCESS_BY_INFO ="导出成功,{}";

        String EXPORT_FAILED_BY_INFO ="导出失败,{}";


        String ENABLE_SUCCESS ="启用成功";

        String ENABLE_FAILURE ="启用失败";

        String DISABLE_SUCCESS ="禁用成功";

        String DISABLE_FAILURE ="禁用失败";

        /**
         * 非法签名
         */
        String SIGN_FAIL_MSG = "非法访问，请检查请求信息";

        String VALIDATE_CODE_FAIL_MSG = "验证码验证失败";

        //apilog 请求
        String API_LOG_ERROR_MSG =  "请求成功，但是为接受到参数，请管理员核对排查";
    }


}
