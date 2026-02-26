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
  `lv` int(2) DEFAULT '2' COMMENT '管理员等级，0最高',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`id`,`name`,`password`,`phone`,`lv`) values (6,'0958','e10adc3949ba59abbe56e057f20f883e','18166950958',0),(9,'0951','e10adc3949ba59abbe56e057f20f883e','18166950951',2),(10,'0952','81dc9bdb52d04dc20036dbd8313ed055','18166950952',3),(20,'qx','e10adc3949ba59abbe56e057f20f883e','18166950000',3),(21,'123','e10adc3949ba59abbe56e057f20f883e','18166950955',3);

/*Table structure for table `admintree` */

DROP TABLE IF EXISTS `admintree`;

CREATE TABLE `admintree` (
  `streeID` bigint(20) NOT NULL,
  `treeName` varchar(100) NOT NULL,
  `treePath` varchar(100) DEFAULT NULL,
  `ftreeID` bigint(20) DEFAULT NULL,
  `lv` varchar(20) DEFAULT NULL,
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标代码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admintree` */

insert  into `admintree`(`streeID`,`treeName`,`treePath`,`ftreeID`,`lv`,`icon`) values (101,'用户管理','',1,'0,1,2,3','el-icon-user'),(10102,'封禁用户','user-list-block',101,'0,1',''),(10103,'解禁用户','user-list-left',101,'0,1',''),(102,'合集管理','',1,'0,1,2,3','el-icon-video-camera'),(10202,'待审核','group-list-wait',102,'0,2',''),(10203,'已通过','group-list-passed',102,'0,2',''),(10204,'已违规','group-list-illegal',102,'0,2',''),(103,'管理员管理','',1,'0','el-icon-s-custom'),(10301,'管理员列表','admin-list-all',103,'0',''),(10302,'添加管理员','admin-add',103,'0',''),(10303,'编辑管理员','admin-list-edit',103,'0',''),(10304,'删除管理员','admin-list-delete',103,'0',''),(10101,'用户列表','user-list-all',101,'0,1,2,3',''),(10201,'合集列表','group-list-all',102,'0,1,2,3',NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';

/*Data for the table `comment` */

insert  into `comment`(`id`,`content`,`group_id`,`user_id`,`status`,`create_time`) values (29,'好','34ff2758-0779-4aef-9e57-de59bde64f38',30,0,'2023-06-19 23:49:58'),(30,'好好好','34ff2758-0779-4aef-9e57-de59bde64f38',30,0,'2023-06-19 23:50:20'),(31,'好啊','34ff2758-0779-4aef-9e57-de59bde64f38',30,0,'2023-06-19 23:58:14'),(32,'sdf','34ff2758-0779-4aef-9e57-de59bde64f38',30,0,'2023-06-23 00:00:40');

/*Table structure for table `concern` */

DROP TABLE IF EXISTS `concern`;

CREATE TABLE `concern` (
  `authorid` bigint(20) NOT NULL,
  `userid` bigint(20) NOT NULL,
  `time` date DEFAULT NULL,
  PRIMARY KEY (`authorid`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `concern` */

/*Table structure for table `mycollect` */

DROP TABLE IF EXISTS `mycollect`;

CREATE TABLE `mycollect` (
  `user_id` bigint(20) NOT NULL,
  `group_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mycollect` */

insert  into `mycollect`(`user_id`,`group_id`) values (30,'34ff2758-0779-4aef-9e57-de59bde64f38');

/*Table structure for table `mylike` */

DROP TABLE IF EXISTS `mylike`;

CREATE TABLE `mylike` (
  `user_id` bigint(20) NOT NULL,
  `group_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mylike` */

insert  into `mylike`(`user_id`,`group_id`) values (30,'34ff2758-0779-4aef-9e57-de59bde64f38');

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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`password`,`email`,`phone`,`status`,`create_time`,`last_login_time`,`sex`,`age`) values (30,'0958','e10adc3949ba59abbe56e057f20f883e',NULL,'18166950958',1,'2023-06-19 20:36:52',NULL,1,23),(31,'18166950951','e10adc3949ba59abbe56e057f20f883e',NULL,'18166950951',1,'2023-06-21 09:48:07',NULL,1,23);

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
  `play_count` bigint(10) DEFAULT '0' COMMENT '播放量',
  `comment_count` bigint(10) DEFAULT '0' COMMENT '评论量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='视频表';

/*Data for the table `videogroup` */

insert  into `videogroup`(`id`,`title`,`author_id`,`brief`,`cover_url`,`create_time`,`catagory`,`istoll`,`price`,`videos`,`state`,`play_count`,`comment_count`) values ('2ddade3f-ca2b-4006-ab55-ba8199880606','vue2',30,'1234','/image/getImg?userid=30&type=videoGroupCover&groupid=2ddade3f-ca2b-4006-ab55-ba8199880606&timestamp=1687441698491','2023-06-22 21:48:19','[\"other\"]',1,'100.23',NULL,1,NULL,NULL),('34ff2758-0779-4aef-9e57-de59bde64f38','vue',30,'这是vue合集','/image/getImg?userid=30&type=videoGroupCover&groupid=34ff2758-0779-4aef-9e57-de59bde64f38&timestamp=1687181509232','2023-06-19 21:31:49','[\"it/foreground/vue\"]',1,'123.34','[{\"duration\":\"12\",\"index\":\"1\",\"state\":\"0\",\"title\":\"第一集\",\"newname\":\"16ad3792-84f6-4a8b-87b1-09c1aad3e186.mp4\",\"desc\":\"这是第一集，第一个程序\"},{\"duration\":\"18\",\"index\":\"2\",\"state\":\"0\",\"title\":\"第二集\",\"newname\":\"4bca6648-c922-43d7-b1a8-ede7581adaae.mp4\",\"desc\":\"这是第二集\"},{\"duration\":\"15\",\"index\":\"3\",\"state\":\"0\",\"title\":\"第三集\",\"newname\":\"908ff084-40c0-401d-bc9e-cc8674041eed.mp4\",\"desc\":\"这是第三集\"}]',1,19,4),('38aa9b3f-4041-4769-a2c9-1bc82e55c1d4','java',31,'1234','/image/getImg?userid=31&type=videoGroupCover&groupid=38aa9b3f-4041-4769-a2c9-1bc82e55c1d4&timestamp=1687340130879','2023-06-21 17:35:31','[\"it/foreground/vue\"]',1,'233.33',NULL,1,NULL,NULL),('4b463696-2f4d-4d02-9adb-e84a69b1207c','vue5',30,'5','/image/getImg?userid=30&type=videoGroupCover&groupid=4b463696-2f4d-4d02-9adb-e84a69b1207c&timestamp=1687441764214','2023-06-22 21:49:24','[\"it/foreground/vue\"]',1,'103.00',NULL,1,2,NULL),('864cd6b7-d146-477d-8d2c-4b5a2c2f4a5e','vue4',30,'v4','/image/getImg?userid=30&type=videoGroupCover&groupid=864cd6b7-d146-477d-8d2c-4b5a2c2f4a5e&timestamp=1687441744798','2023-06-22 21:49:05','[\"it/foreground/vue\"]',1,'103.00',NULL,0,NULL,NULL),('ba8b8214-1f1f-402e-b20f-f614d88593d0','vue3',30,'v3','/image/getImg?userid=30&type=videoGroupCover&groupid=ba8b8214-1f1f-402e-b20f-f614d88593d0&timestamp=1687441722705','2023-06-22 21:48:43','[\"it/foreground/vue\"]',1,'100.23',NULL,1,1,NULL),('ce02cede-aaab-4d9e-a2ab-752ef0669a34','vue6',30,'v6','/image/getImg?userid=30&type=videoGroupCover&groupid=ce02cede-aaab-4d9e-a2ab-752ef0669a34&timestamp=1687441790340','2023-06-22 21:49:50','[\"it/foreground/vue\"]',1,'233.00',NULL,1,5,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
