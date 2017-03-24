/*
Navicat MySQL Data Transfer

Source Server         : 192.168.30.3_test
Source Server Version : 50634
Source Host           : 192.168.30.3:3306
Source Database       : ddd

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2017-02-28 16:27:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_rabbitmq_dlq
-- ----------------------------
DROP TABLE IF EXISTS `tb_rabbitmq_dlq`;
CREATE TABLE `tb_rabbitmq_dlq` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MESSAGE_ID` varchar(50) DEFAULT NULL COMMENT '消息唯ID',
  `MESSAGE_BODY` varchar(255) DEFAULT NULL COMMENT '消息体',
  `MESSAGE_TYPE` varchar(255) DEFAULT '' COMMENT ' 消息类型',
  `MESSAGE_PIPELINE` varchar(255) DEFAULT NULL COMMENT '消息所属交换区',
  `MESSAGE_QUEUE` varchar(255) DEFAULT NULL COMMENT '来自那个监听',
  `MESSAGE_COUNT` int(11) DEFAULT NULL COMMENT '消费次数',
  `MESSAGE_TIME` timestamp NULL DEFAULT NULL COMMENT ' 消息创建时间',
  `DELIVERY_TAG` varchar(255) DEFAULT NULL COMMENT ' 消息通道标签',
  `DELIVERY_MODE` varchar(255) DEFAULT NULL COMMENT ' 消息类型',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `MESSAGE_STATUS` varchar(20) DEFAULT NULL COMMENT '消息重试处理状态',
  `REASON` varchar(25) DEFAULT NULL COMMENT ' 说明',
  `ROUTING_KEYS` varchar(50) DEFAULT NULL COMMENT ' 路由keys',
  `PRIORITY` int(11) DEFAULT NULL COMMENT '消息等级',
  `CONCURRENCY_VERSION` int(11) DEFAULT NULL COMMENT ' 并发版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=276 DEFAULT CHARSET=utf8;
