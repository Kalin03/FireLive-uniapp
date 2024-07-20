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

 Date: 20/07/2024 12:24:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
-- Records of sport_table
-- ----------------------------
INSERT INTO `sport_table` VALUES (1, '跳绳', '分钟', 12, 10, 8, 0.00, 1, '低强度（低于100次/分）中等强度（100~120次/分）高强度（高于120次/分）');
INSERT INTO `sport_table` VALUES (2, '游泳', '分钟', 9, 6, 3, 0.00, 1, '慢速（轻松自由式或仰泳）中等强度（快速自由式或蛙泳）快速（轻松自由式或仰泳）');
INSERT INTO `sport_table` VALUES (3, '骑行', '分钟', 12, 8, 4, 0.00, 1, '低强度（低于16 km/h）中等强度（16~19 km/h）高强度（高于19 km/h）');
INSERT INTO `sport_table` VALUES (4, '卧推', '个', 0, 0, 0, 0.70, 1, '保持正确姿势、控制动作速度、保持完整幅度、顺畅呼吸、适量训练和定期休息。');
INSERT INTO `sport_table` VALUES (5, '足球', '分钟', 12, 9, 6, 0.00, 1, '保持正确姿势、控制动作速度、保持完整幅度、顺畅呼吸、适量训练和定期休息。');
INSERT INTO `sport_table` VALUES (6, '篮球', '分钟', 10, 6, 4, 0.00, 1, '低强度（慢跑、基本的传球和简单的技术训练）中等强度（一般的训练和比赛，如传球、小组对抗训练）高强度（激烈的比赛、高强度的训练，快速跑动、冲刺和身体接触）');
INSERT INTO `sport_table` VALUES (7, '羽毛球', '分钟', 8, 5, 3, 0.00, 1, '低强度（慢跑、轻松的击球练习和简单的移动）中等强度（一般的训练和比赛，如传球、接发球、移动和击球）高强度（激烈的比赛、高强度的训练，快速移动、迅速反应和强烈的击球动作）');
INSERT INTO `sport_table` VALUES (8, '引体向上', '个', 0, 0, 0, 0.70, 1, '保持正确姿势、控制动作速度、保持完整幅度、顺畅呼吸、适量训练和定期休息。');
INSERT INTO `sport_table` VALUES (9, '步行', '分钟', 4, 3, 2, 0.00, 1, '低强度（低于4km/h）中等强度（4~6km/h）高强度（高于6km/h）');
INSERT INTO `sport_table` VALUES (10, '跑步', '分钟', 11, 8, 5, 0.00, 1, '低强度（低于8km/h）中等强度（8~10km/h）高强度（高于10km/h）');
INSERT INTO `sport_table` VALUES (11, '深蹲', '个', 0, 0, 0, 1.10, 1, '保持正确姿势、控制动作速度、保持完整幅度、顺畅呼吸、适量训练和定期休息。');

SET FOREIGN_KEY_CHECKS = 1;
