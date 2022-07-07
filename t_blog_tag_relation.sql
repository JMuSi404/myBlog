/*
 Navicat Premium Data Transfer

 Source Server         : SSM
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 04/07/2022 15:28:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_blog_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_tag_relation`;
CREATE TABLE `t_blog_tag_relation`  (
  `id` bigint(30) NOT NULL COMMENT 'id',
  `blog_id` bigint(30) NULL DEFAULT NULL COMMENT '博客id',
  `tag_id` bigint(30) NULL DEFAULT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_blog_id`(`blog_id`) USING BTREE,
  INDEX `t_tag_id`(`tag_id`) USING BTREE,
  CONSTRAINT `t_blog_id` FOREIGN KEY (`blog_id`) REFERENCES `t_blog` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `t_tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_blog_tag_relation
-- ----------------------------
INSERT INTO `t_blog_tag_relation` VALUES (1543856904046948354, 1532221761548345346, 1533670682262970370);

SET FOREIGN_KEY_CHECKS = 1;
