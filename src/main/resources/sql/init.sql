/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : district

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 15/01/2022 22:39:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for district_area_change_info
-- ----------------------------
DROP TABLE IF EXISTS `district_area_change_info`;
CREATE TABLE `district_area_change_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代码类型（PROVINCE、CITY、AREA）',
  `code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省/城市/区域代码',
  `address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省/城市/区域名称',
  `newest_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新代码',
  `newest_address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新名称',
  `parent_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父节点代码',
  `version` int(11) NULL DEFAULT 0 COMMENT '版本',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '标签',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除（1：是，0：否）',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_code`(`code`) USING BTREE,
  INDEX `idx_parent_code`(`parent_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3277 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '行政区划变更表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for district_area_detail
-- ----------------------------
DROP TABLE IF EXISTS `district_area_detail`;
CREATE TABLE `district_area_detail`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `province_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省代码',
  `province_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省名称',
  `city_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市代码',
  `city_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市名称',
  `area_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '县区代码',
  `area_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '县区名称',
  `version` int(11) NULL DEFAULT 0 COMMENT '版本',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `is_municipality` tinyint(1) NULL DEFAULT 0 COMMENT '是否属于直辖市（1：是，0：否）',
  `is_provincial_counties` tinyint(1) NULL DEFAULT 0 COMMENT '是否属于省直辖县（1：是，0：否）',
  `is_autonomous_region` tinyint(1) NULL DEFAULT 0 COMMENT '是否自治区（1：是，0：否）',
  `is_special_administrative_region` tinyint(1) NULL DEFAULT 0 COMMENT '是否属于特别行政区（1：是，0：否）',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除（1：是，0：否）',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_area_code`(`area_code`) USING BTREE,
  INDEX `idx_city_code`(`city_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 95134 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '行政区划详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for district_area_info
-- ----------------------------
DROP TABLE IF EXISTS `district_area_info`;
CREATE TABLE `district_area_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代码类型（PROVINCE、CITY、AREA）',
  `code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省/城市/区域代码',
  `address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省/城市/区域名称',
  `parent_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父节点代码',
  `version` int(11) NULL DEFAULT 0 COMMENT '版本',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '标签',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除（1：是，0：否）',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_code`(`code`) USING BTREE,
  INDEX `idx_parent_code`(`parent_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 98129 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '行政区划码表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
