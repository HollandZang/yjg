drop table if exists code;
CREATE TABLE code (
                          type varchar(4) DEFAULT NULL,
                          type_desc varchar(16) DEFAULT NULL,
                          code varchar(4) DEFAULT NULL,
                          name varchar(256) DEFAULT NULL
);
comment on table code is 'code表';

drop table if exists log;
CREATE TABLE log (
                         id serial NOT NULL,
                         operate_user_id int NOT NULL,
                         operate_time timestamp NOT NULL,
                         operate_api varchar(128) NOT NULL,
                         ip varchar(15) DEFAULT NULL,
                         param varchar(1024) DEFAULT NULL,
                         result int DEFAULT NULL,
                         response varchar(1024) DEFAULT NULL,
                         PRIMARY KEY (id)
);
comment on table log is '操作日志表';
comment on column log.operate_user_id is '操作人';
comment on column log.operate_time is '操作时间';
comment on column log.operate_api is '操作api';
comment on column log.ip is 'ip来源';

drop table if exists "order";
create table "order"
(
    id serial
        primary key,
    bd_url varchar(1024) not null,
    bd_secret varchar(4) null,
    description varchar(512) null,
    c_time timestamp not null,
    c_user_id int not null,
    status1 varchar(4) not null,
    claim_user_id int null,
    claim_time timestamp null,
    e_time timestamp null,
    status2 varchar(4) null,
    status3 varchar(4) null
);
comment on table "order" is '订单表';
comment on column "order".bd_url is '单子的百度网盘地址';
comment on column "order".bd_secret is '单子的百度网盘提取码';
comment on column "order".description is '单子的说明';
comment on column "order".c_time is '创建时间';
comment on column "order".c_user_id is '创建人id';
comment on column "order".status1 is '状态1：有效&无效 select * from code where type=''0002''';
comment on column "order".claim_user_id is '谁接了这个单子';
comment on column "order".claim_time is '接单时间';
comment on column "order".status2 is '状态2：已接单&未接单 0003';
comment on column "order".status3 is '状态3：已完成&未完成 0004';

drop table if exists "user";
CREATE TABLE "user" (
                          id serial NOT NULL,
                          "user" varchar(16) NOT NULL,
                          pwd varchar(16) NOT NULL,
                          name varchar(16) DEFAULT NULL,
                          role varchar(16) NOT NULL,
                          c_time timestamp DEFAULT NULL,
                          PRIMARY KEY (id)
);
create unique index user_uindex on "user" ("user");
comment on table "user" is '订单表';
comment on column "user".role is 'select * from code where type=''0001''';
comment on column "user".c_time is '创建日期';

truncate "user";
insert into "user" (id, "user", pwd, name, role, c_time)
values  (1, 'gly', 'gly', '管理员1号', '1', '2021-02-28 12:13:14'),
        (2, 'jdy', 'jdy', '接单员1号', '2', '2021-02-28 12:13:14'),
        (3, 'zdy', 'zdy', '做单员1号', '3', '2021-02-28 12:13:14');

truncate code;
insert into code (type, type_desc, code, name)
values  ('0001', '角色类型', '1', '管理员'),
        ('0001', '角色类型', '2', '接单员'),
        ('0001', '角色类型', '3', '做单员'),
        ('0002', '单子有效状态', '1', '有效'),
        ('0002', '单子有效状态', '2', '无效'),
        ('0003', '单子接单状态', '1', '已接单'),
        ('0003', '单子接单状态', '2', '未接单'),
        ('0004', '单子完成状态', '1', '已完成'),
        ('0004', '单子完成状态', '2', '未完成');