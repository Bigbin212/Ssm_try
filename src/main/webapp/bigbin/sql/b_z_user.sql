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
  `phone` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`xlh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of b_z_user
-- ----------------------------
INSERT INTO `b_z_user` VALUES ('c9cc9174d9894fee92223266df36f649', 'admin', 'admin', '172.16.192.171', 'xxxxx@qq.com', '2016-04-29 11:09:46', '13863336458');
