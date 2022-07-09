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

 Date: 09/07/2022 08:58:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_blog
-- ----------------------------
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog`  (
  `id` bigint(30) NOT NULL COMMENT 'id',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `first_Picture` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '首图',
  `flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签/原创/转载/引用',
  `views` int(30) NULL DEFAULT NULL COMMENT '浏览量',
  `commentabled` bit(1) NULL DEFAULT NULL COMMENT '是否可评论',
  `published` bit(1) NULL DEFAULT NULL COMMENT '是否发表',
  `recommendned` bit(1) NULL DEFAULT NULL COMMENT '是否推荐',
  `createTime` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `editTime` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改时间',
  `user_id` bigint(30) NULL DEFAULT NULL COMMENT 'userid',
  `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '博客描述',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'userName',
  `comment_count` int(30) NULL DEFAULT NULL COMMENT '评论数量',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型:0普通博客1友联2关于我',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_blog_t_user`(`user_id`) USING BTREE,
  CONSTRAINT `t_blog_t_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
