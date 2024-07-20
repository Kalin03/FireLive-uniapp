/*
 Navicat Premium Data Transfer

 Source Server         : 47.121.189.72_3306
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 47.121.189.72:3306
 Source Schema         : health_uniapp

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 20/07/2024 12:23:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article_table
-- ----------------------------
DROP TABLE IF EXISTS `article_table`;
CREATE TABLE `article_table`  (
  `article_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `article_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章图片',
  `article_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章标题',
  `article_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章内容',
  `likes_number` int(11) NULL DEFAULT 0 COMMENT '点赞数量',
  `comments_number` int(11) NULL DEFAULT 0 COMMENT '评论数',
  `collect_number` int(11) NULL DEFAULT 0 COMMENT '收藏数量',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`article_id`, `openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 155 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bmi_table
-- ----------------------------
DROP TABLE IF EXISTS `bmi_table`;
CREATE TABLE `bmi_table`  (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录号',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `BMI` double(5, 2) NOT NULL COMMENT 'BMI值',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`record_id`, `openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 162 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for check_in_table
-- ----------------------------
DROP TABLE IF EXISTS `check_in_table`;
CREATE TABLE `check_in_table`  (
  `check_in_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录号',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `early_rising` int(11) NULL DEFAULT 0 COMMENT '早起 0未打卡 1打卡',
  `running` int(11) NULL DEFAULT 0 COMMENT '跑步 0未打卡 1打卡',
  `fitness` int(11) NULL DEFAULT 0 COMMENT '健身 0未打卡 1打卡',
  `early_bedtime` int(11) NULL DEFAULT 0 COMMENT '早睡 0未打卡 1打卡',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`check_in_id`, `openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment_table
-- ----------------------------
DROP TABLE IF EXISTS `comment_table`;
CREATE TABLE `comment_table`  (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录号',
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `to_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回复的id',
  `root_id` int(11) NULL DEFAULT NULL COMMENT '本条评论的根评论',
  `comment_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `type` int(11) NOT NULL COMMENT '评论的类型 0根评论 1子评论',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`record_id`, `article_id`, `openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 171 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for consume_table
-- ----------------------------
DROP TABLE IF EXISTS `consume_table`;
CREATE TABLE `consume_table`  (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录号',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `sport_id` int(11) NOT NULL COMMENT '运动id',
  `sport_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '运动名称',
  `sport_number` double(6, 1) NOT NULL COMMENT '运动',
  `sport_unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '运动单位',
  `sport_consumption` double NOT NULL COMMENT '记录卡路里消耗记录值',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`record_id`, `openid`, `sport_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for feedback_table
-- ----------------------------
DROP TABLE IF EXISTS `feedback_table`;
CREATE TABLE `feedback_table`  (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录号',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `feedback_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '反馈内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`record_id`, `openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for food_priority
-- ----------------------------
DROP TABLE IF EXISTS `food_priority`;
CREATE TABLE `food_priority`  (
  `food_id` int(11) NOT NULL COMMENT '记录号',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `priority` int(11) NOT NULL DEFAULT 1 COMMENT '优先级',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`food_id`, `openid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for food_table
-- ----------------------------
DROP TABLE IF EXISTS `food_table`;
CREATE TABLE `food_table`  (
  `food_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '食物id',
  `food_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '食物名称',
  `carbohydrates` double(6, 1) NOT NULL COMMENT '碳水数值',
  `vitamins` double(6, 1) NOT NULL COMMENT '维生素数值',
  `fats` double(6, 1) NOT NULL COMMENT '脂肪数值',
  `water` double(6, 1) NOT NULL COMMENT '水分数值',
  `minerals` double(6, 1) NOT NULL COMMENT '无机盐',
  `proteins` double(6, 1) NOT NULL COMMENT '蛋白质数值',
  `food_priority` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '食物推荐优先级',
  `food_tip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '温馨提示',
  PRIMARY KEY (`food_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for intake_table
-- ----------------------------
DROP TABLE IF EXISTS `intake_table`;
CREATE TABLE `intake_table`  (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录号',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `food_id` int(11) NOT NULL COMMENT '食物id',
  `food_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '食物名称',
  `food_weight` double(6, 1) NOT NULL COMMENT '食物重量',
  `carbohydrates` double(6, 1) NOT NULL COMMENT '记录摄入碳水',
  `vitamins` double(6, 1) NOT NULL COMMENT '记录摄入维生素',
  `fats` double(6, 1) NOT NULL COMMENT '记录摄入脂肪',
  `water` double(6, 1) NOT NULL COMMENT '记录摄入水分',
  `minerals` double(6, 1) NOT NULL COMMENT '记录摄入无机盐',
  `proteins` double(6, 1) NOT NULL COMMENT '记录摄入蛋白质',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`record_id`, `openid`, `food_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saves_table
-- ----------------------------
DROP TABLE IF EXISTS `saves_table`;
CREATE TABLE `saves_table`  (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录号',
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收藏人id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 154 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sport_priority
-- ----------------------------
DROP TABLE IF EXISTS `sport_priority`;
CREATE TABLE `sport_priority`  (
  `sport_id` int(11) NOT NULL COMMENT '记录号',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `priority` int(11) NOT NULL DEFAULT 1 COMMENT '优先级',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`sport_id`, `openid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sport_table
-- ----------------------------
DROP TABLE IF EXISTS `sport_table`;
CREATE TABLE `sport_table`  (
  `sport_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '运动项目id',
  `sport_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '运动项名称',
  `sport_unit` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '运动项的单位 分钟 个',
  `f_met` int(11) NOT NULL COMMENT '高强度运动',
  `m_met` int(11) NOT NULL COMMENT '中强度运动',
  `s_met` int(11) NOT NULL COMMENT '低强度运动',
  `sport_percent` double(5, 2) NOT NULL COMMENT '运动距离',
  `sport_priority` int(11) NOT NULL COMMENT '运动项推荐优先级',
  `sport_tip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '温馨提示',
  PRIMARY KEY (`sport_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for target_value
-- ----------------------------
DROP TABLE IF EXISTS `target_value`;
CREATE TABLE `target_value`  (
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `carbohydrates` double(6, 1) NOT NULL COMMENT '碳水目标数值',
  `vitamins` double(6, 1) NOT NULL COMMENT '维生素目标数值',
  `fats` double(6, 1) NOT NULL COMMENT '脂肪目标数值',
  `water` double(6, 1) NOT NULL COMMENT '水分目标数值',
  `minerals` double(6, 1) NOT NULL COMMENT '无机盐目标数值',
  `proteins` double(6, 1) NOT NULL COMMENT '蛋白质目标数值',
  `sport_consumption` double(6, 1) NOT NULL COMMENT '运动消耗卡路里目标数值',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`openid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_data
-- ----------------------------
DROP TABLE IF EXISTS `user_data`;
CREATE TABLE `user_data`  (
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户头像url',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `height` double(6, 2) NULL DEFAULT NULL COMMENT '身高',
  `weight` double(6, 2) NULL DEFAULT NULL COMMENT '体重',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `signature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `health_analyse` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '健康分析',
  `target` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '健身、减肥、增重、保持',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`openid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_table
-- ----------------------------
DROP TABLE IF EXISTS `user_table`;
CREATE TABLE `user_table`  (
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '微信账号id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`openid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
