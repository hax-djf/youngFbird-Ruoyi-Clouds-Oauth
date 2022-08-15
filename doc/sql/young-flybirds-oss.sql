/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.144.129
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 192.168.144.129:3306
 Source Schema         : young-flybirds-oss

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 05/08/2022 16:37:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_file_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_info`;
CREATE TABLE `sys_file_info`  (
  `file_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '文件编号',
  `file_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文件名称',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文件路径',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file_info
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
