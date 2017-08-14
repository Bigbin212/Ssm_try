/*
*Date: 2017-03-22 09:49:27
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
  `photo` varchar(1024) DEFAULT NULL,
  `yhqx` varchar(8) DEFAULT '1' COMMENT '权限',
  PRIMARY KEY (`xlh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of b_z_user
-- ----------------------------
--管理员
INSERT INTO `b_z_user` VALUES ('c9cc9174d9894fee92223266df36f649', 'admin', '930109', '127.0.0.1', 'admin@qq.com', null, null, null, '0');
--普通用户
INSERT INTO `b_z_user` VALUES ('d931b50f1db44ee197e175c9d217c37b', 'godness', '888888', '127.0.0.1', 'godness@qq.com', null, null, NULL, '1');
commit;