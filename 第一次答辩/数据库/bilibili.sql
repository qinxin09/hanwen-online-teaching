/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 8.0.15 : Database - bilibili
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bilibili` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bilibili`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`id`,`name`,`password`,`phone`) values (5,'勤欣','e10adc3949ba59abbe56e057f20f883e','18166950958');

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `group_id` varchar(36) NOT NULL COMMENT '视频id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(1:正常，2:删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';

/*Data for the table `comment` */

insert  into `comment`(`id`,`content`,`group_id`,`user_id`,`status`,`create_time`) values (26,'好','696b3eb0-0663-47c0-948e-a635477eb476',27,0,'2023-06-16 11:02:10'),(27,'好','0653915e-e974-4718-b297-0f1b5a5ee567',27,0,'2023-06-17 23:06:38');

/*Table structure for table `concern` */

DROP TABLE IF EXISTS `concern`;

CREATE TABLE `concern` (
  `authorid` bigint(20) NOT NULL,
  `userid` bigint(20) NOT NULL,
  `time` date DEFAULT NULL,
  PRIMARY KEY (`authorid`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `concern` */

insert  into `concern`(`authorid`,`userid`,`time`) values (27,28,'2023-06-16');

/*Table structure for table `mycollect` */

DROP TABLE IF EXISTS `mycollect`;

CREATE TABLE `mycollect` (
  `user_id` bigint(20) NOT NULL,
  `group_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mycollect` */

insert  into `mycollect`(`user_id`,`group_id`) values (27,'82c77fcd-b0ab-442c-bf35-600ca62565e1');

/*Table structure for table `mylike` */

DROP TABLE IF EXISTS `mylike`;

CREATE TABLE `mylike` (
  `user_id` bigint(20) NOT NULL,
  `group_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mylike` */

insert  into `mylike`(`user_id`,`group_id`) values (17,'5defabd4-b8dd-42e2-a755-b21af55f8ebf'),(26,'280e2349-8c4b-4588-a417-05f69c447a3d'),(26,'5defabd4-b8dd-42e2-a755-b21af55f8ebf'),(27,'0653915e-e974-4718-b297-0f1b5a5ee567'),(27,'696b3eb0-0663-47c0-948e-a635477eb476'),(28,'696b3eb0-0663-47c0-948e-a635477eb476');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(1:正常，2:冻结)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `sex` int(1) DEFAULT NULL COMMENT '1男，0女',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`password`,`email`,`phone`,`status`,`create_time`,`last_login_time`,`sex`,`age`) values (27,'0958','e10adc3949ba59abbe56e057f20f883e',NULL,'18166950958',1,'2023-06-15 20:15:57',NULL,1,22),(28,'0951','e10adc3949ba59abbe56e057f20f883e',NULL,'18166950951',1,'2023-06-15 20:16:32',NULL,1,20);

/*Table structure for table `videogroup` */

DROP TABLE IF EXISTS `videogroup`;

CREATE TABLE `videogroup` (
  `id` varchar(36) NOT NULL COMMENT '合集id',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `author_id` bigint(20) DEFAULT NULL COMMENT '作者id',
  `brief` varchar(255) DEFAULT NULL COMMENT '简介',
  `cover_url` varchar(255) DEFAULT NULL COMMENT '封面图片url',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `catagory` text NOT NULL COMMENT '分类',
  `istoll` tinyint(1) NOT NULL COMMENT '是否收费',
  `price` decimal(5,2) DEFAULT NULL COMMENT '合集价格',
  `videos` text COMMENT '视频列表json',
  `state` int(2) DEFAULT '0' COMMENT '0待审核，1通过，2违规',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='视频表';

/*Data for the table `videogroup` */

insert  into `videogroup`(`id`,`title`,`author_id`,`brief`,`cover_url`,`create_time`,`catagory`,`istoll`,`price`,`videos`,`state`) values ('0653915e-e974-4718-b297-0f1b5a5ee567','测试3',27,'第三次测试','/image/getImg?userid=27&type=videoGroupCover&groupid=0653915e-e974-4718-b297-0f1b5a5ee567&timestamp=1686886054883','2023-06-16 11:27:35','[\"it/foreground/vue\",\"it/foreground/layui\",\"it/foreground/other\"]',0,NULL,'[{\"index\":\"1\",\"state\":\"0\",\"title\":\"测试3\",\"newname\":\"4f3e27c8-9d62-4b12-b7f4-9efc99048fd8.mp4\",\"desc\":\"阿斯弗\"}]',1),('16840635-a852-4c80-91c5-0428d13c7659','nodejs0958',27,'这是0958创建的合集','/image/getImg?userid=27&type=videoGroupCover&groupid=16840635-a852-4c80-91c5-0428d13c7659&timestamp=1686837241469','2023-06-15 21:54:02','[\"other\"]',0,NULL,'[{\"index\":\"1\",\"state\":\"0\",\"title\":\"第一天\",\"newname\":\"0de6b636-8831-44a0-a0a3-4777083b6509.mp4\",\"desc\":\"学习nodejs第一天\"},{\"index\":\"2\",\"state\":\"0\",\"title\":\"第二天\",\"newname\":\"d56280dc-94aa-4dab-8be6-53a4cba88274.mp4\",\"desc\":\"nodejs第二天\"},{\"index\":\"3\",\"state\":\"0\",\"title\":\"第三天\",\"newname\":\"b7fdc76e-b8af-43d8-9fd2-3f163b479028.mp4\",\"desc\":\"学习nodejs第三天\"},{\"index\":\"4\",\"state\":\"0\",\"title\":\"第四天\",\"newname\":\"3c1a42d8-efe0-4e05-88b7-5f92134f9e23.mp4\",\"desc\":\"学习nodejs第四天\"},{\"index\":\"5\",\"state\":\"0\",\"title\":\"第五天\",\"newname\":\"72ea5e31-148b-49ba-b4ac-3486d249e073.mp4\",\"desc\":\"学习nodejs第五天\"},{\"index\":\"6\",\"state\":\"0\",\"title\":\"第六天\",\"newname\":\"ad856c23-72b8-4c5a-8346-a644ed5ed40e.mp4\",\"desc\":\"学习nodejs第六天\"},{\"index\":\"7\",\"state\":\"0\",\"title\":\"第十天\",\"newname\":\"ee864f95-a072-4f8f-908f-197919022d8a.mp4\",\"desc\":\"学习nodejs第十天\"}]',1),('465839e5-826f-4918-ae11-04f0af0fe17f','其它',27,'其它合集','/image/getImg?userid=27&type=videoGroupCover&groupid=465839e5-826f-4918-ae11-04f0af0fe17f&timestamp=1686838344107','2023-06-15 22:12:24','[\"it/foreground/other\"]',1,'321.23','[{\"index\":\"1\",\"state\":\"0\",\"title\":\"react1\",\"newname\":\"0b4805c9-9de8-4d1d-a4f9-eb3ea37e889a.mp4\",\"desc\":\"react实战\"}]',1),('696b3eb0-0663-47c0-948e-a635477eb476','vue0958',27,'这是0958上传的视频','/image/getImg?userid=27&type=videoGroupCover&groupid=696b3eb0-0663-47c0-948e-a635477eb476&timestamp=1686832475448','2023-06-15 20:34:36','[\"it/foreground/vue\"]',1,'123.33','[{\"index\":\"1\",\"state\":\"0\",\"title\":\"第一集\",\"newname\":\"26602091-c7ca-400a-bcc0-f958ffb0cd4d.mp4\",\"desc\":\"第一个VUE程序\"},{\"index\":\"2\",\"state\":\"0\",\"title\":\"第二集\",\"newname\":\"ce179617-b14d-4118-b484-b101ff9c7a80.mp4\",\"desc\":\"VUE常见指令\"},{\"index\":\"3\",\"state\":\"0\",\"title\":\"第三集\",\"newname\":\"d855a997-65ca-4a29-8622-eb3d6353dc45.mp4\",\"desc\":\"v-model的使用\"},{\"index\":\"4\",\"state\":\"0\",\"title\":\"第四集\",\"newname\":\"097c315c-0f8a-4512-a366-31e937d3be40.mp4\",\"desc\":\"MVVM设计模式\"}]',1),('82c77fcd-b0ab-442c-bf35-600ca62565e1','java进阶课0951',28,'这是0951创建的合集','/image/getImg?userid=28&type=videoGroupCover&groupid=82c77fcd-b0ab-442c-bf35-600ca62565e1&timestamp=1686835455782','2023-06-15 21:24:16','[\"it/java\"]',1,'99.99','[{\"index\":\"1\",\"state\":\"0\",\"title\":\"java进阶1\",\"newname\":\"71dcb2ea-ece6-49c1-b3a2-1365b8c93e58.mp4\",\"desc\":\"hashCode方法\"},{\"index\":\"2\",\"state\":\"0\",\"title\":\"java进阶2\",\"newname\":\"6c204c7b-323e-4d16-8106-30790544846c.mp4\",\"desc\":\"可变字符串互转\"},{\"index\":\"3\",\"state\":\"0\",\"title\":\"java进阶3\",\"newname\":\"41db2462-2b08-46ce-b5e5-2fe5dee36a6a.mp4\",\"desc\":\"自动装箱与拆箱\"},{\"index\":\"4\",\"state\":\"0\",\"title\":\"java进阶4\",\"newname\":\"7301f6a1-8fb4-4ad5-a326-cd7ab8552e0f.mp4\",\"desc\":\"Random随机数\"}]',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
