Jac-aaa编写以及部署测试

1、简介
基于spring boot spring security 的认证，当前项目中采用password jdbc认证模式（其他认证模式参见官网）。
2、环境
Jdk1.8(1.7未测试)。
3、编写运行
由于当前采用spring security jdbc存储，有三个内置表，在搭建环境时需要注意导入。
创建表语句如下
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(250) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(250) NOT NULL COMMENT '客户端ID',
  `resource_ids` varchar(256) DEFAULT NULL COMMENT '资源id',
  `client_secret` varchar(256) DEFAULT NULL COMMENT '客户端秘钥',
  `scope` varchar(256) DEFAULT NULL COMMENT '处理',
  `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT 'authorized 类型',
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL COMMENT 'access_token有效期',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '刷新token时间',
  `additional_information` varchar(4096) DEFAULT NULL COMMENT '备注，附加说明',
  `autoapprove` varchar(256) DEFAULT NULL COMMENT '自动批准',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_user` (
  `USERNAME` varchar(30) NOT NULL,
  `PASSWORD` varchar(64) NOT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `ADDRESS` varchar(100) DEFAULT NULL,
  `AGE` int(11) DEFAULT NULL,
  `EXPIRED` int(11) DEFAULT NULL,
  `DISABLED` int(11) DEFAULT NULL,
  PRIMARY KEY (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*
* 用户属性表
*/
CREATE TABLE SYS_ATTRS (
  USERNAME VARCHAR(30) NOT NULL,
  ATTR_KEY VARCHAR(50) NOT NULL,
  ATTR_VAL     VARCHAR(100) NOT NULL
);

---用户属性表
INSERT INTO SYS_ATTRS VALUES ('admin', 'group', 'ADMIN_ROLE');
INSERT INTO SYS_ATTRS VALUES ('admin', 'group', 'MANAGEMENT_ROLE');
INSERT INTO SYS_ATTRS VALUES ('admin', 'group', 'DEV_ROLE');
INSERT INTO SYS_ATTRS VALUES ('admin', 'school', 'GuangZhou');
INSERT INTO SYS_ATTRS VALUES ('admin', 'school', 'ZhuHai');
INSERT INTO SYS_ATTRS VALUES ('zhangsan', 'group', 'DEV_ROLE');


INSERT INTO `sys_user` VALUES ('admin', '202cb962ac59075b964b07152d234b70', 'huang.wenbin@foxmail.com', '广州天河', 24, 0, 0);
INSERT INTO `sys_user` VALUES ('wangwu', '827ccb0eea8a706c4c34a16891f84e7b', 'wangwu@foxmail.com', '广州番禺', 27, 1, 0);
INSERT INTO `sys_user` VALUES ('zhangsan', '25d55ad283aa400af464c76d713c07ad', 'zhangsan@foxmail.com', '广州越秀', 26, 0, 0);
INSERT INTO `sys_user` VALUES ('zhaosi', '81dc9bdb52d04dc20036dbd8313ed055', 'zhaosi@foxmail.com', '广州海珠', 25, 0, 1);

INSERT INTO `oauth_client_details` VALUES ('clientapp', 'Bearer', '112233', 'snsapi_base', 'password,refresh_token,client_credentials', 'https://www.baidu.com', NULL, 3600, 3700, NULL, NULL);

其中oauth_client_details表需要手动配置client信息（后续需要增加维护页面），其他俩个表用来存储生成的token相关信息

运行打包为正常的springboot项目，不多赘述

扩展认证接口编写参考controller包下面的类

测试 

POST http://localhost:8081/oauth/token HTTP/1.1
Accept: application/json
Content-Type: application/x-www-form-urlencoded
Authorization: Basic clientapp 112233

grant_type=password&username=admin&password=202cb962ac59075b964b07152d234b70&scope=snsapi_base

GET http://localhost:8081/oauth/revoke HTTP/1.1
Authorization: Bearer 90687670-0d95-49ab-941b-a3efc0afe843


GET http://localhost:8081/oauth/profile HTTP/1.1
Authorization: Bearer 90687670-0d95-49ab-941b-a3efc0afe843


POST http://localhost:8081/oauth/token HTTP/1.1
Accept: application/json
Content-Type: application/x-www-form-urlencoded
Authorization: Basic clientapp 112233

grant_type=refresh_token&refresh_token=9b7efe7f-37eb-4de1-adfb-2912d6f65322

POST http://localhost:8081/oauth/token HTTP/1.1
Accept: application/json
Content-Type: application/x-www-form-urlencoded
Authorization: Basic clientapp 112233

grant_type=client_credentials&scope=read_userinfo

