/*-----------------------修改数据的安全模式--------------------------*/

SET SQL_SAFE_UPDATES = 0;


--管理员
INSERT INTO `b_z_user` VALUES ('c9cc9174d9894fee92223266df36f649', 'admin', '930109', '127.0.0.1', 'admin@qq.com', null, null, null, '0');
--普通用户
INSERT INTO `b_z_user` VALUES ('d931b50f1db44ee197e175c9d217c37b', 'godness', '888888', '127.0.0.1', 'godness@qq.com', null, null, NULL, '1');
commit;