/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : bigbin

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2017-03-18 12:41:44
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `b_z_user`
-- ----------------------------
DROP TABLE IF EXISTS `b_z_user`;
CREATE TABLE `b_z_user` (
  `xlh` varchar(64) NOT NULL COMMENT '序列号UUID',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `ip` varchar(128) DEFAULT NULL COMMENT 'ip地址',
  `email` varchar(64) NOT NULL COMMENT '邮箱',
  `zcsj` datetime DEFAULT NULL COMMENT '注册时间',
  `phone` varchar(16) DEFAULT NULL COMMENT '电话号码',
  `yhqx` varchar(8) DEFAULT '1' COMMENT '权限',
  PRIMARY KEY (`xlh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
