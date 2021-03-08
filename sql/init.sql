create schema if not exists yjg;

drop table if exists yjg.`code`;
CREATE TABLE yjg.`code` (
                            `type` varchar(4) DEFAULT NULL,
                            `type_desc` varchar(16) DEFAULT NULL,
                            `code` varchar(4) DEFAULT NULL,
                            `name` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='code表';

drop table if exists yjg.`log`;
CREATE TABLE yjg.`log` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `operate_user_id` int(11) NOT NULL COMMENT '操作人',
                           `operate_time` datetime NOT NULL COMMENT '操作时间',
                           `operate_api` varchar(128) NOT NULL COMMENT '操作api',
                           `ip` varchar(15) DEFAULT NULL COMMENT 'ip来源',
                           `param` varchar(1024) DEFAULT NULL,
                           `result` int(11) DEFAULT NULL,
                           `response` varchar(1024) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=390 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

drop table if exists yjg.`order`;
create table yjg.`order`
(
    id int auto_increment
        primary key,
    bd_url varchar(1024) not null comment '单子的百度网盘地址',
    bd_secret varchar(4) null comment '单子的百度网盘提取码',
    description varchar(512) null comment '单子的说明',
    c_time datetime not null comment '创建时间',
    c_user_id int not null comment '创建人id',
    status1 varchar(4) not null comment '状态1：有效&无效 select * from code where type=''0002''',
    claim_user_id int null comment '谁接了这个单子',
    claim_time datetime null comment '接单时间',
    e_time datetime null comment '接单人完成单子的时间',
    status2 varchar(4) null comment '状态2：已接单&未接单 0003',
    status3 varchar(4) null comment '状态3：已完成&未完成 0004'
) ENGINE=InnoDB AUTO_INCREMENT=390 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

drop table if exists yjg.`user`;
CREATE TABLE yjg.`user` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `user` varchar(16) NOT NULL,
                            `pwd` varchar(16) NOT NULL,
                            `name` varchar(16) DEFAULT NULL,
                            `role` varchar(16) NOT NULL COMMENT 'select * from code where type=''0001''',
                            `c_time` datetime DEFAULT NULL COMMENT '创建日期',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `user_user_uindex` (`user`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

truncate yjg.user;
insert into yjg.user (id, user, pwd, name, role, c_time)
values  (1, 'gly', 'gly', '管理员1号', '1', '2021-02-28 12:13:14'),
        (2, 'jdy', 'jdy', '接单员1号', '2', '2021-02-28 12:13:14'),
        (3, 'zdy', 'zdy', '做单员1号', '3', '2021-02-28 12:13:14');

truncate yjg.code;
insert into yjg.code (type, type_desc, code, name)
values  ('0001', '角色类型', '1', '管理员'),
        ('0001', '角色类型', '2', '接单员'),
        ('0001', '角色类型', '3', '做单员'),
        ('0002', '单子有效状态', '1', '有效'),
        ('0002', '单子有效状态', '2', '无效'),
        ('0003', '单子接单状态', '1', '已接单'),
        ('0003', '单子接单状态', '2', '未接单'),
        ('0004', '单子完成状态', '1', '已完成'),
        ('0004', '单子完成状态', '2', '未完成');