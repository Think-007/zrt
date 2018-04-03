/*
Navicat MySQL Data Transfer

Source Server         : 172.18.5.110
Source Server Version : 50525
Source Host           : 172.18.5.110:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2018-04-03 17:45:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for zrt_product
-- ----------------------------
DROP TABLE IF EXISTS `zrt_product`;
CREATE TABLE `zrt_product` (
  `product_name` varchar(30) DEFAULT NULL COMMENT '产品名称',
  `product_series` varchar(255) DEFAULT NULL COMMENT '产品所属系列',
  `product_pic` varchar(255) DEFAULT NULL COMMENT '产品图片',
  `product_desc` varchar(255) DEFAULT NULL COMMENT '商品的描述',
  `video_url` varchar(255) DEFAULT NULL COMMENT '音频地址',
  `audio_url` varchar(255) DEFAULT NULL COMMENT '视频地址',
  `audio_pic` varchar(255) DEFAULT NULL COMMENT '视频封面图片',
  `template_id` int(11) DEFAULT NULL COMMENT '模板id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of zrt_product
-- ----------------------------
