/*
Navicat MySQL Data Transfer

Source Server         : student
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : studentsystem

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-08-24 15:40:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 NOT NULL,
  `age` tinyint(8) DEFAULT NULL,
  `gender` tinyint(1) NOT NULL,
  `major` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `academy` varchar(255) CHARACTER SET utf8 NOT NULL,
  `headPath` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `authority` tinyint(1) NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '管理', '3', '0', '你师兄', '互联网学院', 'sss', '1', '202CB962AC59075B964B07152D234B70');
INSERT INTO `student` VALUES ('33', '2525', '66', '0', '3123', '外文学院', null, '0', '202CB962AC59075B964B07152D234B70');
INSERT INTO `student` VALUES ('35', '2525', '25', '0', '计科', '互联网学院', null, '0', '202CB962AC59075B964B07152D234B70');
INSERT INTO `student` VALUES ('38', '2525', '16', '1', '金工', '金投院', null, '1', '202CB962AC59075B964B07152D234B70');
