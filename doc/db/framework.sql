/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : framework

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2014-07-25 13:34:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_netdisk`
-- ----------------------------
DROP TABLE IF EXISTS `sys_netdisk`;
CREATE TABLE `sys_netdisk` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FILE_URL` varchar(200) DEFAULT NULL COMMENT '文件网址',
  `FILE_NAME` varchar(200) DEFAULT NULL COMMENT '文件名',
  `FILE_DESC` varchar(200) DEFAULT NULL COMMENT '文件说明',
  `FILE_SIZE` varchar(20) DEFAULT NULL COMMENT '文件大小',
  `FILE_HIT` int(11) DEFAULT NULL COMMENT '下载次数',
  `FILE_OWNER` int(11) DEFAULT NULL COMMENT '上传者ID',
  `UPLOAD_TIME` datetime DEFAULT NULL COMMENT '上传时间',
  `ENABLE` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网盘';

-- ----------------------------
-- Records of sys_netdisk
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(50) DEFAULT NULL COMMENT '用户名',
  `PASSWORD` varchar(50) DEFAULT NULL COMMENT '密码',
  `NICK_NAME` varchar(50) DEFAULT NULL COMMENT '昵称',
  `LAST_LOGIN_IP` varchar(50) DEFAULT NULL COMMENT '上次登录IP',
  `LAST_LOGIN_TIME` varchar(50) DEFAULT NULL COMMENT '上次登录时间',
  `ENABLE` bit(1) DEFAULT b'1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '202CB962AC59075B964B07152D234B70', 'gary', '127.0.0.1', '2014-07-25 13:25:59', '');
INSERT INTO `sys_user` VALUES ('2', 'user1', '202CB962AC59075B964B07152D234B70', 'user1', null, null, '');
INSERT INTO `sys_user` VALUES ('3', 'user2', '202CB962AC59075B964B07152D234B70', 'user2', null, null, '');
INSERT INTO `sys_user` VALUES ('4', 'user3', '202CB962AC59075B964B07152D234B70', 'user3', null, null, '');
INSERT INTO `sys_user` VALUES ('5', 'user4', '202CB962AC59075B964B07152D234B70', 'user4', null, null, '');
INSERT INTO `sys_user` VALUES ('6', 'user5', '202CB962AC59075B964B07152D234B70', 'user5', null, null, '');
INSERT INTO `sys_user` VALUES ('7', 'user6', '202CB962AC59075B964B07152D234B70', 'user6', null, null, '');
INSERT INTO `sys_user` VALUES ('8', 'user7', '202CB962AC59075B964B07152D234B70', 'user7', null, null, '');
INSERT INTO `sys_user` VALUES ('9', 'user8', '202CB962AC59075B964B07152D234B70', 'user8', null, null, '');
INSERT INTO `sys_user` VALUES ('10', 'user9', '202CB962AC59075B964B07152D234B70', 'user9', null, null, '');
INSERT INTO `sys_user` VALUES ('11', 'user10', '202CB962AC59075B964B07152D234B70', 'user10', null, null, '');
INSERT INTO `sys_user` VALUES ('12', '用户1', '202CB962AC59075B964B07152D234B70', '用户1', null, null, '');
INSERT INTO `sys_user` VALUES ('13', '用户2', '202CB962AC59075B964B07152D234B70', '用户2', null, null, '');
INSERT INTO `sys_user` VALUES ('14', '用户3', '202CB962AC59075B964B07152D234B70', '用户3', null, null, '');
INSERT INTO `sys_user` VALUES ('15', '用户4', '202CB962AC59075B964B07152D234B70', '用户4', null, null, '');
INSERT INTO `sys_user` VALUES ('16', '用户5', '202CB962AC59075B964B07152D234B70', '用户5', null, null, '');
INSERT INTO `sys_user` VALUES ('17', '用户6', '202CB962AC59075B964B07152D234B70', '用户6', null, null, '');
INSERT INTO `sys_user` VALUES ('18', '用户7', '202CB962AC59075B964B07152D234B70', '用户7', null, null, '');
INSERT INTO `sys_user` VALUES ('19', '用户8', '202CB962AC59075B964B07152D234B70', '用户8', null, null, '');
INSERT INTO `sys_user` VALUES ('20', '用户9', '202CB962AC59075B964B07152D234B70', '用户9', null, null, '');
INSERT INTO `sys_user` VALUES ('21', '用户10', '202CB962AC59075B964B07152D234B70', '用户10', null, null, '');
INSERT INTO `sys_user` VALUES ('22', '用户11', '202CB962AC59075B964B07152D234B70', '用户11', null, null, '');
