/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 120.79.220.218:3306
 Source Schema         : young-flybirds-system

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 05/01/2023 11:44:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建者id',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新者id',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  `tenant_id` bigint(20) NULL DEFAULT 1 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow', NULL, NULL, b'0', 1);
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '初始化密码 123456', NULL, NULL, b'0', 1);
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '深色主题theme-dark，浅色主题theme-light', NULL, NULL, b'0', 1);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) NULL DEFAULT NULL COMMENT '租户id',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建用户id',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改用户id',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 111 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', 'FBIRD科技', 0, 'flybirds', '15888888888', 'flybirds@qq.com', '0', b'0', 'admin', '2018-03-16 11:33:00', 'admin', '2022-01-18 06:21:47', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '深圳总公司', 1, '若依', '15888888888', 'ry@qq.com', '0', b'0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '长沙分公司', 2, '若依', '15888888888', 'ry@qq.com', '0', b'0', 'admin', '2018-03-16 11:33:00', 'admin', '2022-01-18 06:21:47', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '研发部门', 1, '若依', '15888888888', 'ry@qq.com', '0', b'0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', '0', b'0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', 3, '若依', '15888888888', 'ry@qq.com', '0', b'0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '财务部门', 4, '若依', '15888888888', 'ry@qq.com', '0', b'0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', 5, '若依', '15888888888', 'ry@qq.com', '0', b'0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '市场部门', 1, '若依', '15888888888', 'ry@qq.com', '0', b'0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '财务部门', 2, '若依', '15888888888', 'ry@qq.com', '0', b'0', 'admin', '2018-03-16 11:33:00', 'admin', '2022-01-18 06:21:47', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (110, 102, '0,100,102', '测试部门', 3, 'hax', '17683991005', '1435501085@qq.com', '0', b'0', 'admin', '2022-01-18 06:22:27', '', NULL, NULL, 1, NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(20) NULL DEFAULT NULL,
  `update_user` bigint(20) NULL DEFAULT NULL,
  `del_flag` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 362 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2022-01-18 10:04:44', '性别男', NULL, 1, b'0');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2022-01-18 10:05:07', '性别女', NULL, 1, b'0');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2022-01-18 03:11:58', '性别未知', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '显示菜单', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '隐藏菜单', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2021-06-15 07:35:58', '正常状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2021-06-15 07:36:01', '停用状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '停用状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '默认分组', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统分组', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统默认是', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统默认否', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '公告', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '关闭状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '新增操作', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '修改操作', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '删除操作', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '授权操作', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '导出操作', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '导入操作', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '强退操作', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '生成操作', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '清空操作', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '停用状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (29, 1, '授权码模式', 'authorization_code', 'sys_grant_type', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '授权码模式', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (30, 2, '密码模式', 'password', 'sys_grant_type', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '密码模式', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (31, 3, '客户端模式', 'client_credentials', 'sys_grant_type', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '客户端模式', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (32, 4, '简化模式', 'implicit', 'sys_grant_type', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '简化模式', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (33, 5, '刷新模式', 'refresh_token', 'sys_grant_type', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '刷新模式', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (34, 0, 'varchar', 'varchar', 'sys_column_type', NULL, NULL, 'N', '0', 'admin', '2021-06-10 01:57:29', 'admin', '2021-06-10 02:35:28', '255,0', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (35, 1, 'bigint', 'bigint', 'sys_column_type', NULL, NULL, 'N', '0', 'admin', '2021-06-10 01:57:48', 'admin', '2021-06-10 02:20:35', '11,0', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (36, 2, 'char', 'char', 'sys_column_type', NULL, NULL, 'N', '0', 'admin', '2021-06-10 01:57:54', 'admin', '2021-06-10 02:20:45', '1,0', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (37, 3, 'int', 'int', 'sys_column_type', NULL, NULL, 'N', '0', 'admin', '2021-06-10 01:58:01', 'admin', '2021-06-10 02:21:09', '5,0', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (38, 4, 'float', 'float', 'sys_column_type', NULL, NULL, 'N', '0', 'admin', '2021-06-10 01:58:08', 'admin', '2021-06-10 02:22:27', '10,1', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (39, 5, 'double', 'double', 'sys_column_type', NULL, NULL, 'N', '0', 'admin', '2021-06-10 01:58:14', 'admin', '2021-06-10 02:22:36', '10,2', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (40, 6, 'date', 'date', 'sys_column_type', NULL, NULL, 'N', '0', 'admin', '2021-06-10 01:58:20', 'admin', '2021-06-10 02:22:42', '0,0', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (41, 7, 'datetime', 'datetime', 'sys_column_type', NULL, NULL, 'N', '0', 'admin', '2021-06-10 01:58:25', 'admin', '2021-06-10 02:22:48', '0,0', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (42, 7, 'enum', 'enum', 'sys_column_type', NULL, NULL, 'N', '0', 'admin', '2021-06-10 01:58:31', 'admin', '2021-06-10 02:34:48', '0,0', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (43, 8, 'decimal', 'decimal', 'sys_column_type', NULL, NULL, 'N', '0', 'admin', '2021-06-10 01:58:37', 'admin', '2021-06-10 02:34:56', '20,8', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (44, 9, 'josn', 'josn', 'sys_column_type', NULL, NULL, 'N', '0', 'admin', '2021-06-10 01:58:43', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (45, 0, '启用', '1', 'sys_general_stastus', NULL, NULL, 'N', '0', 'admin', '2021-06-10 15:31:15', 'admin', '2021-06-15 07:35:30', '启用', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (46, 0, '禁用', '0', 'sys_general_stastus', NULL, NULL, 'N', '0', 'admin', '2021-06-10 15:31:28', 'admin', '2021-06-15 07:35:36', '禁用', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (47, 0, '单表', 'single_table', 'sys_genplus_category', NULL, NULL, 'N', '0', 'admin', '2021-06-18 09:57:05', 'admin', '2021-06-18 16:12:58', '单表', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (49, 3, '左树右表单', 'single_left_tree_right_form', 'sys_genplus_category', NULL, NULL, 'N', '0', 'admin', '2021-06-18 09:59:57', 'admin', '2021-06-18 16:53:28', '左树右表单', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (50, 4, '左树右表格(主子表)', 'master_left_tree_right_table', 'sys_genplus_category', NULL, NULL, 'N', '0', 'admin', '2021-06-18 10:01:00', 'admin', '2021-06-18 16:55:19', '左树右表格(主子表)', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (51, 5, '主子表', 'master_child_table', 'sys_genplus_category', NULL, NULL, 'N', '0', 'admin', '2021-06-18 10:01:39', 'admin', '2021-06-18 10:06:16', '主子表', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (53, 1, '单树表', 'single_tree_table', 'sys_genplus_category', NULL, NULL, 'N', '0', 'admin', '2021-06-18 10:11:07', 'admin', '2021-06-18 16:13:50', '单树表\n', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (55, 0, '卡片式', 'card', 'sys_css_type', NULL, NULL, 'N', '0', 'admin', '2021-06-18 16:27:48', 'admin', '2021-06-28 09:50:04', '卡片式', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (56, 1, '弹窗式', 'pop', 'sys_css_type', NULL, NULL, 'N', '0', 'admin', '2021-06-18 16:28:03', 'admin', '2021-06-28 09:50:09', '弹窗式', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (100, 1, '人脸检测', 'faceDetect', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:57:10', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (101, 2, '植物识别', 'plant', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:57:25', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (102, 3, '银行卡识别', 'bankCard', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:57:37', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (103, 4, '身份证识别', 'idCard', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:57:48', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (104, 5, '车牌号识别', 'plate', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:58:01', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (105, 6, '驾驶证识别', 'driver', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:58:14', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (106, 7, '动物识别', 'animal', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:58:26', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (107, 8, '车型识别', 'car', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:58:43', 'admin', '2019-10-12 17:58:58', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (108, 9, '菜品识别', 'dish', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:59:09', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (109, 10, '通用文字识别', 'general_basic', 'ai_type', '', '', 'Y', '0', 'admin', '2019-10-12 17:59:21', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (110, 1, '头条', 'top', 'article_region', '', '', 'N', '0', 'admin', '2019-10-12 17:59:21', '', '2019-10-12 17:58:58', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (111, 2, '热门', 'hot', 'article_region', '', '', 'N', '0', 'admin', '2019-10-12 17:59:21', '', '2019-10-12 17:58:58', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (112, 3, '精选推荐', 'recommend', 'article_region', '', '', 'N', '0', 'admin', '2019-10-12 17:59:21', '', '2019-10-12 17:58:58', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (113, 4, '评论最多', 'most_comment', 'article_region', '', '', 'N', '0', 'admin', '2019-10-12 17:59:21', '', '2019-10-12 17:58:58', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (114, 5, '最新', 'new', 'article_region', '', '', 'Y', '0', 'admin', '2019-10-12 17:59:21', '', '2019-10-12 17:58:58', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (115, 1, '系统标签', 's', 'tags_type', '', 'primary', 'Y', '0', '', '2019-10-12 17:59:21', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (116, 2, '个人标签', 'p', 'tags_type', '', 'danger', 'N', '0', '', '2019-10-12 17:59:21', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (117, 3, '博客标签', 'blogTab', 'tags_type', '', 'success', 'N', '0', '', '2019-10-12 17:59:21', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (118, 2, '多骨鱼', 'duoguyu', 'article_model', '', 'success', 'N', '0', '', '2019-10-12 17:59:21', 'admin', '2021-05-01 08:37:03', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (119, 3, '其它', 'other', 'article_model', '', 'danger', 'N', '0', '', '2019-10-12 17:59:21', 'admin', '2021-05-01 08:37:36', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (120, 1, '未发送', '0', 'send_flag', '', 'info', 'Y', '0', 'admin', '2019-10-11 21:48:39', 'admin', '2019-10-12 17:12:43', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (121, 2, '已发送', '1', 'send_flag', '', 'success', 'N', '0', 'admin', '2019-10-11 21:49:00', 'admin', '2019-10-12 17:12:50', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (122, 1, '立即', '0', 'send_type', '', 'info', 'Y', '0', 'admin', '2019-10-11 21:48:39', 'admin', '2019-10-12 17:12:43', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (123, 2, '定时', '1', 'send_type', '', 'success', 'N', '0', 'admin', '2019-10-11 21:49:00', 'admin', '2019-10-12 17:12:50', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (124, 1, '草稿', '0', 'sys_available_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '草稿状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (125, 2, '已审核', '1', 'sys_available_status', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '已审核状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (126, 3, '待审核', '2', 'sys_available_status', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '待审核状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (127, 1, '未使用', '0', 'use_state', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2019-11-09 20:34:28', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (128, 2, '使用中', '1', 'use_state', '', 'danger', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2019-11-09 20:34:41', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (129, 1, '图片', '1', 'material_type', '', 'success', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '草稿状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (130, 2, '视频', '2', 'material_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '已审核状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (131, 3, '文本', '3', 'material_type', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '待审核状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (132, 4, '音频', '4', 'material_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '待审核状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (133, 5, '压缩', '5', 'material_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '待审核状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (134, 6, '其它', '6', 'material_type', '', 'default', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '待审核状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (135, 1, '推送', 'urls', 'baidu_push_type', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '待审核状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (136, 2, '更新', 'update', 'baidu_push_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '待审核状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (137, 3, '删除', 'del', 'baidu_push_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '待审核状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (138, 1, '相册', 'album', 'album_type', '', 'primary', 'Y', '0', 'admin', '2019-11-08 10:43:57', 'admin', '2019-11-16 18:24:37', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (140, 1, '待执行', 'wait', 'spider_mission_status', '', 'info', 'Y', '0', 'admin', '2019-11-11 14:23:39', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (141, 2, '执行中', 'running', 'spider_mission_status', '', 'success', 'N', '0', 'admin', '2019-11-11 14:24:20', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (142, 3, '完成', 'done', 'spider_mission_status', '', 'primary', 'N', '0', 'admin', '2019-11-11 14:25:16', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (143, 4, '错误', 'error', 'spider_mission_status', '', 'danger', 'N', '0', 'admin', '2019-11-11 14:26:29', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (144, 1, '默认', 'DEFAULT', 'spider_exit_way', '', 'default', 'Y', '0', 'admin', '2019-11-11 15:02:25', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (145, 2, '持续时间', 'DURATION', 'spider_exit_way', '', 'primary', 'N', '0', 'admin', '2019-11-11 15:04:01', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (146, 3, '链接计数', 'URL_COUNT', 'spider_exit_way', '', 'success', 'N', '0', 'admin', '2019-11-11 15:05:06', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (147, 1, 'Xpath', 'xpath', 'spider_extract_type', '', 'primary', 'Y', '0', 'admin', '2019-11-12 10:14:26', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (148, 2, 'Css', 'css', 'spider_extract_type', '', 'success', 'N', '0', 'admin', '2019-11-12 10:14:42', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (149, 3, '常量', 'constant', 'spider_extract_type', '', 'warning', 'N', '0', 'admin', '2019-11-12 10:15:07', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (150, 1, '替换', 'replace', 'field_value_process_type', '', 'primary', 'Y', '0', 'admin', '2019-11-14 17:01:32', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (151, 2, '截取之前', 'substrbefore', 'field_value_process_type', '', 'success', 'N', '0', 'admin', '2019-11-14 17:01:54', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (152, 3, '截取之后', 'substrafter', 'field_value_process_type', '', 'info', 'N', '0', 'admin', '2019-11-14 17:02:17', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (153, 1, '协议模板', 'xieyi', 'template_type', '', 'primary', 'Y', '0', 'admin', '2019-11-17 12:34:22', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (154, 2, '邮件模板', 'email', 'template_type', '', 'success', 'Y', '0', 'admin', '2019-11-17 12:34:45', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (155, 3, '短信模板', 'sms', 'template_type', '', '', 'Y', '0', 'admin', '2019-11-17 12:35:16', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (156, 1, '系统通知', 'system', 'site_msg_type', '', 'primary', 'Y', '0', 'admin', '2019-11-17 20:01:58', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (157, 2, '私信', 'private', 'site_msg_type', '', 'success', 'N', '0', 'admin', '2019-11-17 20:02:40', 'admin', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (158, 3, '留言', 'liuyan', 'site_msg_type', '', 'info', 'N', '0', 'admin', '2019-11-18 14:06:23', 'admin', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (159, 1, '文章评论', 'article', 'cms_comment_type', '', 'primary', 'Y', '0', 'admin', '2019-11-19 16:35:30', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (160, 2, '书籍评论', 'book', 'cms_comment_type', '', 'success', 'N', '0', 'admin', '2019-11-19 16:36:05', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (161, 3, '相册评论', 'album', 'cms_comment_type', '', 'info', 'N', '0', 'admin', '2019-11-19 16:37:06', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (162, 4, '系统留言', 'liuyan', 'cms_comment_type', '', 'warning', 'N', '0', 'admin', '2019-11-19 16:37:31', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (164, 1, '直接下载', '0', 'cms_download_type', '', 'default', 'Y', '0', 'admin', '2019-11-23 16:18:35', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (165, 2, '登录下载', '1', 'cms_download_type', '', 'success', 'N', '0', 'admin', '2019-11-23 16:18:55', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (166, 3, '付费下载', '2', 'cms_download_type', '', 'info', 'N', '0', 'admin', '2019-11-23 16:19:59', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (167, 1, '免费', '0', 'cms_pay_type', NULL, 'success', 'Y', '1', 'admin', '2019-11-23 16:17:25', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (168, 2, '积分支付', '1', 'cms_pay_type', '', 'primary', 'Y', '0', 'admin', '2019-11-23 16:21:09', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (169, 3, '人民币支付', '2', 'cms_pay_type', '', 'success', 'N', '0', 'admin', '2019-11-23 16:22:02', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (170, 1, '主题模板', '1', 'cms_resource_type', '', 'primary', 'Y', '0', 'admin', '2019-11-23 17:15:03', 'admin', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (171, 2, '小程序', '2', 'cms_resource_type', '', 'success', 'N', '0', 'admin', '2019-11-23 17:15:19', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (172, 3, '软件', '3', 'cms_resource_type', '', 'info', 'N', '0', 'admin', '2019-11-23 17:16:06', 'admin', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (173, 1, '电脑', 'Computer', 'client_device_type', '', 'primary', 'Y', '0', 'admin', '2019-11-29 08:42:07', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (174, 2, '手机', 'Mobile', 'client_device_type', '', 'success', 'N', '0', 'admin', '2019-11-29 08:42:26', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (175, 3, '平板', 'Tablet', 'client_device_type', '', 'info', 'N', '0', 'admin', '2019-11-29 08:42:51', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (176, 4, '未知设备', 'Unknown', 'client_device_type', '', 'warning', 'N', '0', 'admin', '2019-11-29 08:43:15', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (177, 1, '公共', 'public', 'share_type', '', 'primary', 'Y', '0', 'admin', '2019-12-31 08:45:54', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (178, 2, '会员', 'vip', 'share_type', '', 'success', 'N', '0', 'admin', '2019-12-31 08:49:08', 'admin', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (179, 1, '最新', 'new', 'common_flag', '', 'default', 'Y', '0', 'admin', '2020-01-04 13:22:08', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (180, 2, '最热', 'hot', 'common_flag', '', 'primary', 'N', '0', 'admin', '2020-01-04 13:22:23', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (181, 1, '开发', '1', 'project_mission_type', '', 'default', 'Y', '0', 'admin', '2020-01-10 10:12:54', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (182, 2, 'Bug', '2', 'project_mission_type', '', 'primary', 'N', '0', 'admin', '2020-01-10 10:13:17', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (183, 1, '专科以下', '1', 'education', '', '', 'Y', '0', 'admin', '2020-04-15 15:18:53', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (184, 2, '专科', '2', 'education', '', '', 'N', '0', 'admin', '2020-04-15 15:19:02', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (185, 3, '本科', '3', 'education', '', '', 'N', '0', 'admin', '2020-04-15 15:19:31', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (186, 4, '研究生', '4', 'education', '', '', 'N', '0', 'admin', '2020-04-15 15:20:06', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (187, 5, '博士', '5', 'education', '', '', 'N', '0', 'admin', '2020-04-15 15:20:19', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (188, 6, '博士后', '6', 'education', '', '', 'N', '0', 'admin', '2020-04-15 15:20:35', 'admin', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (189, 7, '在读研究生', '7', 'education', '', '', 'N', '0', 'admin', '2020-04-15 15:21:37', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (190, 8, '在读博士', '8', 'education', '', '', 'N', '0', 'admin', '2020-04-15 15:21:51', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (191, 1, '未婚', '1', 'marriage', '', '', 'Y', '0', 'admin', '2020-04-15 15:22:58', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (192, 2, '已婚', '2', 'marriage', '', '', 'N', '0', 'admin', '2020-04-15 15:23:12', 'admin', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (193, 3, '离异', '3', 'marriage', '', '', 'N', '0', 'admin', '2020-04-15 15:23:34', 'admin', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (194, 4, '丧偶', '4', 'marriage', '', '', 'N', '0', 'admin', '2020-04-15 15:24:11', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (195, 1, '汉族', '1', 'minzu', '', '', 'Y', '0', 'admin', '2020-04-15 15:26:05', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (196, 2, '壮族', '2', 'minzu', '', '', 'N', '0', 'admin', '2020-04-15 15:26:26', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (197, 3, '满族', '3', 'minzu', '', '', 'N', '0', 'admin', '2020-04-15 15:26:38', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (198, 4, '回族', '4', 'minzu', '', '', 'Y', '0', 'admin', '2020-04-15 15:26:46', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (199, 5, '苗族', '5', 'minzu', '', '', 'N', '0', 'admin', '2020-04-15 15:27:06', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (200, 6, '维吾尔族', '6', 'minzu', '', '', 'N', '0', 'admin', '2020-04-15 15:27:23', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (201, 7, '土家族', '7', 'minzu', '', '', 'N', '0', 'admin', '2020-04-15 15:27:43', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (202, 8, '彝族', '8', 'minzu', '', '', 'N', '0', 'admin', '2020-04-15 15:27:52', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (203, 9, '蒙古族', '9', 'minzu', '', '', 'N', '0', 'admin', '2020-04-15 15:28:14', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (204, 10, '藏族', '10', 'minzu', '', '', 'N', '0', 'admin', '2020-04-15 15:28:34', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (205, 11, '其它', '11', 'minzu', '', '', 'N', '0', 'admin', '2020-04-15 15:28:43', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (206, 1, '无', '0', 'children', '', '', 'Y', '0', 'admin', '2020-04-15 15:29:57', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (207, 2, '有', '1', 'children', '', '', 'N', '0', 'admin', '2020-04-15 15:30:08', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (208, 1, '3000以下', '1', 'salary', '', '', 'Y', '0', 'admin', '2020-04-15 15:31:06', 'admin', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (209, 2, '3001-5000元', '2', 'salary', '', '', 'N', '0', 'admin', '2020-04-15 15:31:45', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (210, 3, '5001-8000元', '3', 'salary', '', '', 'N', '0', 'admin', '2020-04-15 15:32:03', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (211, 4, '8001-10000元', '4', 'salary', '', '', 'N', '0', 'admin', '2020-04-15 15:32:24', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (212, 5, '1万-2万', '5', 'salary', '', '', 'N', '0', 'admin', '2020-04-15 15:32:45', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (213, 6, '2万-5万', '6', 'salary', '', '', 'N', '0', 'admin', '2020-04-15 15:33:10', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (214, 7, '5万以上', '7', 'salary', '', '', 'N', '0', 'admin', '2020-04-15 15:33:37', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (215, 1, 'O型', '1', 'xuexing', '', '', 'Y', '0', 'admin', '2020-04-15 15:43:02', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (216, 2, 'A型', '2', 'xuexing', '', '', 'N', '0', 'admin', '2020-04-15 15:43:13', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (217, 3, 'B型', '3', 'xuexing', '', '', 'N', '0', 'admin', '2020-04-15 15:43:28', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (218, 4, 'AB型', '4', 'xuexing', '', '', 'Y', '0', 'admin', '2020-04-15 15:43:43', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (219, 2, '收费', '1', 'cms_free', '', 'success', 'N', '0', 'admin', '2019-11-23 16:17:25', '', '2019-10-12 17:59:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (220, 1, '垃圾广告信息', '1', 'report_type', NULL, NULL, 'Y', '0', 'admin', '2021-03-15 16:13:14', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (221, 2, '涉黄涉暴等违法信息', '2', 'report_type', NULL, 'primary', 'N', '0', 'admin', '2021-03-15 16:13:39', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (222, 3, '侮辱、恶意及辱骂行为', '3', 'report_type', NULL, 'success', 'Y', '0', 'admin', '2021-03-15 16:13:58', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (223, 4, '侵权', '4', 'report_type', NULL, 'info', 'N', '0', 'admin', '2021-03-15 16:14:43', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (224, 1, '每日登陆', '1', 'forum_in_type', NULL, 'primary', 'Y', '0', 'admin', '2021-03-18 19:16:55', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (225, 2, '每日签到', '2', 'forum_in_type', NULL, 'success', 'Y', '0', 'admin', '2021-03-18 19:17:12', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (226, 3, '发表帖子', '3', 'forum_in_type', NULL, 'info', 'Y', '0', 'admin', '2021-03-18 19:17:52', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (227, 1, '下载模板', '1', 'forum_out_type', NULL, 'primary', 'Y', '0', 'admin', '2021-03-19 20:04:15', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (228, 2, '下载附件', '2', 'forum_out_type', NULL, 'info', 'N', '0', 'admin', '2021-03-19 20:04:34', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (229, 1, '置顶', 'top', 'forum_question_region', '', 'primary', 'Y', '0', 'admin', '2021-03-20 18:31:41', 'admin', '2021-03-20 18:32:53', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (230, 2, '精品', 'good', 'forum_question_region', '', 'success', 'N', '0', 'admin', '2021-03-20 18:31:58', 'admin', '2021-03-20 18:33:03', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (231, 3, '推荐', 'recommend', 'forum_question_region', NULL, 'warning', 'N', '0', 'admin', '2021-03-20 18:32:40', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (232, 4, '内容至为推荐', '4', 'forum_in_type', '', 'warning', 'N', '0', 'admin', '2021-03-21 09:12:53', 'admin', '2021-03-21 09:16:21', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (233, 5, '内容至为精品', '5', 'forum_in_type', '', 'danger', 'N', '0', 'admin', '2021-03-21 09:13:19', 'admin', '2021-03-21 09:16:26', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (234, 6, '内容被置顶', '6', 'forum_in_type', '', 'primary', 'Y', '0', 'admin', '2021-03-21 09:13:44', 'admin', '2021-03-21 09:16:30', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (235, 1, '私信', 'private', 'forum_msg_type', NULL, 'primary', 'Y', '0', 'admin', '2021-03-24 20:48:06', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (236, 2, '系统', 'system', 'forum_msg_type', NULL, 'info', 'N', '0', 'admin', '2021-03-24 20:48:24', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (237, 3, '公告', 'notice', 'forum_msg_type', NULL, 'warning', 'N', '0', 'admin', '2021-03-24 20:49:13', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (238, 4, '关注', 'focus', 'forum_msg_type', NULL, 'danger', 'N', '0', 'admin', '2021-03-24 20:50:20', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (239, 5, '推荐', 'recommend', 'forum_msg_type', NULL, 'default', 'N', '0', 'admin', '2021-03-24 20:52:33', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (240, 6, '留言', 'comment', 'forum_msg_type', NULL, 'primary', 'Y', '0', 'admin', '2021-03-25 19:04:17', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (241, 1, '置为草稿', '0', 'forum_report_deal_type', '', 'default', 'Y', '0', 'admin', '2021-04-01 10:14:26', 'admin', '2021-04-01 10:15:30', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (242, 2, '审核通过', '1', 'forum_report_deal_type', NULL, 'success', 'N', '0', 'admin', '2021-04-01 10:14:54', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (243, 3, '删除', '2', 'forum_report_deal_type', '', 'info', 'Y', '0', 'admin', '2021-04-01 10:15:17', 'admin', '2021-04-01 10:36:39', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (244, 1, '今夕何夕', 'jxhx', 'article_model', '', 'warning', 'Y', '0', 'admin', '2021-05-01 08:35:53', 'admin', '2021-05-01 08:36:55', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (245, 6, '焦点', 'focus', 'article_region', NULL, NULL, 'N', '0', 'admin', '2021-05-03 14:22:07', '', '2021-05-03 14:22:09', NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (246, 1, '免费', '0', 'template_pay_type', NULL, NULL, 'Y', '0', 'admin', '2021-06-18 15:09:36', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (247, 2, '积分支付', '1', 'template_pay_type', NULL, NULL, 'N', '0', 'admin', '2021-06-18 15:09:52', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (248, 3, '人民币支付', '2', 'template_pay_type', NULL, NULL, 'N', '0', 'admin', '2021-06-18 15:10:04', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (249, 1, '自动获取', '0', 'obtain_type', NULL, NULL, 'Y', '0', 'admin', '2021-06-28 14:10:46', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (250, 2, '联系管理', '1', 'obtain_type', NULL, NULL, 'N', '0', 'admin', '2021-06-28 14:10:59', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (251, 1, '入账(+)', '1', 'plus_flag', NULL, NULL, 'Y', '0', 'admin', '2021-06-28 14:21:20', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (252, 2, '出账(-)', '0', 'plus_flag', NULL, NULL, 'N', '0', 'admin', '2021-06-28 14:21:31', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (253, 1, '模板', '1', 'score_goods_type', NULL, NULL, 'Y', '0', 'admin', '2021-06-28 23:00:54', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (254, 2, '虚拟商品', '2', 'score_goods_type', '', '', 'N', '0', 'admin', '2021-06-28 23:01:14', 'admin', '2021-06-28 23:01:34', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (255, 3, '实物商品', '3', 'score_goods_type', NULL, NULL, 'N', '0', 'admin', '2021-06-28 23:01:29', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (256, 1, '二居室', '1', 'decorate_house_type', NULL, NULL, 'Y', '0', 'admin', '2021-07-02 18:48:42', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (257, 2, '三居室', '2', 'decorate_house_type', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:49:00', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (258, 3, '四居室', '3', 'decorate_house_type', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:49:12', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (259, 4, '别墅', '4', 'decorate_house_type', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:49:44', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (260, 5, '其它户型', '5', 'decorate_house_type', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:49:59', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (261, 1, '100㎡以下', '1', 'decorate_house_area', NULL, NULL, 'Y', '0', 'admin', '2021-07-02 18:51:36', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (262, 2, '100-140㎡', '2', 'decorate_house_area', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:51:57', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (263, 3, '140-200㎡', '3', 'decorate_house_area', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:52:28', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (264, 4, '200-300㎡', '4', 'decorate_house_area', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:52:48', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (265, 5, '300㎡以上', '5', 'decorate_house_area', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:53:05', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (266, 1, '北欧', '1', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:54:04', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (267, 2, '简欧', '2', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:54:29', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (268, 3, '新中式', '3', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:54:43', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (269, 4, '美式', '4', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:54:58', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (270, 5, '现代', '5', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:55:10', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (271, 6, '田园', '6', 'decorate_house_style', '', '', 'N', '0', 'admin', '2021-07-02 18:55:23', 'admin', '2021-07-02 18:55:27', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (272, 7, '欧式', '7', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:55:41', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (273, 8, '美式乡村', '8', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:55:57', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (274, 9, '中式', '9', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:56:11', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (275, 10, '地中海', '10', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:56:24', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (276, 11, '混搭', '11', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:56:36', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (277, 12, '日式', '12', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:57:17', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (278, 13, '法式', '13', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:57:31', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (279, 14, '港式', '14', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:57:44', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (280, 15, '台式', '15', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:58:01', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (281, 16, '轻奢', '16', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:58:17', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (282, 17, '简美', '17', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:58:33', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (283, 18, '工业风', '18', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:58:48', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (284, 19, '其它', '19', 'decorate_house_style', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:58:58', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (285, 1, '客厅', '1', 'decorate_house_space', NULL, NULL, 'Y', '0', 'admin', '2021-07-02 18:59:41', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (286, 2, '卧室', '2', 'decorate_house_space', NULL, NULL, 'N', '0', 'admin', '2021-07-02 18:59:57', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (287, 3, '厨房', '3', 'decorate_house_space', NULL, NULL, 'N', '0', 'admin', '2021-07-02 19:00:08', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (288, 4, '餐厅', '4', 'decorate_house_space', NULL, NULL, 'N', '0', 'admin', '2021-07-02 19:00:17', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (289, 5, '卫生间', '5', 'decorate_house_space', NULL, NULL, 'N', '0', 'admin', '2021-07-02 19:00:30', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (290, 6, '衣帽间', '6', 'decorate_house_space', NULL, NULL, 'N', '0', 'admin', '2021-07-02 19:00:40', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (291, 7, '书房', '7', 'decorate_house_space', NULL, NULL, 'Y', '0', 'admin', '2021-07-02 19:00:54', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (292, 8, '阳台', '8', 'decorate_house_space', NULL, NULL, 'N', '0', 'admin', '2021-07-02 19:01:04', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (293, 9, '入户花园', '9', 'decorate_house_space', NULL, NULL, 'N', '0', 'admin', '2021-07-02 19:01:20', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (294, 10, '过道', '10', 'decorate_house_space', NULL, NULL, 'N', '0', 'admin', '2021-07-02 19:01:40', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (295, 11, '玄关', '11', 'decorate_house_space', NULL, NULL, 'N', '0', 'admin', '2021-07-02 19:01:55', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (296, 12, '次卧', '12', 'decorate_house_space', NULL, NULL, 'N', '0', 'admin', '2021-07-02 19:02:06', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (297, 1, '优秀设计师', '1', 'decorate_designer_title', NULL, NULL, 'Y', '0', 'admin', '2021-07-02 19:04:38', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (298, 2, '主任设计师', '2', 'decorate_designer_title', NULL, NULL, 'Y', '0', 'admin', '2021-07-02 19:04:47', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (299, 3, '明星设计师', '3', 'decorate_designer_title', NULL, NULL, 'N', '0', 'admin', '2021-07-02 20:50:12', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (300, 4, '设计总监', '4', 'decorate_designer_title', NULL, NULL, 'N', '0', 'admin', '2021-07-02 20:50:24', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (301, 5, '金牌设计师', '5', 'decorate_designer_title', NULL, NULL, 'N', '0', 'admin', '2021-07-02 20:50:50', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (302, 6, '首席设计师', '6', 'decorate_designer_title', NULL, NULL, 'N', '0', 'admin', '2021-07-02 20:51:20', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (303, 1, '装修攻略', 'strategy', 'decorate_article_type1', '', '', 'N', '0', 'admin', '2021-07-13 21:03:11', 'admin', '2021-07-14 20:34:33', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (304, 2, '装修装潢', 'zhuanghuang', 'decorate_article_type1', NULL, NULL, 'N', '0', 'admin', '2021-07-13 21:03:39', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (305, 4, '替换掉html标签', 'replace_html', 'field_value_process_type', NULL, NULL, 'N', '0', 'admin', '2021-07-14 10:46:33', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (306, 5, '替换掉a标签', 'replace_a', 'field_value_process_type', '', '', 'N', '0', 'admin', '2021-07-14 11:25:47', 'admin', '2021-07-14 13:20:01', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (307, 1, '下载单张图片', '1', 'spider_high_setting', NULL, NULL, 'N', '0', 'admin', '2021-07-14 13:32:51', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (308, 2, '下载内容详情图片', '2', 'spider_high_setting', NULL, NULL, 'N', '0', 'admin', '2021-07-14 13:33:19', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (309, 3, '自动选取封面图片', '3', 'spider_high_setting', NULL, NULL, 'N', '0', 'admin', '2021-07-14 13:33:45', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (310, 1, '装修水电', 'electric', 'decorate_article_type2_1', NULL, NULL, 'N', '0', 'admin', '2021-07-14 20:28:13', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (311, 2, '装修木工', 'carpenter', 'decorate_article_type2_1', NULL, NULL, 'N', '0', 'admin', '2021-07-14 20:28:28', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (312, 3, '装修设计', 'design', 'decorate_article_type2_1', NULL, NULL, 'N', '0', 'admin', '2021-07-14 20:28:43', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (313, 4, '装修入住', 'check_in', 'decorate_article_type2_1', NULL, NULL, 'N', '0', 'admin', '2021-07-14 20:28:58', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (314, 5, '装修收房', 'inspected', 'decorate_article_type2_1', NULL, NULL, 'N', '0', 'admin', '2021-07-14 20:29:12', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (315, 6, '装修竣工', 'completion', 'decorate_article_type2_1', NULL, NULL, 'N', '0', 'admin', '2021-07-14 20:29:28', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (316, 1, '别墅装潢', 'bieshu', 'decorate_article_type2_2', NULL, NULL, 'N', '0', 'admin', '2021-07-14 20:29:48', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (317, 2, '室内装潢', 'shinei', 'decorate_article_type2_2', NULL, NULL, 'N', '0', 'admin', '2021-07-14 20:30:04', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (318, 3, '装潢设计', 'sheji', 'decorate_article_type2_2', NULL, NULL, 'N', '0', 'admin', '2021-07-14 20:30:16', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (319, 4, '装潢效果图', 'xgt', 'decorate_article_type2_2', NULL, NULL, 'N', '0', 'admin', '2021-07-14 20:30:29', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (320, 5, '装潢攻略', 'gonglve', 'decorate_article_type2_2', NULL, NULL, 'N', '0', 'admin', '2021-07-14 20:30:45', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (321, 6, '装潢预算', 'yusuan', 'decorate_article_type2_2', NULL, NULL, 'N', '0', 'admin', '2021-07-14 20:30:58', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (322, 1, '模板', 'template', 'coupon_category', NULL, NULL, 'N', '0', 'admin', '2021-07-16 20:49:04', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (323, 2, '插件', 'plug', 'coupon_category', NULL, NULL, 'N', '0', 'admin', '2021-07-16 20:49:41', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (324, 1, '直抵', '1', 'coupon_type', NULL, NULL, 'N', '0', 'admin', '2021-07-16 20:50:15', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (325, 2, '满减', '2', 'coupon_type', NULL, NULL, 'N', '0', 'admin', '2021-07-16 20:50:25', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (326, 1, '捐赠充值', 'donate', 'in_out_type', NULL, NULL, 'N', '0', 'admin', '2021-07-19 20:42:26', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (327, 2, '微信充值', 'wechat', 'in_out_type', '', '', 'N', '0', 'admin', '2021-07-19 20:43:05', 'admin', '2021-07-19 20:44:51', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (328, 3, '支付宝充值', 'alipay', 'in_out_type', NULL, NULL, 'N', '0', 'admin', '2021-07-19 20:45:06', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (329, 4, '购买模板', 'template', 'in_out_type', NULL, NULL, 'N', '0', 'admin', '2021-07-19 20:45:47', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (330, 5, '购买插件', 'plug', 'in_out_type', NULL, NULL, 'N', '0', 'admin', '2021-07-19 20:46:01', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (332, 0, '草稿', '1', 'doc_available_status', NULL, NULL, 'N', '0', 'admin', '2021-08-09 09:35:35', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (333, 2, '未审核', '3', 'doc_available_status', NULL, NULL, 'N', '0', 'admin', '2021-08-09 09:35:53', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (334, 1, '已审核', '2', 'doc_available_status', NULL, NULL, 'N', '0', 'admin', '2021-08-09 09:36:01', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (335, 0, '待审核', '0', 'audit_state', NULL, NULL, 'N', '0', 'admin', '2021-08-11 09:52:47', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (336, 0, '审核通过', '1', 'audit_state', NULL, NULL, 'N', '0', 'admin', '2021-08-11 09:53:05', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (337, 0, '审核未通过', '2', 'audit_state', NULL, NULL, 'N', '0', 'admin', '2021-08-11 09:53:12', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (338, 0, '调试(钉钉)', 'DEBUG_DING_TALK', 'sys_sms_channel_code', NULL, NULL, 'N', '0', 'admin', '2022-01-15 02:51:30', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (339, 1, '阿里云', 'ALIYUN', 'sys_sms_channel_code', NULL, NULL, 'N', '0', 'admin', '2022-01-15 02:51:52', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (340, 0, '验证码', '1', 'sys_sms_template_type', NULL, NULL, 'N', '0', 'admin', '2022-01-15 04:09:21', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (341, 1, '通知', '2', 'sys_sms_template_type', NULL, NULL, 'N', '0', 'admin', '2022-01-15 04:09:30', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (342, 2, '营销', '3', 'sys_sms_template_type', NULL, NULL, 'N', '0', 'admin', '2022-01-15 04:09:40', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (343, 0, '初始化', '0', 'sys_sms_send_status', NULL, NULL, 'N', '0', 'admin', '2022-01-15 04:10:40', 'admin', '2022-01-15 04:10:51', NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (344, 1, '发送成功', '10', 'sys_sms_send_status', NULL, NULL, 'N', '0', 'admin', '2022-01-15 04:10:59', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (345, 2, '发送失败', '20', 'sys_sms_send_status', NULL, NULL, 'N', '0', 'admin', '2022-01-15 04:11:07', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (346, 3, '不发送', '30', 'sys_sms_send_status', NULL, NULL, 'N', '0', 'admin', '2022-01-15 04:11:16', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (347, 0, '等待结果', '0', 'sys_sms_receive_status', NULL, NULL, 'N', '0', 'admin', '2022-01-15 04:12:33', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (348, 1, '接收成功', '10', 'sys_sms_receive_status', NULL, NULL, 'N', '0', 'admin', '2022-01-15 04:12:41', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (349, 2, '接收失败', '20', 'sys_sms_receive_status', NULL, NULL, 'N', '0', 'admin', '2022-01-15 04:12:50', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (350, 0, '自动生成', '1', 'sys_error_code_type', NULL, NULL, 'N', '0', 'admin', '2022-01-15 04:14:43', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (351, 1, '手动编辑', '2', 'sys_error_code_type', NULL, NULL, 'N', '0', 'admin', '2022-01-15 04:14:51', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (352, 0, '会员', '1', 'user_type', NULL, NULL, 'N', '0', 'admin', '2022-01-15 06:29:36', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (353, 1, '管理员', '2', 'user_type', NULL, NULL, 'N', '0', 'admin', '2022-01-15 06:29:44', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (354, 10, '查询', '10', 'sys_oper_type', NULL, NULL, 'N', '0', 'admin', '2022-01-18 02:56:21', 'admin', '2022-01-18 02:57:15', '查询操作', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (355, 11, '同步', '11', 'sys_oper_type', NULL, NULL, 'N', '0', 'admin', '2022-01-18 02:56:49', 'admin', '2022-01-18 02:57:23', '同步操作', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (356, 12, '刷新', '12', 'sys_oper_type', NULL, NULL, 'N', '0', 'admin', '2022-01-18 02:56:59', 'admin', '2022-01-18 02:57:31', '刷新操作', NULL, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (357, 0, '未处理', '0', 'api_error_log_process_status', NULL, NULL, 'N', '0', 'admin', '2022-01-19 03:22:53', '', NULL, NULL, 1, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (358, 1, '已处理', '1', 'api_error_log_process_status', NULL, NULL, 'N', '0', 'admin', '2022-01-19 03:23:02', '', NULL, NULL, 1, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (359, 2, '已忽略', '2', 'api_error_log_process_status', NULL, NULL, 'N', '0', 'admin', '2022-01-19 03:23:09', '', NULL, NULL, 1, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (360, 0, '公开', '0', 'sys_dynamic_type', NULL, NULL, 'N', '0', 'admin', '2022-01-27 11:26:56', '', NULL, NULL, 1, NULL, b'0');
INSERT INTO `sys_dict_data` VALUES (361, 1, '私有', '1', 'sys_dynamic_type', NULL, NULL, 'N', '0', 'admin', '2022-01-27 11:27:05', '', NULL, NULL, 1, NULL, b'0');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(20) NULL DEFAULT NULL,
  `update_user` bigint(20) NULL DEFAULT NULL,
  `del_flag` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 164 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2021-07-14 16:03:56', '用户性别列表', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '菜单状态列表', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统开关列表', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '任务状态列表', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '任务分组列表', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统是否列表', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知类型列表', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知状态列表', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '操作类型列表', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '登录状态列表', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (11, '授权类型', 'sys_grant_type', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '授权类型列表', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (12, '数据库物理类型', 'sys_column_type', '0', 'admin', '2021-06-10 01:57:01', 'admin', '2021-06-10 02:05:54', '数据库物理类型', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (13, '通用状态', 'sys_general_stastus', '0', 'admin', '2021-06-10 15:30:20', '', NULL, '系统通用状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (14, '代码生成类别', 'sys_genplus_category', '0', 'admin', '2021-06-18 09:55:27', 'admin', '2021-06-18 09:55:55', 'mybatisPlus', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (15, '系统界面样式', 'sys_css_type', '0', 'admin', '2021-06-18 16:27:31', '', NULL, '系统界面样式', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (100, '人工智能识别类型', 'ai_type', '0', 'admin', '2019-10-12 17:55:24', 'ry', '2019-10-28 20:05:34', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (101, '文章专区', 'article_region', '0', 'admin', '2019-10-12 17:55:24', 'ry', '2019-10-28 20:05:31', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (102, '标签类型', 'tags_type', '0', 'admin', '2019-10-12 17:55:24', 'ry', '2019-10-28 20:05:31', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (103, '文章模型', 'article_model', '0', 'admin', '2019-10-12 17:55:24', 'ry', '2019-10-28 20:05:31', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (104, '发送标志', 'send_flag', '0', 'admin', '2019-10-12 17:55:24', 'admin', '2019-10-12 17:55:24', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (105, '发送类型', 'send_type', '0', 'admin', '2019-10-12 17:55:24', 'admin', '2019-10-12 17:55:24', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (106, '文章状态', 'sys_available_status', '0', 'admin', '2019-10-12 17:55:24', 'admin', '2019-10-12 17:55:24', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (107, '使用状态', 'use_state', '0', 'admin', '2019-10-12 17:55:24', 'admin', '2019-10-12 17:55:24', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (108, '素材类型', 'material_type', '0', 'admin', '2019-10-12 17:55:24', 'admin', '2019-10-12 17:55:24', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (109, '百度推送类型', 'baidu_push_type', '0', 'admin', '2019-10-12 17:55:24', 'admin', '2019-10-12 17:55:24', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (110, '相册类型', 'album_type', '0', 'admin', '2019-11-08 10:41:04', '', '2019-11-08 10:41:04', '相册类型', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (111, '爬虫任务状态', 'spider_mission_status', '0', 'admin', '2019-11-11 14:22:40', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (112, '爬虫退出方式', 'spider_exit_way', '0', 'admin', '2019-11-11 15:01:27', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (113, '爬虫内容提取类型', 'spider_extract_type', '0', 'admin', '2019-11-12 10:13:40', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (114, '爬虫值处理规则', 'field_value_process_type', '0', 'admin', '2019-11-14 17:01:07', '', '2019-11-08 10:41:04', '爬虫字段值处理规则', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (115, '模板分类', 'template_type', '0', 'admin', '2019-11-17 12:33:38', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (116, '站内消息类型', 'site_msg_type', '0', 'admin', '2019-11-17 20:01:30', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (117, '评论类型', 'cms_comment_type', '0', 'admin', '2019-11-19 16:35:02', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (118, '收/免费标志', 'cms_free', '0', 'admin', '2019-11-23 16:16:43', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (119, '资源下载类型', 'cms_download_type', '0', 'admin', '2019-11-23 16:17:59', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (120, '支付类型', 'cms_pay_type', '0', 'admin', '2019-11-23 16:20:42', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (121, '资源类型', 'cms_resource_type', '0', 'admin', '2019-11-23 17:14:36', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (122, '客户端设备类型', 'client_device_type', '0', 'admin', '2019-11-29 08:41:28', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (123, '共享类型', 'share_type', '0', 'admin', '2019-12-31 08:43:40', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (124, '状态标志', 'common_flag', '0', 'admin', '2020-01-04 13:21:49', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (125, '项目任务类型', 'project_mission_type', '0', 'admin', '2020-01-10 10:12:30', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (126, '学历', 'education', '0', 'admin', '2020-04-15 15:17:39', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (127, '婚姻状态', 'marriage', '0', 'admin', '2020-04-15 15:22:18', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (128, '民族', 'minzu', '0', 'admin', '2020-04-15 15:25:48', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (129, '有无子女', 'children', '0', 'admin', '2020-04-15 15:29:36', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (130, '月收入', 'salary', '0', 'admin', '2020-04-15 15:30:40', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (131, '血型', 'xuexing', '0', 'admin', '2020-04-15 15:42:16', '', '2019-11-08 10:41:04', '', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (132, '举报类型', 'report_type', '0', 'admin', '2021-03-15 16:11:58', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (133, '入账类型', 'forum_in_type', '0', 'admin', '2021-03-18 19:16:01', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (134, '论坛出账类型', 'forum_out_type', '0', 'admin', '2021-03-19 20:03:51', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (135, '论坛问题分区', 'forum_question_region', '0', 'admin', '2021-03-20 18:30:49', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (136, '论坛消息类型', 'forum_msg_type', '0', 'admin', '2021-03-24 20:47:19', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (137, '论坛举报处理类型', 'forum_report_deal_type', '0', 'admin', '2021-04-01 10:13:51', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (138, '模板付费类型', 'template_pay_type', '0', 'admin', '2021-06-18 15:08:52', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (139, '获取方式', 'obtain_type', '0', 'admin', '2021-06-28 14:10:22', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (140, '加减标志', 'plus_flag', '0', 'admin', '2021-06-28 14:20:56', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (141, '积分商品类型', 'score_goods_type', '0', 'admin', '2021-06-28 23:00:25', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (142, '户型', 'decorate_house_type', '0', 'admin', '2021-07-02 18:45:51', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (143, '面积', 'decorate_house_area', '0', 'admin', '2021-07-02 18:50:46', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (144, '风格', 'decorate_house_style', '0', 'admin', '2021-07-02 18:53:44', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (145, '空间', 'decorate_house_space', '0', 'admin', '2021-07-02 18:59:27', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (146, '设计师头衔', 'decorate_designer_title', '0', 'admin', '2021-07-02 19:04:24', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (147, '装修文章大类', 'decorate_article_type1', '0', 'admin', '2021-07-13 21:02:39', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (148, '爬虫高级设置', 'spider_high_setting', '0', 'admin', '2021-07-14 13:30:36', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (149, '装修攻略', 'decorate_article_type2_1', '0', 'admin', '2021-07-14 20:27:01', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (150, '装修装潢', 'decorate_article_type2_2', '0', 'admin', '2021-07-14 20:27:14', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (151, '优惠券分类', 'coupon_category', '0', 'admin', '2021-07-16 20:48:42', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (152, '优惠券类型', 'coupon_type', '0', 'admin', '2021-07-16 20:50:02', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (153, '出入账类型', 'in_out_type', '0', 'admin', '2021-07-19 20:41:48', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (154, '稿件审核状态', 'doc_available_status', '0', 'admin', '2021-08-09 09:30:04', '', NULL, '稿件审核状态', NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (155, '系统审核', 'audit_state', '0', 'admin', '2021-08-11 09:52:28', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (156, '短信渠道编码', 'sys_sms_channel_code', '0', 'admin', '2022-01-15 02:50:52', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (157, '短信模板的类型', 'sys_sms_template_type', '0', 'admin', '2022-01-15 04:08:49', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (158, '短信发送状态', 'sys_sms_send_status', '0', 'admin', '2022-01-15 04:10:20', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (159, '短信接收状态', 'sys_sms_receive_status', '0', 'admin', '2022-01-15 04:11:53', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (160, '错误码的类型', 'sys_error_code_type', '0', 'admin', '2022-01-15 04:14:26', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (161, '用户类型', 'user_type', '0', 'admin', '2022-01-15 06:29:22', '', NULL, NULL, NULL, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (162, '处理状态', 'api_error_log_process_status', '0', 'admin', '2022-01-19 03:22:33', '', NULL, '处理状态', 1, NULL, b'0');
INSERT INTO `sys_dict_type` VALUES (163, '动态类型', 'sys_dynamic_type', '0', 'admin', '2022-01-27 11:26:23', '', NULL, NULL, 1, NULL, b'0');

-- ----------------------------
-- Table structure for sys_dynamic
-- ----------------------------
DROP TABLE IF EXISTS `sys_dynamic`;
CREATE TABLE `sys_dynamic`  (
  `dynamic_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dynamic_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `dynamic_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型（1私有 2公共）',
  `dynamic_content` longblob NULL COMMENT '内容',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1关闭）',
  `create_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(20) NULL DEFAULT NULL,
  `update_user` bigint(20) NULL DEFAULT NULL,
  `imgs` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片img',
  `avatar` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `del_flag` bit(1) NOT NULL DEFAULT b'0',
  `tenant_id` bigint(20) NULL DEFAULT 1 COMMENT '租户ID',
  PRIMARY KEY (`dynamic_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dynamic
-- ----------------------------
INSERT INTO `sys_dynamic` VALUES (1, '温馨提醒：2022/1/27 flybirds新版本发布啦', '0', 0x3C703EE58AA8E68081E6B58BE8AF95EFBC8CE4BB8AE5A4A9E5BE88E5BC80E5BF83E591803C2F703E, '0', 'admin', '2022-01-27 11:33:00', 'ry', '2022-01-27 14:36:48', '管理员', NULL, NULL, 'https://wpimg.wallstcn.com/bcce3734-0837-4b9f-9261-351ef384f75a.jpg?imageView2/2/h/440,https://wpimg.wallstcn.com/50530061-851b-4ca5-9dc5-2fead928a939.jpg?imageView2/2/h/440,https://wpimg.wallstcn.com/9679ffb0-9e0b-4451-9916-e21992218054.jpg?imageView2/2/h/440', 'http://120.79.220.218:9000/demo/flybirds_logo.png', b'0', 1);
INSERT INTO `sys_dynamic` VALUES (2, '维护通知：2018-07-01 若依系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '管理员', NULL, NULL, 'https://wpimg.wallstcn.com/bcce3734-0837-4b9f-9261-351ef384f75a.jpg?imageView2/2/h/440,https://wpimg.wallstcn.com/50530061-851b-4ca5-9dc5-2fead928a939.jpg?imageView2/2/h/440,https://wpimg.wallstcn.com/9679ffb0-9e0b-4451-9916-e21992218054.jpg?imageView2/2/h/440', 'http://120.79.220.218:9000/demo/flybirds_logo.png', b'0', 1);

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '登录/退出状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号操作（0 登录 1 退出 ）',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1475 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (1409, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-18 18:41:40', '0');
INSERT INTO `sys_logininfor` VALUES (1410, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-18 18:41:43', '0');
INSERT INTO `sys_logininfor` VALUES (1411, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '[ 强制挤出 ] 账号在内网IP,操作系统为 Chrome 9 版本:98.0.4758.102,ip地址 120.234.167.130,119.91.25.140 ', '2022-08-18 18:41:47', '1');
INSERT INTO `sys_logininfor` VALUES (1412, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-18 18:47:55', '1');
INSERT INTO `sys_logininfor` VALUES (1413, 'flybirds', '127.0.0.1,112.96.50.124', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-18 19:23:22', '0');
INSERT INTO `sys_logininfor` VALUES (1414, 'flybirds', '112.96.50.124,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-18 19:25:08', '0');
INSERT INTO `sys_logininfor` VALUES (1415, 'flybirds', '127.0.0.1,112.96.50.124', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '[ 强制挤出 ] 账号在内网IP,操作系统为 Chrome 9 版本:98.0.4758.102,ip地址 112.96.50.124,119.91.25.140 ', '2022-08-18 19:25:08', '1');
INSERT INTO `sys_logininfor` VALUES (1416, 'flybirds', '112.96.50.124,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-18 19:27:14', '1');
INSERT INTO `sys_logininfor` VALUES (1417, 'flybirds', '127.0.0.1,112.96.50.124', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-18 19:27:45', '0');
INSERT INTO `sys_logininfor` VALUES (1418, 'flybirds', '127.0.0.1,112.96.50.124', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-18 19:28:09', '1');
INSERT INTO `sys_logininfor` VALUES (1419, 'admin', '127.0.0.1,112.96.50.124', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-18 19:28:19', '0');
INSERT INTO `sys_logininfor` VALUES (1420, 'admin', '127.0.0.1,112.96.50.124', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-18 19:43:59', '1');
INSERT INTO `sys_logininfor` VALUES (1421, 'admin', '127.0.0.1,112.96.50.124', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-18 19:48:46', '0');
INSERT INTO `sys_logininfor` VALUES (1422, 'admin', '127.0.0.1,112.96.50.124', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-18 19:50:24', '1');
INSERT INTO `sys_logininfor` VALUES (1423, 'admin', '127.0.0.1,112.96.50.124', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-18 19:50:32', '0');
INSERT INTO `sys_logininfor` VALUES (1424, 'admin', '112.96.50.124,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-18 20:01:04', '0');
INSERT INTO `sys_logininfor` VALUES (1425, 'admin', '127.0.0.1,112.96.50.124', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '[ 强制挤出 ] 账号在内网IP,操作系统为 Chrome 9 版本:98.0.4758.102,ip地址 112.96.50.124,119.91.25.140 ', '2022-08-18 20:01:05', '1');
INSERT INTO `sys_logininfor` VALUES (1426, 'admin', '112.96.50.124,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-18 20:03:40', '1');
INSERT INTO `sys_logininfor` VALUES (1427, 'flybirds', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-19 09:04:06', '0');
INSERT INTO `sys_logininfor` VALUES (1428, 'flybirds', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-19 09:05:26', '1');
INSERT INTO `sys_logininfor` VALUES (1429, 'flybirds', '127.0.0.1,120.234.167.130', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-19 09:05:42', '0');
INSERT INTO `sys_logininfor` VALUES (1430, 'admin', '127.0.0.1,120.234.167.130', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '1', '无意义的客户端数据（账号或者密码错误）', '2022-08-19 10:06:17', '0');
INSERT INTO `sys_logininfor` VALUES (1431, 'admin', '127.0.0.1,120.234.167.130', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-19 10:06:26', '0');
INSERT INTO `sys_logininfor` VALUES (1432, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-19 12:06:17', '0');
INSERT INTO `sys_logininfor` VALUES (1433, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-19 12:07:45', '1');
INSERT INTO `sys_logininfor` VALUES (1434, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '1', '无意义的客户端数据（账号或者密码错误）', '2022-08-19 12:07:49', '0');
INSERT INTO `sys_logininfor` VALUES (1435, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-19 12:07:59', '0');
INSERT INTO `sys_logininfor` VALUES (1436, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '1', '无意义的客户端数据（账号或者密码错误）', '2022-08-19 12:45:04', '0');
INSERT INTO `sys_logininfor` VALUES (1437, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-19 12:45:07', '0');
INSERT INTO `sys_logininfor` VALUES (1438, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '1', '无意义的客户端数据（账号或者密码错误）', '2022-08-19 15:25:28', '0');
INSERT INTO `sys_logininfor` VALUES (1439, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-19 15:25:31', '0');
INSERT INTO `sys_logininfor` VALUES (1440, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-19 15:42:09', '1');
INSERT INTO `sys_logininfor` VALUES (1441, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '1', '无意义的客户端数据（账号或者密码错误）', '2022-08-19 15:42:13', '0');
INSERT INTO `sys_logininfor` VALUES (1442, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-19 15:42:15', '0');
INSERT INTO `sys_logininfor` VALUES (1443, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-19 15:42:46', '1');
INSERT INTO `sys_logininfor` VALUES (1444, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-19 15:42:54', '0');
INSERT INTO `sys_logininfor` VALUES (1445, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-19 15:43:05', '1');
INSERT INTO `sys_logininfor` VALUES (1446, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-19 15:43:15', '0');
INSERT INTO `sys_logininfor` VALUES (1447, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-19 15:44:10', '1');
INSERT INTO `sys_logininfor` VALUES (1448, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '1', '无意义的客户端数据（账号或者密码错误）', '2022-08-19 15:44:14', '0');
INSERT INTO `sys_logininfor` VALUES (1449, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-19 15:44:16', '0');
INSERT INTO `sys_logininfor` VALUES (1450, 'admin', '120.234.167.130,119.91.25.140', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-19 15:44:49', '1');
INSERT INTO `sys_logininfor` VALUES (1451, 'admin', '127.0.0.1', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-19 16:34:01', '0');
INSERT INTO `sys_logininfor` VALUES (1452, 'admin', '127.0.0.1', '内网IP', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-19 16:34:42', '1');
INSERT INTO `sys_logininfor` VALUES (1453, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-19 17:17:39', '0');
INSERT INTO `sys_logininfor` VALUES (1454, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-19 17:17:49', '0');
INSERT INTO `sys_logininfor` VALUES (1455, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '[ 强制挤出 ] 账号在河北省 沧州市,操作系统为 Chrome 9 版本:98.0.4758.102,ip地址 119.91.25.140 ', '2022-08-19 17:17:50', '1');
INSERT INTO `sys_logininfor` VALUES (1456, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 9 版本:98.0.4758.102', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-19 17:24:33', '1');
INSERT INTO `sys_logininfor` VALUES (1457, 'flybirds', '119.91.25.140', '河北省 沧州市', 'Chrome 8 版本:80.0.3987.87', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-23 22:18:02', '0');
INSERT INTO `sys_logininfor` VALUES (1458, 'flybirds', '119.91.25.140', '河北省 沧州市', 'Chrome 8 版本:80.0.3987.87', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-23 22:28:36', '1');
INSERT INTO `sys_logininfor` VALUES (1459, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 8 版本:80.0.3987.87', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-23 22:31:40', '0');
INSERT INTO `sys_logininfor` VALUES (1460, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 8 版本:80.0.3987.87', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-23 22:42:29', '1');
INSERT INTO `sys_logininfor` VALUES (1461, 'flybirds', '119.91.25.140', '河北省 沧州市', 'Chrome 8 版本:80.0.3987.87', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-23 23:07:35', '0');
INSERT INTO `sys_logininfor` VALUES (1462, 'flybirds', '119.91.25.140', '河北省 沧州市', 'Chrome 8 版本:80.0.3987.87', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-23 23:25:31', '1');
INSERT INTO `sys_logininfor` VALUES (1463, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 10 版本:104.0.0.0', 'WINDOWS 版本:Windows 10', '1', '系统错误，请联系管理员！', '2022-08-26 14:33:23', '0');
INSERT INTO `sys_logininfor` VALUES (1464, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 10 版本:104.0.0.0', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-26 14:38:31', '0');
INSERT INTO `sys_logininfor` VALUES (1465, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 10 版本:104.0.0.0', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-26 14:39:16', '1');
INSERT INTO `sys_logininfor` VALUES (1466, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 10 版本:104.0.0.0', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-08-29 17:53:51', '0');
INSERT INTO `sys_logininfor` VALUES (1467, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 10 版本:104.0.0.0', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-08-29 17:55:21', '1');
INSERT INTO `sys_logininfor` VALUES (1468, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 10 版本:104.0.0.0', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-09-06 16:57:47', '0');
INSERT INTO `sys_logininfor` VALUES (1469, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 10 版本:104.0.0.0', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-09-06 16:58:22', '1');
INSERT INTO `sys_logininfor` VALUES (1470, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 10 版本:104.0.0.0', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-09-09 17:15:39', '0');
INSERT INTO `sys_logininfor` VALUES (1471, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 10 版本:104.0.0.0', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-09-09 17:16:42', '1');
INSERT INTO `sys_logininfor` VALUES (1472, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 10 版本:105.0.0.0', 'WINDOWS 版本:Windows 10', '1', '无意义的客户端数据（账号或者密码错误）', '2022-11-23 15:51:14', '0');
INSERT INTO `sys_logininfor` VALUES (1473, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 10 版本:105.0.0.0', 'WINDOWS 版本:Windows 10', '0', '登录成功', '2022-11-23 15:51:22', '0');
INSERT INTO `sys_logininfor` VALUES (1474, 'admin', '119.91.25.140', '河北省 沧州市', 'Chrome 10 版本:105.0.0.0', 'WINDOWS 版本:Windows 10', '0', '用户注销', '2022-11-23 15:52:35', '1');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_user` bigint(20) NULL DEFAULT NULL,
  `update_user` bigint(20) NULL DEFAULT NULL,
  `del_flag` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2053 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, 1, 0, 'M', '0', '0', '', 'system', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统管理目录', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 2, 'monitor', NULL, 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统监控目录', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 3, 'tool', NULL, 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统工具目录', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '用户管理菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '角色管理菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '菜单管理菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '部门管理菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '岗位管理菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '字典管理菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '参数设置菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 9, 'notice', 'system/notice/index', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知公告菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 10, 'log', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '日志管理菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '在线用户菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (114, '表单构建', 3, 10, 'build', 'tool/build/index', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2018-03-16 11:33:00', 'admin', '2022-01-03 07:04:22', '表单构建菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'system/operlog/index', 1, 0, 'C', '0', '0', 'system:operlog:list', 'form', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '操作日志菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'system/logininfor/index', 1, 0, 'C', '0', '0', 'system:logininfor:list', 'logininfor', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '登录日志菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, 1, '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, 2, '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, 3, '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, 4, '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1005, '用户导出', 100, 5, '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1006, '用户导入', 100, 6, '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, 7, '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, 1, '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, 2, '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, 3, '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, 4, '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1012, '角色导出', 101, 5, '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, 1, '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, 2, '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, 3, '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, 4, '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1017, '部门查询', 103, 1, '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1018, '部门新增', 103, 2, '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1019, '部门修改', 103, 3, '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1020, '部门删除', 103, 4, '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1021, '岗位查询', 104, 1, '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1022, '岗位新增', 104, 2, '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1023, '岗位修改', 104, 3, '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1024, '岗位删除', 104, 4, '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1025, '岗位导出', 104, 5, '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1026, '字典查询', 105, 1, '#', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1027, '字典新增', 105, 2, '#', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1028, '字典修改', 105, 3, '#', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1029, '字典删除', 105, 4, '#', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1030, '字典导出', 105, 5, '#', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1031, '参数查询', 106, 1, '#', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1032, '参数新增', 106, 2, '#', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1033, '参数修改', 106, 3, '#', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1034, '参数删除', 106, 4, '#', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1035, '参数导出', 106, 5, '#', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1041, '公告查询', 107, 1, '#', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1042, '公告新增', 107, 2, '#', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1043, '公告修改', 107, 3, '#', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1044, '公告删除', 107, 4, '#', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1045, '操作查询', 500, 1, '#', '', 1, 0, 'F', '0', '0', 'system:operlog:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1046, '操作删除', 500, 2, '#', '', 1, 0, 'F', '0', '0', 'system:operlog:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1047, '日志导出', 500, 4, '#', '', 1, 0, 'F', '0', '0', 'system:operlog:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1048, '登录查询', 501, 1, '#', '', 1, 0, 'F', '0', '0', 'system:logininfor:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1049, '登录删除', 501, 2, '#', '', 1, 0, 'F', '0', '0', 'system:logininfor:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1050, '日志导出', 501, 3, '#', '', 1, 0, 'F', '0', '0', 'system:logininfor:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1051, '在线查询', 109, 1, '#', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1052, '批量强退', 109, 2, '#', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1053, '单条强退', 109, 3, '#', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1212, '短信服务', 1, 11, 'sms', NULL, 1, 0, 'M', '0', '0', '', 'phone', 'admin', '2021-11-07 11:54:29', 'admin', '2021-11-10 14:01:20', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1213, '短信渠道', 1212, 1, 'sms-channel', 'system/sms/smsChannel', 1, 0, 'C', '0', '0', 'sms:channel:list', '#', 'admin', '2021-11-07 12:00:19', '', NULL, '短信渠道菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1214, '短信渠道信息查询', 1213, 1, '#', '', 1, 0, 'F', '0', '0', 'sms:channel:query', '#', 'admin', '2021-11-07 12:00:19', '', NULL, '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1215, '短信渠道信息新增', 1213, 2, '#', '', 1, 0, 'F', '0', '0', 'sms:channel:add', '#', 'admin', '2021-11-07 12:00:19', '', NULL, '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1216, '短信渠道信息修改', 1213, 3, '#', '', 1, 0, 'F', '0', '0', 'sms:channel:edit', '#', 'admin', '2021-11-07 12:00:19', '', NULL, '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1217, '短信渠道信息删除', 1213, 4, '#', '', 1, 0, 'F', '0', '0', 'sms:channel:remove', '#', 'admin', '2021-11-07 12:00:19', '', NULL, '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1218, '短信渠道信息导出', 1213, 5, '#', '', 1, 0, 'F', '0', '0', 'sms:channel:export', '#', 'admin', '2021-11-07 12:00:19', '', NULL, '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1219, '短信日志', 1212, 2, 'sms-log', 'system/sms/smsLog', 1, 0, 'C', '0', '0', 'sms:log:list', '#', 'admin', '2021-11-07 12:00:43', '', NULL, '短信渠道菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1220, '短信日志信息查询', 1219, 1, '#', '', 1, 0, 'F', '0', '0', 'sms:log:query', '#', 'admin', '2021-11-07 12:00:43', '', NULL, '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1221, '短信日志信息导出', 1219, 5, '#', '', 1, 0, 'F', '0', '0', 'sms:log:export', '#', 'admin', '2021-11-07 12:00:43', '', NULL, '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1227, '短信模板', 1212, 3, 'sms-template', 'system/sms/smsTemplate', 1, 0, 'C', '0', '0', 'sms:template:list', '#', 'admin', '2021-11-07 12:04:17', '', NULL, '短信模板菜单', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1228, '短信模板信息查询', 1227, 1, '#', '', 1, 0, 'F', '0', '0', 'sms:template:query', '#', 'admin', '2021-11-07 12:04:17', '', NULL, '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1229, '短信模板信息新增', 1227, 2, '#', '', 1, 0, 'F', '0', '0', 'sms:template:add', '#', 'admin', '2021-11-07 12:04:17', '', NULL, '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1230, '短信模板信息修改', 1227, 3, '#', '', 1, 0, 'F', '0', '0', 'sms:template:edit', '#', 'admin', '2021-11-07 12:04:17', '', NULL, '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1231, '短信模板信息删除', 1227, 4, '#', '', 1, 0, 'F', '0', '0', 'sms:template:remove', '#', 'admin', '2021-11-07 12:04:17', '', NULL, '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1232, '短信模板信息导出', 1227, 5, '#', '', 1, 0, 'F', '0', '0', 'sms:template:export', '#', 'admin', '2021-11-07 12:04:17', '', NULL, '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1239, 'springBootAdmin', 2, 7, 'admin-server', 'monitor/admin-server/index', 1, 0, 'C', '0', '0', 'monitor:admin-server:list', 'dashboard', 'admin', '2021-11-19 08:02:09', '', NULL, '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1240, '服务监控', 2, 8, 'server', 'monitor/server/index', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2021-11-19 08:03:23', '', NULL, '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (1241, '缓存监控', 2, 9, 'cache', 'monitor/cache/index', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'tab', 'admin', '2021-11-19 08:05:43', 'admin', '2021-11-19 08:06:04', '', NULL, NULL, b'0');
INSERT INTO `sys_menu` VALUES (2011, '应用管理', 0, 0, 'application', '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2021-12-12 04:49:38', 'admin', '2022-01-22 04:39:06', '', NULL, 1, b'0');
INSERT INTO `sys_menu` VALUES (2045, '数据库文档', 3, 10, 'db-doc', 'tool/db-doc/index', 1, 0, 'C', '0', '0', 'tool:db-doc:list', 'documentation', 'admin', '2022-01-19 08:44:21', '', NULL, '', 1, NULL, b'0');
INSERT INTO `sys_menu` VALUES (2046, '应用中心', 2011, 1, 'app-center', 'application/app-center/index', 1, 0, 'C', '0', '0', 'application:app-center:list', 'client', 'admin', '2022-01-22 04:40:34', 'admin', '2022-01-22 04:55:58', '', 1, 1, b'0');
INSERT INTO `sys_menu` VALUES (2048, '动态圈', 1, 8, 'dynamic', 'system/dynamic/index', 1, 0, 'C', '0', '0', 'system:dynamic:list', 'peoples', 'admin', '2022-01-27 11:07:45', '', NULL, '', 1, NULL, b'0');
INSERT INTO `sys_menu` VALUES (2049, '动态查询', 2048, 1, '', NULL, 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2022-01-27 11:08:22', '', NULL, '', 1, NULL, b'0');
INSERT INTO `sys_menu` VALUES (2050, '动态新增', 2048, 2, '', NULL, 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2022-01-27 11:08:41', '', NULL, '', 1, NULL, b'0');
INSERT INTO `sys_menu` VALUES (2051, '动态修改', 2048, 3, '', NULL, 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2022-01-27 11:09:00', '', NULL, '', 1, NULL, b'0');
INSERT INTO `sys_menu` VALUES (2052, '动态删除', 2048, 4, '', NULL, 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2022-01-27 11:09:20', '', NULL, '', 1, NULL, b'0');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(20) NULL DEFAULT NULL,
  `update_user` bigint(20) NULL DEFAULT NULL,
  `del_flag` bit(1) NOT NULL DEFAULT b'0',
  `tenant_id` bigint(20) NULL DEFAULT 1 COMMENT '租户ID',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '温馨提醒：2018-07-01 若依新版本发布啦', '2', 0xE696B0E78988E69CACE58685E5AEB9, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '管理员', NULL, NULL, b'0', 1);
INSERT INTO `sys_notice` VALUES (2, '维护通知：2018-07-01 若依系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '管理员', NULL, NULL, b'0', 1);

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int(1) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1032 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (1028, '菜单管理', 3, 'com.flybirds.system.sysMenu.controller.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/menu/111', '127.0.0.1,112.96.50.124', '', NULL, '{\"msg\":\"菜单已分配,不允许删除\",\"code\":400}', 0, NULL, '2022-08-18 19:28:42');
INSERT INTO `sys_oper_log` VALUES (1029, '菜单管理', 3, 'com.flybirds.system.sysMenu.controller.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/menu/2047', '127.0.0.1,112.96.50.124', '', NULL, '{\"msg\":\"菜单已分配,不允许删除\",\"code\":400}', 0, NULL, '2022-08-18 19:34:49');
INSERT INTO `sys_oper_log` VALUES (1030, '字典类型', 3, 'com.flybirds.system.sysDicData.controller.SysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/dict/data/163', '120.234.167.130,119.91.25.140', '', NULL, 'null', 1, 'Transaction synchronization is not active', '2022-08-19 13:01:14');
INSERT INTO `sys_oper_log` VALUES (1031, '字典类型', 3, 'com.flybirds.system.sysDicData.controller.SysDictDataController.remove()', 'DELETE', 1, 'admin', NULL, '/dict/data/139', '120.234.167.130,119.91.25.140', '', NULL, 'null', 1, 'Transaction synchronization is not active', '2022-08-19 13:03:33');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建用户id',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改用户id',
  `tenant_id` bigint(20) NULL DEFAULT 1 COMMENT '租户ID',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, 1);
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, 1);
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, 1);
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '', NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
  `tenant_id` bigint(20) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', b'0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '超级管理员', NULL, NULL, 1);
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '2', 1, 1, '0', b'0', 'admin', '2018-03-16 11:33:00', 'admin', '2022-08-04 09:35:07', '普通角色', NULL, 1, 1);
INSERT INTO `sys_role` VALUES (3, '测试', '222', 3, '1', 1, 1, '0', b'0', 'admin', '2022-01-18 10:04:11', 'admin', '2022-01-18 10:04:17', '11', 1, 1, 1);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  `tenant_id` bigint(20) NULL DEFAULT 1 COMMENT '租户ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (2, 100, 1);
INSERT INTO `sys_role_dept` VALUES (2, 101, 1);
INSERT INTO `sys_role_dept` VALUES (2, 105, 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `tenant_id` bigint(20) NOT NULL DEFAULT 1 COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 178 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (131, 2, 1, 1);
INSERT INTO `sys_role_menu` VALUES (132, 2, 100, 1);
INSERT INTO `sys_role_menu` VALUES (133, 2, 101, 1);
INSERT INTO `sys_role_menu` VALUES (134, 2, 102, 1);
INSERT INTO `sys_role_menu` VALUES (135, 2, 103, 1);
INSERT INTO `sys_role_menu` VALUES (136, 2, 104, 1);
INSERT INTO `sys_role_menu` VALUES (137, 2, 105, 1);
INSERT INTO `sys_role_menu` VALUES (138, 2, 106, 1);
INSERT INTO `sys_role_menu` VALUES (139, 2, 1212, 1);
INSERT INTO `sys_role_menu` VALUES (140, 2, 1213, 1);
INSERT INTO `sys_role_menu` VALUES (141, 2, 1219, 1);
INSERT INTO `sys_role_menu` VALUES (142, 2, 1227, 1);
INSERT INTO `sys_role_menu` VALUES (143, 2, 2, 1);
INSERT INTO `sys_role_menu` VALUES (144, 2, 109, 1);
INSERT INTO `sys_role_menu` VALUES (145, 2, 2011, 1);
INSERT INTO `sys_role_menu` VALUES (146, 2, 2047, 1);
INSERT INTO `sys_role_menu` VALUES (147, 2, 2046, 1);
INSERT INTO `sys_role_menu` VALUES (148, 2, 1001, 1);
INSERT INTO `sys_role_menu` VALUES (149, 2, 1008, 1);
INSERT INTO `sys_role_menu` VALUES (150, 2, 1013, 1);
INSERT INTO `sys_role_menu` VALUES (151, 2, 1017, 1);
INSERT INTO `sys_role_menu` VALUES (152, 2, 1021, 1);
INSERT INTO `sys_role_menu` VALUES (153, 2, 1026, 1);
INSERT INTO `sys_role_menu` VALUES (154, 2, 1031, 1);
INSERT INTO `sys_role_menu` VALUES (155, 2, 1032, 1);
INSERT INTO `sys_role_menu` VALUES (156, 2, 1033, 1);
INSERT INTO `sys_role_menu` VALUES (157, 2, 2048, 1);
INSERT INTO `sys_role_menu` VALUES (158, 2, 2049, 1);
INSERT INTO `sys_role_menu` VALUES (159, 2, 2050, 1);
INSERT INTO `sys_role_menu` VALUES (160, 2, 2051, 1);
INSERT INTO `sys_role_menu` VALUES (161, 2, 2052, 1);
INSERT INTO `sys_role_menu` VALUES (162, 2, 1214, 1);
INSERT INTO `sys_role_menu` VALUES (163, 2, 1215, 1);
INSERT INTO `sys_role_menu` VALUES (164, 2, 1220, 1);
INSERT INTO `sys_role_menu` VALUES (165, 2, 1228, 1);
INSERT INTO `sys_role_menu` VALUES (166, 2, 1229, 1);
INSERT INTO `sys_role_menu` VALUES (167, 2, 1051, 1);
INSERT INTO `sys_role_menu` VALUES (168, 2, 111, 1);
INSERT INTO `sys_role_menu` VALUES (169, 2, 112, 1);
INSERT INTO `sys_role_menu` VALUES (170, 2, 113, 1);
INSERT INTO `sys_role_menu` VALUES (171, 2, 1239, 1);
INSERT INTO `sys_role_menu` VALUES (172, 2, 1240, 1);
INSERT INTO `sys_role_menu` VALUES (173, 2, 1241, 1);
INSERT INTO `sys_role_menu` VALUES (174, 2, 3, 1);
INSERT INTO `sys_role_menu` VALUES (175, 2, 116, 1);
INSERT INTO `sys_role_menu` VALUES (176, 2, 114, 1);
INSERT INTO `sys_role_menu` VALUES (177, 2, 2045, 1);

-- ----------------------------
-- Table structure for sys_social_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_social_user`;
CREATE TABLE `sys_social_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
  `user_id` bigint(20) NOT NULL COMMENT '关联的用户编号',
  `user_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '用户类型',
  `type` tinyint(4) NOT NULL COMMENT '社交平台的类型',
  `openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社交 openid',
  `token` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '社交 token',
  `union_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社交的全局编号',
  `raw_token_info` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始 Token 数据，一般是 JSON 格式',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `raw_user_info` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始用户数据，一般是 JSON 格式',
  `create_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint(20) NULL DEFAULT 0 COMMENT '租户编号',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建用户id',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社交用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_social_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '租户编号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户名',
  `contact_user_id` bigint(20) NULL DEFAULT NULL COMMENT '联系人的用户编号',
  `contact_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系人',
  `contact_mobile` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系手机',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '租户状态（0正常 1停用）',
  `domain` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '绑定域名',
  `package_id` bigint(20) NOT NULL COMMENT '租户套餐编号',
  `expire_time` datetime(0) NOT NULL COMMENT '过期时间',
  `account_count` int(11) NOT NULL COMMENT '账号数量',
  `create_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '租户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES (1, 'FBIRD', NULL, '会飞的小鸟', '17321315478', '0', 'https://www.iocoder.cn', 0, '2099-02-19 17:14:16', 9999, '1', '2021-01-05 17:03:47', '1', '2022-05-10 02:28:14', b'0', NULL, NULL);

-- ----------------------------
-- Table structure for sys_tenant_package
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_package`;
CREATE TABLE `sys_tenant_package`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '套餐编号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '套餐名',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '租户状态（0正常 1停用）',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '备注',
  `menu_ids` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联的菜单编号',
  `create_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '租户套餐表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tenant_package
-- ----------------------------
INSERT INTO `sys_tenant_package` VALUES (111, '普通套餐', '0', '小功能', '[1024,1025,1,102,103,104,1013,1014,1015,1016,1017,1018,1019,1020,1021,1022,1023]', '1', '2022-02-22 00:54:00', '1', '2022-03-19 18:39:13', b'0', NULL, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` int(2) NULL DEFAULT 1 COMMENT '用户类型',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `pass_word` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标志（0 正产 1删除）',
  `login_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(20) NULL DEFAULT NULL COMMENT '租户id',
  `locks` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否锁定用户（0正常 1锁定）',
  `online_sync` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否在线同步（0允许，1表示不允许）',
  `online_number` int(1) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '在线用户',
  `reg_channel` int(1) NULL DEFAULT NULL COMMENT '注册渠道',
  `last_login_channel` int(1) NULL DEFAULT NULL COMMENT '最后登录渠道',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建者id',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改者id',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '会飞的小鸟', 0, '14355010825@qq.com', '17683991205', '1', 'http://120.79.220.218:9000/demo/flybirds_logo.png', '$2a$10$fgqBvEIJlE37PM26fyw.Xufy8zQnfY806baPMD9RkRuJ/DP0rISs2', '0', b'0', '119.91.25.140', '2022-11-23 15:52:35', 'admin', '2018-03-16 11:33:00', 'ry', '2021-08-03 07:21:15', '管理员', 1, '0', '0', 1, NULL, 2, NULL, NULL);
INSERT INTO `sys_user` VALUES (2, 105, 'flybirds', '会飞的小鸟', 0, 'flybirds01@qq.com', '15666666666', '1', 'http://120.79.220.218:9000/demo/flybirds_logo.png', '$2a$10$fgqBvEIJlE37PM26fyw.Xufy8zQnfY806baPMD9RkRuJ/DP0rISs2', '0', b'0', '119.91.25.140', '2022-08-23 23:25:31', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '测试员', 1, '0', '0', 1, 0, 2, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  `tenant_id` bigint(20) NULL DEFAULT 1 COMMENT '租户ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1, 1);
INSERT INTO `sys_user_post` VALUES (2, 2, 1);
INSERT INTO `sys_user_post` VALUES (5, 4, 1);
INSERT INTO `sys_user_post` VALUES (6, 4, 1);
INSERT INTO `sys_user_post` VALUES (8, 4, 1);
INSERT INTO `sys_user_post` VALUES (10, 4, 1);
INSERT INTO `sys_user_post` VALUES (11, 4, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `tenant_id` bigint(20) NULL DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2, 1);
INSERT INTO `sys_user_role` VALUES (5, 2, 1);
INSERT INTO `sys_user_role` VALUES (6, 2, 1);
INSERT INTO `sys_user_role` VALUES (8, 2, 1);
INSERT INTO `sys_user_role` VALUES (10, 2, NULL);
INSERT INTO `sys_user_role` VALUES (11, 2, NULL);

SET FOREIGN_KEY_CHECKS = 1;
