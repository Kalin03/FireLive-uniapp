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

 Date: 20/07/2024 12:24:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
-- Records of food_table
-- ----------------------------
INSERT INTO `food_table` VALUES (1, '米饭', 25.9, 2.0, 0.3, 70.9, 120.0, 2.6, 1, '一碗米饭通常在100g~200g');
INSERT INTO `food_table` VALUES (2, '鸡蛋', 2.8, 0.8, 8.8, 74.1, 485.0, 13.3, 1, '一个鸡蛋通常在50~80g');
INSERT INTO `food_table` VALUES (3, '面条', 61.9, 1.7, 0.7, 28.5, 411.0, 8.3, 1, '一两面条通常在50g');
INSERT INTO `food_table` VALUES (4, '面包', 58.6, 1.8, 5.1, 27.4, 509.0, 8.3, 1, '一个面包通常在60~120g');
INSERT INTO `food_table` VALUES (5, '猪肉', 2.4, 3.9, 37.0, 46.8, 452.0, 13.2, 1, '一片猪肉通常在7~10g');
INSERT INTO `food_table` VALUES (6, '鸡肉', 1.3, 5.7, 9.4, 69.0, 500.0, 19.3, 1, '一个鸡腿通常在170~250g');
INSERT INTO `food_table` VALUES (7, '牛肉', 2.0, 5.8, 4.2, 72.8, 520.0, 19.9, 1, '一片牛肉通常在10~15g');
INSERT INTO `food_table` VALUES (8, '大白菜', 3.2, 31.7, 0.1, 94.6, 70.0, 1.5, 1, '一片白菜通常在20~60g');
INSERT INTO `food_table` VALUES (9, '油麦菜', 2.1, 20.2, 0.4, 95.7, 313.0, 1.4, 1, '一片油麦菜通常在20~60g');
INSERT INTO `food_table` VALUES (10, '玉米', 22.8, 18.1, 1.2, 71.3, 390.0, 4.0, 1, '一颗玉米可食用部分在150~200g');
INSERT INTO `food_table` VALUES (11, '苹果', 13.5, 4.3, 0.2, 85.9, 142.0, 0.2, 1, '一个苹果通常在150~500g');
INSERT INTO `food_table` VALUES (12, '香蕉', 22.0, 8.8, 0.2, 75.8, 336.0, 1.4, 1, '一个香蕉可食用部分在90~150g');
INSERT INTO `food_table` VALUES (13, '柑桔', 11.9, 28.5, 0.2, 86.9, 220.0, 0.7, 1, '一个柑桔通常在50g');
INSERT INTO `food_table` VALUES (14, '纯净水', 0.0, 0.0, 0.0, 100.0, 0.0, 0.0, 1, '建议每天喝2L水');
INSERT INTO `food_table` VALUES (15, '可乐', 10.7, 0.0, 0.0, 89.1, 22.0, 0.1, 1, '建议少喝可乐');
INSERT INTO `food_table` VALUES (16, '牛奶', 3.4, 1.3, 3.2, 89.8, 335.0, 3.0, 1, '建议早餐来杯牛奶');

SET FOREIGN_KEY_CHECKS = 1;
