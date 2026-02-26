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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`id`,`name`,`password`,`phone`,`lv`) values (6,'0958','e10adc3949ba59abbe56e057f20f883e','18166950958',0),(9,'0951','e10adc3949ba59abbe56e057f20f883e','18166950951',2),(10,'0952','81dc9bdb52d04dc20036dbd8313ed055','18166950952',3),(20,'qx','e10adc3949ba59abbe56e057f20f883e','18166950000',3),(22,'合集审核员','e10adc3949ba59abbe56e057f20f883e','18166951111',2),(23,'lv4','e10adc3949ba59abbe56e057f20f883e','18166954444',5),(25,'6666','e10adc3949ba59abbe56e057f20f883e','18166666666',5);

/*Table structure for table `admintree` */

DROP TABLE IF EXISTS `admintree`;

CREATE TABLE `admintree` (
  `streeID` bigint(20) NOT NULL,
  `treeName` varchar(100) NOT NULL,
  `treePath` varchar(100) DEFAULT NULL,
  `ftreeID` bigint(20) DEFAULT NULL,
  `lv` varchar(20) DEFAULT NULL,
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标代码',
  PRIMARY KEY (`streeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admintree` */

insert  into `admintree`(`streeID`,`treeName`,`treePath`,`ftreeID`,`lv`,`icon`) values (101,'用户管理','',1,'0,1,4,3','el-icon-user'),(102,'合集管理','',1,'0,3,2','el-icon-video-camera'),(103,'管理员管理','',1,'0','el-icon-s-custom'),(104,'权限管理',NULL,1,'0','el-icon-lock'),(105,'创作者管理','',1,'0,5','el-icon-video-camera-solid'),(10101,'用户列表','user-list-all',101,'0,1,4,3',''),(10102,'封禁用户','user-list-block',101,'0,1,3',''),(10103,'解禁用户','user-list-left',101,'0,1,3',''),(10201,'合集列表','group-list-all',102,'0,3,2',NULL),(10202,'待审核','group-list-wait',102,'0,3,2',''),(10203,'已通过','group-list-passed',102,'0,3,2',''),(10204,'已违规','group-list-illegal',102,'0,2',''),(10301,'管理员列表','admin-list-all',103,'0',''),(10302,'添加管理员','admin-add',103,'0',''),(10303,'编辑管理员','admin-list-edit',103,'0',''),(10304,'删除管理员','admin-list-delete',103,'0',''),(10401,'权限列表','permission-list',104,'0',NULL),(10501,'进行中','task-list-active',105,'0',NULL),(10502,'已完结','task-list-ended',105,'0',NULL),(10503,'发布任务','task-list-publish',105,'0',NULL),(10504,'创建任务','task-create',105,'0',NULL),(10505,'作品管理123','works-list',105,'0,5',NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';

/*Data for the table `comment` */

insert  into `comment`(`id`,`content`,`group_id`,`user_id`,`status`,`create_time`) values (29,'好','34ff2758-0779-4aef-9e57-de59bde64f38',30,0,'2023-06-19 23:49:58'),(30,'好好好','34ff2758-0779-4aef-9e57-de59bde64f38',30,0,'2023-06-19 23:50:20'),(31,'好啊','34ff2758-0779-4aef-9e57-de59bde64f38',30,0,'2023-06-19 23:58:14'),(32,'sdf','34ff2758-0779-4aef-9e57-de59bde64f38',30,0,'2023-06-23 00:00:40'),(33,'好','ce02cede-aaab-4d9e-a2ab-752ef0669a34',30,0,'2023-06-28 21:33:22'),(34,'好','a5f8b412-d4b8-4a34-a56e-778608064219',30,0,'2023-07-10 09:37:28');

/*Table structure for table `concern` */

DROP TABLE IF EXISTS `concern`;

CREATE TABLE `concern` (
  `authorid` bigint(20) NOT NULL,
  `userid` bigint(20) NOT NULL,
  `time` date DEFAULT NULL,
  PRIMARY KEY (`authorid`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `concern` */

/*Table structure for table `crawler` */

DROP TABLE IF EXISTS `crawler`;

CREATE TABLE `crawler` (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cover_url` text,
  `video_url` text,
  `length` varchar(10) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `play_count` varchar(200) DEFAULT NULL,
  `author_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

/*Data for the table `crawler` */

insert  into `crawler`(`id`,`cover_url`,`video_url`,`length`,`title`,`play_count`,`author_name`) values (35,'/image/getImg?type=crawlerCoverImg&fileName=1688971081386.jpg','https://haokan.baidu.com/v?vid=5381409118917773857&sfrom=recommend','00:21','原神，雷神一刀流操作，你品，你细品！','10.0万次播放 . 2022年08月21日','努力冷静的枕头'),(36,'/image/getImg?type=crawlerCoverImg&fileName=1688971081757.jpg','https://haokan.baidu.com/v?vid=15696880438069136112&sfrom=recommend','01:43','逆天的吃鸡手法：这才是真正的绝地求生吧？','11万次播放 . 2022年08月21日','游戏西小瓜'),(37,'/image/getImg?type=crawlerCoverImg&fileName=1688971081876.jpg','https://haokan.baidu.com/v?vid=4417407313082674964&sfrom=recommend','01:03','咱来学习单词countryside','9.8万次播放 . 2022年08月21日','小迪教英语'),(38,'/image/getImg?type=crawlerCoverImg&fileName=1688971081997.jpg','https://haokan.baidu.com/v?vid=10934348668221820826&sfrom=recommend','04:23','今天晚餐爱了，尤其是红烧鲤鱼做得特别棒，幸福两口子吃得美滋滋','9.6万次播放 . 2022年08月21日','惜臻生活日记'),(39,'/image/getImg?type=crawlerCoverImg&fileName=1688971082160.jpg','https://haokan.baidu.com/v?vid=17423502808667523815&sfrom=recommend','01:23','魔兽真三国无双5.9D，司马懿的技能演示','7.6万次播放 . 2022年08月21日','87电玩大叔'),(40,'/image/getImg?type=crawlerCoverImg&fileName=1688971082286.jpg','https://haokan.baidu.com/v?vid=6126984882901974993&sfrom=recommend','00:42','王者荣耀英雄猴子','9.5万次播放 . 2022年08月21日','百家小浩洋'),(41,'/image/getImg?type=crawlerCoverImg&fileName=1688971082418.jpg','https://haokan.baidu.com/v?vid=11116550053248918967&sfrom=recommend','02:12','正片来了，实战细节无敌近战闪镜2.0','11万次播放 . 2022年08月21日','白宋游戏教学'),(42,'/image/getImg?type=crawlerCoverImg&fileName=1688971082616.jpg','https://haokan.baidu.com/v?vid=12763127217567401192&sfrom=recommend','01:45','植物大战僵尸：最佳男团pk，谁是最强阵容？','9.8万次播放 . 2022年08月21日','琪白石'),(43,'/image/getImg?type=crawlerCoverImg&fileName=1688971082798.jpg','https://haokan.baidu.com/v?vid=7971992402145997050&sfrom=recommend','02:17','迷你世界：不同boos的战斗力！羽蛇神只有250，而黑龙有2000？','11万次播放 . 2022年08月21日','毛豆迷你世界吖'),(44,'/image/getImg?type=crawlerCoverImg&fileName=1688971083009.jpg','https://haokan.baidu.com/v?vid=15205373927418293172&sfrom=recommend','00:12','银赫的眼神太有故事了，瞬间就被戳中','5.8万次播放 . 2022年08月21日','会火'),(45,'/image/getImg?type=crawlerCoverImg&fileName=1689042026947.jpg','https://haokan.baidu.com/v?vid=3509730743093689839&sfrom=recommend','01:24','大叔给轮船底部清除藤壶，月入3w，赚钱无所谓，主要是解压','4.1万次播放 . 2022年08月21日','袁瑞爱动物'),(46,'/image/getImg?type=crawlerCoverImg&fileName=1689042027265.jpg','https://haokan.baidu.com/v?vid=7045138314764924205&sfrom=recommend','01:23','游戏搬砖收徒弟等于割韭菜吗？','4.2万次播放 . 2022年08月21日','东大木游戏'),(47,'/image/getImg?type=crawlerCoverImg&fileName=1689042027353.jpg','https://haokan.baidu.com/v?vid=7163047852459185352&sfrom=recommend','01:10','迷你世界移动速度排名，哪种方式最快？最后一个不要眨眼！','13万次播放 . 2022年08月21日','学长很正经'),(48,'/image/getImg?type=crawlerCoverImg&fileName=1689042027425.jpg','https://haokan.baidu.com/v?vid=5844287766264469144&sfrom=recommend','01:08','洛克王国桃李满天下，春晖遍四方，呱呱师父兔仙人解析','13万次播放 . 2022年08月21日','甜糯米米'),(49,'/image/getImg?type=crawlerCoverImg&fileName=1689042027514.jpg','https://haokan.baidu.com/v?vid=3634769498135910235&sfrom=recommend','01:38','甜橙酱：挑战不淘汰突变者 全程苟分溜人 凭空变身超级猎手！','12万次播放 . 2022年08月21日','甜橙酱解说'),(50,'/image/getImg?type=crawlerCoverImg&fileName=1689042027596.jpg','https://haokan.baidu.com/v?vid=11798066697143361024&sfrom=recommend','00:40','有贫血检查血小板低，应该怎么办？','10万次播放 . 2022年08月21日','血液科刘驰医生'),(51,'/image/getImg?type=crawlerCoverImg&fileName=1689042027665.jpg','https://haokan.baidu.com/v?vid=12147339322542457609&sfrom=recommend','01:29','十多万就能提14款二手宝马，牌面绝对杠杠的，说不定还能学会修车','10万次播放 . 2022年08月21日','小李秒秒车'),(52,'/image/getImg?type=crawlerCoverImg&fileName=1689042027761.jpg','https://haokan.baidu.com/v?vid=7175283880117686951&sfrom=recommend','01:59','《神王的日常生活102上》菜刀大佬VS金翎仙剑','23万次播放 . 2022年08月21日','浆糊百晓升'),(53,'/image/getImg?type=crawlerCoverImg&fileName=1689042027852.jpg','https://haokan.baidu.com/v?vid=13198358679405223411&sfrom=recommend','02:22','学霸为接近女孩，竟会这样做','11万次播放 . 2022年08月21日','呆龟影视'),(54,'/image/getImg?type=crawlerCoverImg&fileName=1689294646568.jpg','https://haokan.baidu.com/v?vid=17613693602544408197&sfrom=recommend','03:55','来自金字塔内的超兽，持刀暴徒艾斯，大战斯芬克斯','15万次播放 . 2022年08月21日','一团动漫'),(55,'/image/getImg?type=crawlerCoverImg&fileName=1689294647058.jpg','https://haokan.baidu.com/v?vid=11292075038212415664&sfrom=recommend','03:32','双一流高校都分布在哪些地方？这10座城市最适合读大学！','12万次播放 . 2022年08月21日','导航教育'),(56,'/image/getImg?type=crawlerCoverImg&fileName=1689294647273.jpg','https://haokan.baidu.com/v?vid=15205373927418293172&sfrom=recommend','00:12','银赫的眼神太有故事了，瞬间就被戳中','5.9万次播放 . 2022年08月21日','会火'),(57,'/image/getImg?type=crawlerCoverImg&fileName=1689294647401.jpg','https://haokan.baidu.com/v?vid=5482418486375686743&sfrom=recommend','01:49','红木圆台的尺寸和椅子的张数是多少，都是有一个标准的','10万次播放 . 2022年08月21日','东阳红木聪弟'),(58,'/image/getImg?type=crawlerCoverImg&fileName=1689294647509.jpg','https://haokan.baidu.com/v?vid=786665845529165107&sfrom=recommend','00:25','雀氏帅！','5.1万次播放 . 2022年08月21日','花伞国服阿离'),(59,'/image/getImg?type=crawlerCoverImg&fileName=1689294647611.jpg','https://haokan.baidu.com/v?vid=2587829571895247323&sfrom=recommend','01:16','罪犯在24小时监控下，竟然完美越狱，真实版越狱','12万次播放 . 2022年08月21日','龙公子电影'),(60,'/image/getImg?type=crawlerCoverImg&fileName=1689294647938.jpg','https://haokan.baidu.com/v?vid=11130148655768595528&sfrom=recommend','00:36','为什么七月份不搬家不买家具，其实是有道理的 国学智慧 曾仕','10万次播放 . 2022年08月21日','肥猫不挑食'),(61,'/image/getImg?type=crawlerCoverImg&fileName=1689294648156.jpg','https://haokan.baidu.com/v?vid=15213830146983195801&sfrom=recommend','01:42','非洲蜗牛遇上好心人，被救助，红火蚁却来偷袭','37万次播放 . 2022年08月21日','老黄的小世界'),(62,'/image/getImg?type=crawlerCoverImg&fileName=1689297887120.jpg','https://haokan.baidu.com/v?vid=752165580218253341&sfrom=recommend','06:54','母亲撒下弥天大谎，被拆穿后遭众叛亲离','7.3万次播放 . 03月22日','神喵追剧'),(63,'/image/getImg?type=crawlerCoverImg&fileName=1689297887470.jpg','https://haokan.baidu.com/v?vid=8818524898398856641&sfrom=recommend','05:23','2023团体赛：蒋川VS洪智，开局脱谱乱战大斗综合功力，鹿死谁手？','2.1万次播放 . 03月22日','林萧棋苑'),(64,'/image/getImg?type=crawlerCoverImg&fileName=1689297887584.jpg','https://haokan.baidu.com/v?vid=10123946170831040509&sfrom=recommend','04:47','堵门送货，少见的木箱','7959次播放 . 03月22日','周德明tuhot67'),(65,'/image/getImg?type=crawlerCoverImg&fileName=1689297887692.jpg','https://haokan.baidu.com/v?vid=14247824085022061352&sfrom=recommend','02:10','theshy格温对线刀妹，一级就开始对刚，线上疯狂单吃，操作拉满','9316次播放 . 03月22日','小小shy君'),(66,'/image/getImg?type=crawlerCoverImg&fileName=1689297888611.jpg','https://haokan.baidu.com/v?vid=14079800235199845268&sfrom=recommend','00:24','总公司签工程承包合同，分公司施工也可以','6752次播放 . 03月22日','税务筹划曾老师'),(67,'/image/getImg?type=crawlerCoverImg&fileName=1689297888682.jpg','https://haokan.baidu.com/v?vid=8332363896266564589&sfrom=recommend','01:27','樱花校园模拟器拍剧＃搞笑女恋爱第六集','1.8万次播放 . 03月22日','酸奶888'),(68,'/image/getImg?type=crawlerCoverImg&fileName=1689297888819.jpg','https://haokan.baidu.com/v?vid=12044588368491672277&sfrom=recommend','04:43','来到智商很低的世界，吾很帅成为天才，一加一等于几都知道','3.6万次播放 . 03月22日','杰哥帅搞笑日常'),(69,'/image/getImg?type=crawlerCoverImg&fileName=1689297888908.jpg','https://haokan.baidu.com/v?vid=9731662589216106590&sfrom=recommend','00:34','一个方子《控炎消腺汤》，缩小孩子腺样体肥大，改善张嘴打呼！','2086次播放 . 03月22日','王道涵主任讲儿科');

/*Table structure for table `mycollect` */

DROP TABLE IF EXISTS `mycollect`;

CREATE TABLE `mycollect` (
  `user_id` bigint(20) NOT NULL,
  `group_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mycollect` */

insert  into `mycollect`(`user_id`,`group_id`) values (30,'043cc97a-5407-4087-9b47-62d3c466d8f6'),(30,'34ff2758-0779-4aef-9e57-de59bde64f38'),(30,'95846dc9-45c6-4a11-a882-ffb84b54cb57');

/*Table structure for table `mylike` */

DROP TABLE IF EXISTS `mylike`;

CREATE TABLE `mylike` (
  `user_id` bigint(20) NOT NULL,
  `group_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mylike` */

insert  into `mylike`(`user_id`,`group_id`) values (30,'043cc97a-5407-4087-9b47-62d3c466d8f6'),(30,'34ff2758-0779-4aef-9e57-de59bde64f38'),(30,'8fefd89b-c324-4357-a28e-929045697b6c'),(30,'a5f8b412-d4b8-4a34-a56e-778608064219');

/*Table structure for table `permissions` */

DROP TABLE IF EXISTS `permissions`;

CREATE TABLE `permissions` (
  `id` int(3) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `leval` int(2) NOT NULL COMMENT '等级',
  `name` varchar(10) NOT NULL COMMENT '名称',
  `createTime` date DEFAULT NULL COMMENT '创建时间',
  `items` varchar(255) DEFAULT NULL COMMENT '授权的列表',
  `roles` varchar(50) DEFAULT NULL COMMENT '绑定的角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `permissions` */

insert  into `permissions`(`id`,`leval`,`name`,`createTime`,`items`,`roles`) values (13,1,'一级','2023-06-28','用户列表,封禁用户,解禁用户','1'),(18,2,'合集审核','2023-07-13','合集列表,待审核,已通过,已违规','4,3,1,2'),(21,3,'3','2023-06-28','用户列表,封禁用户,解禁用户,合集列表,待审核,已通过','4,3,1'),(22,4,'四级','2023-07-13','用户列表','4'),(24,5,'作品管理员','2023-07-14','作品管理123','4,3,2');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(3) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(20) NOT NULL COMMENT '角色名称',
  `leval` int(2) DEFAULT NULL COMMENT '角色的等级',
  `desc` varchar(100) DEFAULT NULL COMMENT '角色的描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`leval`,`desc`) values (1,'用户管理员',1,'查看，并管理用户账户'),(2,'合集审核员',2,'审核用户上传的合集，包括查看、删除、审核'),(3,'普通员工',3,'查看用户信息，合集信息');

/*Table structure for table `tasks` */

DROP TABLE IF EXISTS `tasks`;

CREATE TABLE `tasks` (
  `id` bigint(14) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `date_start` date DEFAULT NULL COMMENT '开始时间',
  `date_stop` date DEFAULT NULL COMMENT '截止时间',
  `task_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务描述',
  `gifts_desc` varchar(255) DEFAULT NULL COMMENT '任务奖品',
  `state` int(2) DEFAULT NULL COMMENT '任务状态(0待发布，1进行中，2已结束）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1689298149195 DEFAULT CHARSET=utf8;

/*Data for the table `tasks` */

insert  into `tasks`(`id`,`name`,`date_start`,`date_stop`,`task_desc`,`gifts_desc`,`state`) values (1688382597006,'测试1','2023-06-29','2023-07-19','1234','1234',1),(1688385677980,'阿斯顿','2023-06-26','2023-07-19','df','d\'s',0),(1688450549923,'第二个任务','2023-06-30','2023-07-25','撒旦发个','撒旦发生',1),(1688473340729,'任务三','2023-07-03','2023-07-10','这是第三个任务','实现了就通过考核！',1),(1689266128039,'测试','2023-07-13','2023-07-14','测试','测试',1),(1689298149194,'测试qwe','2023-07-14','2023-07-16','qwert','jss',1);

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
  `vip` int(2) DEFAULT '0' COMMENT '1会员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`password`,`email`,`phone`,`status`,`create_time`,`last_login_time`,`sex`,`age`,`vip`) values (30,'0958','e10adc3949ba59abbe56e057f20f883e',NULL,'18166950958',1,'2023-06-19 20:36:52',NULL,1,23,0),(31,'18166950951','e10adc3949ba59abbe56e057f20f883e',NULL,'18166950951',1,'2023-06-21 09:48:07',NULL,1,23,0);

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
  `attachement_names` varchar(255) DEFAULT NULL COMMENT '附件文件名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='视频表';

/*Data for the table `videogroup` */

insert  into `videogroup`(`id`,`title`,`author_id`,`brief`,`cover_url`,`create_time`,`catagory`,`istoll`,`price`,`videos`,`state`,`play_count`,`comment_count`,`attachement_names`) values ('043cc97a-5407-4087-9b47-62d3c466d8f6','vue11',30,'vue11','/image/getImg?userid=30&type=videoGroupCover&groupid=043cc97a-5407-4087-9b47-62d3c466d8f6&timestamp=1688049340505','2023-06-29 22:35:41','[\"it/foreground/vue\",\"it/foreground/layui\",\"it/foreground/other\"]',1,'123.00','[{\"duration\":\"18\",\"index\":\"1\",\"state\":\"0\",\"title\":\"11\",\"newname\":\"0834f2c5-6dbe-47ce-89d0-293eaabb74ff.mp4\",\"desc\":\"11\"}]',1,4,NULL,'2023-06-07_165633.png,vue.docx,vue.pdf'),('2ddade3f-ca2b-4006-ab55-ba8199880606','vue2',30,'1234','/image/getImg?userid=30&type=videoGroupCover&groupid=2ddade3f-ca2b-4006-ab55-ba8199880606&timestamp=1687441698491','2023-06-22 21:48:19','[\"other\"]',1,'100.23','[{\"duration\":\"18\",\"index\":\"1\",\"state\":\"0\",\"title\":\"vue2\",\"newname\":\"4647fc93-946d-455e-a715-764a3bcfd2ac.mp4\",\"desc\":\"v\"}]',1,4,NULL,NULL),('34ff2758-0779-4aef-9e57-de59bde64f38','vue',30,'这是vue合集','/image/getImg?userid=30&type=videoGroupCover&groupid=34ff2758-0779-4aef-9e57-de59bde64f38&timestamp=1687181509232','2023-06-19 21:31:49','[\"it/foreground/vue\"]',1,'123.34','[{\"duration\":\"12\",\"index\":\"1\",\"state\":\"0\",\"title\":\"第一集\",\"newname\":\"16ad3792-84f6-4a8b-87b1-09c1aad3e186.mp4\",\"desc\":\"这是第一集，第一个程序\"},{\"duration\":\"18\",\"index\":\"2\",\"state\":\"0\",\"title\":\"第二集\",\"newname\":\"4bca6648-c922-43d7-b1a8-ede7581adaae.mp4\",\"desc\":\"这是第二集\"},{\"duration\":\"15\",\"index\":\"3\",\"state\":\"0\",\"title\":\"第三集\",\"newname\":\"908ff084-40c0-401d-bc9e-cc8674041eed.mp4\",\"desc\":\"这是第三集\"},{\"duration\":\"174\",\"index\":\"4\",\"state\":\"0\",\"title\":\"1\",\"newname\":\"fa81fba1-1d79-4ab3-987e-588c97fd204b.mp4\",\"desc\":\"测试\"}]',1,49,4,NULL),('38aa9b3f-4041-4769-a2c9-1bc82e55c1d4','java',31,'1234','/image/getImg?userid=31&type=videoGroupCover&groupid=38aa9b3f-4041-4769-a2c9-1bc82e55c1d4&timestamp=1687340130879','2023-06-21 17:35:31','[\"it/foreground/vue\"]',1,'233.33','[{\"duration\":\"174\",\"index\":\"1\",\"state\":\"0\",\"title\":\"1234\",\"newname\":null,\"desc\":\"这是0951上传的视频\"}]',1,2,NULL,NULL),('44e87776-2672-42bf-9720-937ef36c16a9','vue',30,'a','/image/getImg?userid=30&type=videoGroupCover&groupid=44e87776-2672-42bf-9720-937ef36c16a9&timestamp=1687967120354','2023-06-28 23:45:21','[\"it/foreground/vue\"]',0,NULL,'[{\"duration\":\"18\",\"index\":\"1\",\"state\":\"0\",\"title\":\"v\",\"newname\":\"aa43efc0-6cbc-43da-8266-0ee07c63137c.mp4\",\"desc\":\"v\"}]',1,4,NULL,NULL),('4b463696-2f4d-4d02-9adb-e84a69b1207c','vue5',30,'5','/image/getImg?userid=30&type=videoGroupCover&groupid=4b463696-2f4d-4d02-9adb-e84a69b1207c&timestamp=1687441764214','2023-06-22 21:49:24','[\"it/foreground/vue\"]',1,'103.00',NULL,1,2,NULL,NULL),('864cd6b7-d146-477d-8d2c-4b5a2c2f4a5e','vue4',30,'v4','/image/getImg?userid=30&type=videoGroupCover&groupid=864cd6b7-d146-477d-8d2c-4b5a2c2f4a5e&timestamp=1687441744798','2023-06-22 21:49:05','[\"it/foreground/vue\"]',1,'103.00','[{\"duration\":\"12\",\"index\":\"1\",\"state\":\"0\",\"title\":\"vue4\",\"newname\":\"9a9002b3-010f-4900-87fa-f738c421acb7.mp4\",\"desc\":\"4\"}]',1,2,NULL,NULL),('8fefd89b-c324-4357-a28e-929045697b6c','vue10',30,'vue10 __','/image/getImg?userid=30&type=videoGroupCover&groupid=8fefd89b-c324-4357-a28e-929045697b6c&timestamp=1687967188857','2023-06-28 23:46:29','[\"it/foreground/vue\"]',0,NULL,'[{\"duration\":\"12\",\"index\":\"1\",\"state\":\"0\",\"title\":\"hj\",\"newname\":\"6469e44c-3753-410a-a358-1514e44877bf.mp4\",\"desc\":\"sdf\"},{\"duration\":\"15\",\"index\":\"2\",\"state\":\"0\",\"title\":\"3\",\"newname\":\"38a65bed-c140-4668-93b8-ed6198ae67b6.mp4\",\"desc\":\"333\"},{\"duration\":\"18\",\"index\":\"3\",\"state\":\"0\",\"title\":\"2\",\"newname\":\"ad1f61ed-25d5-49a2-af75-4440415d6836.mp4\",\"desc\":\"222\"},{\"duration\":\"12\",\"index\":\"4\",\"state\":\"0\",\"title\":\"vue 44444\",\"newname\":\"c353315e-b52b-416c-bc13-b2951f586ba3.mp4\",\"desc\":\"444\"}]',1,7,NULL,NULL),('95846dc9-45c6-4a11-a882-ffb84b54cb57','测试附件',30,'这是测试附件','/image/getImg?userid=30&type=videoGroupCover&groupid=95846dc9-45c6-4a11-a882-ffb84b54cb57&timestamp=1688558960981','2023-07-05 20:09:21','[\"it/foreground/vue\"]',0,NULL,'[{\"duration\":\"173\",\"index\":\"1\",\"state\":\"0\",\"title\":\"测试附件\",\"newname\":\"304a75c5-e0f5-40e1-9ac4-315fbf77e8c9.mp4\",\"desc\":\"测试附件\"},{\"duration\":\"151\",\"index\":\"2\",\"state\":\"0\",\"title\":\"测试附件3\",\"newname\":\"a5f0f94c-c277-46f8-b060-afa8e8d369cc.mp4\",\"desc\":\"测试附件3\"}]',1,33,NULL,'vue.pdf'),('9dacd207-f7ab-4698-ac25-09d6aca37f9e','java1',30,'java1','/image/getImg?userid=30&type=videoGroupCover&groupid=9dacd207-f7ab-4698-ac25-09d6aca37f9e&timestamp=1687524146212','2023-06-23 20:42:26','[\"it/java\"]',1,'100.23','[{\"duration\":\"174\",\"index\":\"1\",\"state\":\"0\",\"title\":\"java1\",\"newname\":\"1870267d-6bf0-420b-89b1-037e7523f8b7.mp4\",\"desc\":\"1\"}]',1,10,NULL,NULL),('a5f8b412-d4b8-4a34-a56e-778608064219','vue12',30,'vue12','/image/getImg?userid=30&type=videoGroupCover&groupid=a5f8b412-d4b8-4a34-a56e-778608064219&timestamp=1688049691391','2023-06-29 22:41:31','[\"it/foreground/vue\",\"it/foreground/layui\",\"it/foreground/other\"]',0,NULL,'[{\"duration\":\"12\",\"index\":\"1\",\"state\":\"0\",\"title\":\"vue12\",\"newname\":\"6e715582-bfff-4e14-a810-d488d7b0c5ab.mp4\",\"desc\":\"12\"}]',1,25,1,'2023-06-07_165633 - 副本.png,vue.docx,vue.pdf'),('ba8b8214-1f1f-402e-b20f-f614d88593d0','vue3',30,'v3','/image/getImg?userid=30&type=videoGroupCover&groupid=ba8b8214-1f1f-402e-b20f-f614d88593d0&timestamp=1687441722705','2023-06-22 21:48:43','[\"it/foreground/vue\"]',1,'100.23',NULL,1,3,NULL,NULL),('ce02cede-aaab-4d9e-a2ab-752ef0669a34','vue6',30,'v6','/image/getImg?userid=30&type=videoGroupCover&groupid=ce02cede-aaab-4d9e-a2ab-752ef0669a34&timestamp=1687441790340','2023-06-22 21:49:50','[\"it/foreground/vue\"]',1,'233.00','[{\"duration\":\"12\",\"index\":\"1\",\"state\":\"0\",\"title\":\"1\",\"newname\":\"f017e157-d533-4843-a4c3-2c0622c159a9.mp4\",\"desc\":\"1\"}]',1,9,1,NULL),('f2ada5f7-705c-4df5-946c-af7cbd7cde58','测试附件1234',30,'1234','/image/getImg?userid=30&type=videoGroupCover&groupid=f2ada5f7-705c-4df5-946c-af7cbd7cde58&timestamp=1689298009946','2023-07-14 09:26:50','[\"it/foreground/vue\"]',0,NULL,NULL,1,0,NULL,'vue.pdf');

/*Table structure for table `votes` */

DROP TABLE IF EXISTS `votes`;

CREATE TABLE `votes` (
  `tourist_id` varchar(36) NOT NULL COMMENT '游客id',
  `works_id` varchar(36) NOT NULL COMMENT '作品id',
  `date` varchar(10) NOT NULL COMMENT '投票日期',
  `num` int(1) NOT NULL DEFAULT '0' COMMENT '投票数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `votes` */

insert  into `votes`(`tourist_id`,`works_id`,`date`,`num`) values ('93020ea9-19d9-42fd-968b-71731d17916b','e23498dc-6e55-47b2-8cb3-9d9ed9f00e0f','2023-07-05',4),('93020ea9-19d9-42fd-968b-71731d17916b','8692e7d6-04ec-44d4-aaa9-2f46983f629b','2023-07-06',5),('93020ea9-19d9-42fd-968b-71731d17916b','8692e7d6-04ec-44d4-aaa9-2f46983f629b','2023-07-10',4),('93020ea9-19d9-42fd-968b-71731d17916b','8692e7d6-04ec-44d4-aaa9-2f46983f629b','2023-07-9',4),('93020ea9-19d9-42fd-968b-71731d17916b','e23498dc-6e55-47b2-8cb3-9d9ed9f00e0f','2023-07-11',4),('140c77dd-f8b7-4bee-ae01-b4942aec252c','96aa0f30-d19c-4650-968d-59f144ef2ff8','2023-07-14',4);

/*Table structure for table `works` */

DROP TABLE IF EXISTS `works`;

CREATE TABLE `works` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `taskid` bigint(14) NOT NULL COMMENT '任务id',
  `video_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '上传的视频名称',
  `video_length` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '视频长度',
  `id` varchar(36) NOT NULL COMMENT 'uuid',
  `url` varchar(255) NOT NULL COMMENT '视频链接',
  `state` int(2) DEFAULT NULL COMMENT '状态0待审核，1通过，2违规',
  `video_desc` varchar(255) DEFAULT NULL COMMENT '视频介绍',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `works` */

insert  into `works`(`user_id`,`taskid`,`video_name`,`video_length`,`id`,`url`,`state`,`video_desc`) values (30,1688473340729,'9cc998ea-5d3b-4414-839b-54266c551f533.mp4','151','8692e7d6-04ec-44d4-aaa9-2f46983f629b','/video/playVideo?taskid=1688473340729&filename=9cc998ea-5d3b-4414-839b-54266c551f533.mp4&userid=30',1,'第三集'),(30,1689298149194,'206e77f4-85a3-42d3-8236-f8d66f1197453.mp4','15','96aa0f30-d19c-4650-968d-59f144ef2ff8','/video/playVideo?taskid=1689298149194&filename=206e77f4-85a3-42d3-8236-f8d66f1197453.mp4&userid=30',1,'vue'),(30,1688382597006,'4cf45455-555c-42bb-9406-c0d39d1fafb02.mp4','173','e23498dc-6e55-47b2-8cb3-9d9ed9f00e0f','/video/playVideo?taskid=1688382597006&filename=4cf45455-555c-42bb-9406-c0d39d1fafb02.mp4&userid=30',1,'测试第二集'),(30,1688382597006,'44791d6f-6a6f-446b-b4e7-e64befe6948310.mp4','170','fd1988b3-1650-4b51-b90e-006ae760fbad','/video/playVideo?taskid=1688382597006&filename=44791d6f-6a6f-446b-b4e7-e64befe6948310.mp4&userid=30',1,'nodejs');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
