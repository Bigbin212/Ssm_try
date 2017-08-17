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


DROP TABLE IF EXISTS `b_qj_xtpz`;
CREATE TABLE `b_qj_xtpz` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '变量名',
  `name_cn` varchar(32) DEFAULT NULL COMMENT '变量中文名',
  `val` varchar(128) DEFAULT NULL COMMENT '变量对应的配置值',
  `expand` varchar(128) DEFAULT NULL COMMENT '代码项扩展',
  `description` varchar(256) DEFAULT NULL COMMENT '代码项描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
