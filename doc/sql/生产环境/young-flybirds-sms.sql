/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 120.79.220.218:3306
 Source Schema         : young-flybirds-sms

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 05/01/2023 11:43:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_sms_channel
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms_channel`;
CREATE TABLE `sys_sms_channel`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `signature` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信签名',
  `code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '渠道编码',
  `status` tinyint(4) NOT NULL COMMENT '开启状态',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `api_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信 API 的账号',
  `api_secret` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信 API 的秘钥',
  `callback_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信发送回调 URL',
  `create_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_user` bigint(19) NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_user` bigint(19) NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `sort` int(10) NOT NULL DEFAULT 0 COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1482631163473321987 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信渠道' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_sms_channel
-- ----------------------------
INSERT INTO `sys_sms_channel` VALUES (1482275954393919492, 'CRM客户管理系统', 'ALIYUN', 0, '阿里云', '0XIpE8E1Z++A55irDnntthJJUQZ15j9J/Rxt+QIzkPw=', 'fKxTv3SqCwTRlIQBv9ckKZr8pZIvs1gfBYMgwFL2T20=', NULL, 'admin', 1, '2021-03-31 11:53:10', 'admin', 1, '2022-01-16 07:10:16', b'0', 1);
INSERT INTO `sys_sms_channel` VALUES (1482631163473321986, 'debug钉钉', 'DEBUG_DING_TALK', 0, '关键字【FBIRD登录】', 'Bchs+kbNMKHx+Pb/gcMA4ZASQcL8xz+QhuB+Je9LUUijNh1fVKx/ctQVqLgFsxF24NaAgcHFOBz6GtDVN/U9/2QR25wPmCYPNs+oP/IX8pE=', '7DHhS2MlZFITw21WJTGcDFSWnBHlGyjqeIo0iTGd9OcOapmdRWTzaNbEtBUGHKktrCM8Hk4uLGY+ij0p4hAeNz+HYATh0F4tWOdrnp5lMWM=', NULL, NULL, 1, '2022-01-16 16:29:50', '', NULL, '2022-01-16 08:29:48', b'0', 0);

-- ----------------------------
-- Table structure for sys_sms_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms_log`;
CREATE TABLE `sys_sms_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `channel_id` bigint(20) NOT NULL COMMENT '短信渠道编号',
  `channel_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信渠道编码',
  `template_id` bigint(20) NOT NULL COMMENT '模板编号',
  `template_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板编码',
  `template_type` tinyint(4) NOT NULL COMMENT '短信类型',
  `template_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信内容',
  `template_params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信参数',
  `api_template_id` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信 API 的模板编号',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户编号',
  `user_type` tinyint(4) NULL DEFAULT NULL COMMENT '用户类型',
  `send_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '发送状态',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  `send_code` int(11) NULL DEFAULT NULL COMMENT '发送结果的编码',
  `send_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送结果的提示',
  `api_send_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信 API 发送结果的编码',
  `api_send_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信 API 发送失败的提示',
  `api_request_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信 API 发送返回的唯一请求 ID',
  `api_serial_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信 API 发送返回的序号',
  `receive_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '接收状态',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '接收时间',
  `api_receive_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'API 接收结果的编码',
  `api_receive_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'API 接收结果的说明',
  `create_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `sort` int(10) NOT NULL DEFAULT 0 COMMENT '排序',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`, `receive_status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1482634539485421571 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_sms_log
-- ----------------------------
INSERT INTO `sys_sms_log` VALUES (1482275954393919493, 1482275954393919492, 'ALIYUN', 1482275954393919490, 'aliyun_test01', 1, '您好,欢迎注册HAX_ORM系统,您的验证码为$123456,验证码5分钟之内有效,请勿泄露他人', '{\"code\":\"123456\"}', 'SMS_186968097', '17683991005', 1, 0, 10, '2022-01-15 17:29:09', 200, '成功', 'OK', 'OK', '361C5080-08C6-5B77-8D56-60F2ED91CE43', '988305642238947008^0', 0, '2022-01-16 14:24:37', NULL, NULL, 'admin', 1, '2022-01-15 09:29:06', 0, NULL);
INSERT INTO `sys_sms_log` VALUES (1482634539485421570, 1482631163473321986, 'DEBUG_DING_TALK', 1482632755098443777, 'dd-01', 1, 'FBIRD登录123456', '{\"code\":\"123456\"}', 'dd-test', '17683991005', 1, 2, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 1, '2022-01-16 16:43:15', 0, NULL);

-- ----------------------------
-- Table structure for sys_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms_template`;
CREATE TABLE `sys_sms_template`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `type` tinyint(4) NOT NULL COMMENT '短信签名',
  `status` tinyint(4) NOT NULL COMMENT '开启状态',
  `code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板编码',
  `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板名称',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板内容',
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '参数数组',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `api_template_id` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信 API 的模板编号',
  `channel_id` bigint(19) NOT NULL COMMENT '短信渠道编号',
  `channel_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信渠道编码',
  `create_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_user` bigint(19) NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_user` bigint(19) NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `delete_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `sort` int(10) NOT NULL DEFAULT 0 COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1482632755098443778 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信模板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_sms_template
-- ----------------------------
INSERT INTO `sys_sms_template` VALUES (1482275954393919490, 1, 0, 'aliyun_test01', 'ORM管理系统', '您好,欢迎注册HAX_ORM系统,您的验证码为${code},验证码5分钟之内有效,请勿泄露他人', '[\"code\"]', NULL, 'SMS_186968097', 1482275954393919492, 'ALIYUN', NULL, 1, '2022-01-15 16:58:22', '', NULL, '2022-01-16 07:10:41', b'0', 0);
INSERT INTO `sys_sms_template` VALUES (1482632755098443777, 1, 0, 'dd-01', '模板测试', 'FBIRD登录{code}', '[\"code\"]', NULL, 'dd-test', 1482631163473321986, 'DEBUG_DING_TALK', NULL, 1, '2022-01-16 16:36:09', '', NULL, '2022-01-16 08:36:09', b'0', 0);

SET FOREIGN_KEY_CHECKS = 1;
