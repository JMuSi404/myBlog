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

 Date: 04/07/2022 15:28:16
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
  `commentCount` int(30) NULL DEFAULT NULL COMMENT '评论数量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_blog_t_user`(`user_id`) USING BTREE,
  CONSTRAINT `t_blog_t_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_blog
-- ----------------------------
INSERT INTO `t_blog` VALUES (1532221761548345346, '关于我', '## &#x1F601;关于我\r\n- - -\r\n我是Joe,一名大三学生，一个菜狗，一直做着自己喜欢的事情,\r\n\\\r\n接下来的学生时光,背背java八股文,刷刷题,准备面试吧!\r\n## &#x1F31F; 技能\r\n- - -\r\n- 语言：Java、C\r\n- 数据库：MySQL\r\n- 开发框架：Springboot、Mybatis(plus)、Vue\r\n- 工具：Maven、git\r\n- IDE：IDEA、Hbuilder\r\n', '关于我', '原创', 3, b'1', b'0', b'0', '2022-06-02 12:45:09', '2022-07-04 15:19:04', 2022576839, '关于我', NULL, NULL);
INSERT INTO `t_blog` VALUES (1543588540833624066, '友链', '## &#x1f31f;友链列表\r\n暂无\r\n## &#x1F44D;添加友链\r\n需要交换友链的可在下方留言\r\n\\\r\n(｡･∀･)ﾉﾞ嗨\r\n```\r\n出于信息需要,大佬你的信息格式要包含：名称、网站链接、头像链接\r\n```\r\n格式可以如下:\r\n```\r\n名称：Joe\r\n网站链接：https://www.hsjhome.top\r\n头像链接：https://www.hsjhome.top/img/blogAvatar.jpg\r\n```', '友链', '原创', 5, b'1', b'0', b'0', '2022-07-03 21:32:41', '2022-07-03 22:22:07', 2022576839, '友链', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
