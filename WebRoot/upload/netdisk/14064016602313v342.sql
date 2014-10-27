/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : framework

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2014-07-28 10:59:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_NAME` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `MENU_URL` varchar(200) DEFAULT NULL COMMENT '菜单网址',
  `MENU_ORDER` int(11) DEFAULT NULL COMMENT '菜单排序',
  `MENU_PID` int(11) DEFAULT NULL COMMENT '上级菜单ID',
  `ENABLE` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', '', '1', '0', '');
INSERT INTO `sys_menu` VALUES ('2', '用户管理', 'SysUser/list.html', '1', '1', '');
INSERT INTO `sys_menu` VALUES ('3', '角色管理', 'SysRole/list.html', '2', '1', '');
INSERT INTO `sys_menu` VALUES ('4', '菜单管理', 'SysMenu/list.html', '3', '1', '');
INSERT INTO `sys_menu` VALUES ('5', '网盘管理', '', '2', '0', '');
INSERT INTO `sys_menu` VALUES ('6', '公共网盘', 'SysNetdisk/list.html', '1', '5', '');
INSERT INTO `sys_menu` VALUES ('7', '回收站', 'SysNetdisk/recycle.html', '2', '5', '');
INSERT INTO `sys_menu` VALUES ('8', '系统信息', 'System/info.html', '4', '1', '');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='网盘';

-- ----------------------------
-- Records of sys_netdisk
-- ----------------------------
INSERT INTO `sys_netdisk` VALUES ('1', '14064016602313v342.sql', 'framework.sql', 'db', '9KB', '0', '1', '2014-07-27 03:07:44', '');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `ROLE_NAME` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `ROLE_DESC` varchar(200) DEFAULT NULL COMMENT '角色说明',
  `ENABLE` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', '系统管理员', '');
INSERT INTO `sys_role` VALUES ('2', '普通用户', '', '');
INSERT INTO `sys_role` VALUES ('3', ' 临时用户', '', '');

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) DEFAULT NULL COMMENT '角色ID',
  `MENU_ID` int(11) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='角色菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('30', '2', '5');
INSERT INTO `sys_role_menu` VALUES ('31', '2', '6');
INSERT INTO `sys_role_menu` VALUES ('32', '2', '7');
INSERT INTO `sys_role_menu` VALUES ('33', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('34', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('35', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('36', '1', '4');
INSERT INTO `sys_role_menu` VALUES ('37', '1', '8');
INSERT INTO `sys_role_menu` VALUES ('38', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('39', '1', '6');
INSERT INTO `sys_role_menu` VALUES ('40', '1', '7');

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
INSERT INTO `sys_user` VALUES ('1', 'admin', '202CB962AC59075B964B07152D234B70', 'gary', '127.0.0.1', '2014-07-28 10:33:02', '');
INSERT INTO `sys_user` VALUES ('2', 'user1', '202CB962AC59075B964B07152D234B70', 'user1', '127.0.0.1', '2014-07-27 03:03:14', '');
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

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) DEFAULT NULL COMMENT '角色ID',
  `USER_ID` int(11) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('16', '2', '12');
INSERT INTO `sys_user_role` VALUES ('17', '2', '13');
INSERT INTO `sys_user_role` VALUES ('18', '2', '14');
INSERT INTO `sys_user_role` VALUES ('19', '2', '15');
INSERT INTO `sys_user_role` VALUES ('20', '2', '16');
INSERT INTO `sys_user_role` VALUES ('21', '2', '17');
INSERT INTO `sys_user_role` VALUES ('22', '2', '18');
INSERT INTO `sys_user_role` VALUES ('23', '2', '19');
INSERT INTO `sys_user_role` VALUES ('24', '2', '20');
INSERT INTO `sys_user_role` VALUES ('25', '2', '21');
INSERT INTO `sys_user_role` VALUES ('27', '2', '22');
INSERT INTO `sys_user_role` VALUES ('28', '3', '2');
INSERT INTO `sys_user_role` VALUES ('29', '3', '3');
INSERT INTO `sys_user_role` VALUES ('30', '3', '4');
INSERT INTO `sys_user_role` VALUES ('31', '3', '5');
INSERT INTO `sys_user_role` VALUES ('32', '3', '6');
INSERT INTO `sys_user_role` VALUES ('33', '3', '7');
INSERT INTO `sys_user_role` VALUES ('34', '3', '8');
INSERT INTO `sys_user_role` VALUES ('35', '3', '9');
INSERT INTO `sys_user_role` VALUES ('36', '3', '10');
INSERT INTO `sys_user_role` VALUES ('37', '3', '11');
INSERT INTO `sys_user_role` VALUES ('38', '2', '2');
INSERT INTO `sys_user_role` VALUES ('39', '2', '3');
INSERT INTO `sys_user_role` VALUES ('40', '2', '4');
INSERT INTO `sys_user_role` VALUES ('41', '2', '5');
INSERT INTO `sys_user_role` VALUES ('42', '2', '6');
INSERT INTO `sys_user_role` VALUES ('43', '2', '7');
INSERT INTO `sys_user_role` VALUES ('44', '2', '8');
INSERT INTO `sys_user_role` VALUES ('45', '2', '9');
INSERT INTO `sys_user_role` VALUES ('46', '2', '10');
