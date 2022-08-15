package com.flybirds.common.constant;

/**
 * 常用BaseConstants
 *
 * @author flybirds
 */
public interface Constant {

    /**
     * UTF-8 字符集
     */
    String UTF8 = "UTF-8";

    /**
     * content-type 字符集
     */
    String CONTENT_TYPE = "application/json; charset=UTF-8";

    /**
     * GBK 字符集
     */
    String GBK = "GBK";

    /**
     * http请求
     */
    String HTTP = "http://";

    /**
     * https请求
     */
    String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    Integer SUCCESS = 200;

    /**
     * 通用失败标识
     */
    Integer FAIL = 400;

    /**
     * 资源映射路径 前缀
     */
    String RESOURCE_PREFIX = "/profile";
    /**
     * true
     */
    boolean TRUE = true;
    /**
     * false
     */
    boolean FALSE = false;

    /** 是否为系统默认（是） */
    String YES = "Y";

    /** 是否为系统默认（否） */
    String NO = "N";

    /**
     * 时间参数
     */
    interface Time{
        /**
         * 验证码有效期（分钟）
         */
        long CAPTCHA_EXPIRATION = 2;

        /**
         * 令牌有效期（秒）
         */
        long TOKEN_EXPIRE = 30 * 60;
    }

    interface CloudServiceName{

        /**
         * 认证服务的serviceid
         */
        String FLYBIRDS_OAUTH = "young-flybirds-oauth";

        /**
         * 系统模块的serviceid
         */
        String FLYBIRDS_SYSTEM = "young-flybirds-system";

        /**
         * 文件服务的serviceid
         */
        String FLYBIRDS_MANGEFILE = "young-flybirds-mangefile";

        /**
         * 短信服务
         */
        String FLYBIRDS_SMS = "young-flybirds-sms";
    }
    
    interface User{
        /**
         * 平台内系统用户的唯一标志
         */
         String SYS_USER = "SYS_USER";

        /** 正常状态 */
         String NORMAL = "0";

        /** 异常状态 */
         String EXCEPTION = "1";

        /** 用户封禁状态 */
         String USER_DISABLE = "1";

        /** 角色封禁状态 */
         String ROLE_DISABLE = "1";

        /** 部门正常状态 */
         String DEPT_NORMAL = "0";

        /** 部门停用状态 */
         String DEPT_DISABLE = "1";

        /** 字典正常状态 */
         String DICT_NORMAL = "0";

        /** 是否为系统默认（是） */
         String YES = "Y";

        /** 是否菜单外链（是） */
         String YES_FRAME = "0";

        /** 是否菜单外链（否） */
         String NO_FRAME = "1";

        /** 菜单类型（目录） */
         String TYPE_DIR = "M";

        /** 菜单类型（菜单） */
         String TYPE_MENU = "C";

        /** 菜单类型（按钮） */
         String TYPE_BUTTON = "F";

        /** Layout组件标识 */
         String LAYOUT = "Layout";

        /** ParentView组件标识 */
         String PARENT_VIEW = "ParentView";

        /** 校验返回结果码 */
         String UNIQUE = "0";

         String NOT_UNIQUE = "1";

        /**
         * 用户名长度限制
         */
         int USERNAME_MIN_LENGTH = 2;

         int USERNAME_MAX_LENGTH = 20;

        /**
         * 密码长度限制
         */
         int PASSWORD_MIN_LENGTH = 5;

         int PASSWORD_MAX_LENGTH = 20;

        /**
         * 手机号长度
         */
         int PHONE_LENGTH = 11;

        /**
         * 手机号验证码长度
         */
         int PHONE_CODE_LENGTH = 6;
    }

    interface Register{
        /**
         * 默认头像
         */
        String DEFAULT_AVATAR = "http://120.79.220.218:9000/demo/flybirds_logo.png";

        String CRRATE_BY_EMEAIL = "admin:邮箱注册";

        String CRRATE_BY_PHONE = "admin:手机注册";

        /* 默认注册人 admin*/
        Long USER_ADMIN_ID = 1L;

        /** 默认用户岗位 */
        Long[] DEFAULT_POST_ID = new Long[]{4l};

        /** 默认用户角色 */
        Long[] DEFAULT_ROLE_ID = new Long[]{2l};

        Long DEFAULT_DEPT_ID = 100l;

        String DEFAULT_STATUS = "0";

        String DEFAULT_LOCKS = "0";
    }

    /* 时间转换器 */
    interface DateFormat{

        String YYYY = "yyyy";

        String YYYY_MM = "yyyy-MM";

        String YYYY_MM_DD = "yyyy-MM-dd";

        String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

        String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

        /* 时区 */
        String TIME_ZONE_DEFAULT_GMT = "GMT+8";
    }
    /**
     * 数字常量
     */
    interface ConstantNumber {
        /**
         * @Fields NEGATIVE : ( -1 )
         */
        Integer NEGATIVE = -1;

        /**
         * @Fields ZERO : ( 0 )
         */
        Integer ZERO = 0;

        /**
         * @Fields ONE : ( 1 )
         */
        Integer ONE = 1;

        /**
         * @Fields TOW : ( 2 )
         */
        Integer TOW = 2;

        /**
         * @Fields THREE : ( 3 )
         */
        Integer THREE = 3;

        /**
         * @Fields FOUR : ( 4 )
         */
        Integer FOUR = 4;

        /**
         * @Fields FIVE : ( 5 )
         */
        Integer FIVE = 5;

        /**
         * @Fields FIVE : ( 6 )
         */
        Integer SIX = 6;

        /**
         * @Fields FIVE : ( 7 )
         */
        Integer Seven = 7;

        /**
         * @Fields FIVE : ( 8 )
         */
        Integer Eight = 8;

        /**
         * @Fields FIVE : ( 9 )
         */
        Integer Nine = 9;

        /**
         * @Fields FIVE : ( 10 )
         */
        Integer Ten = 10;
    }


}
