-- ----------------------------
-- Table structure for auth_resource
-- ----------------------------
DROP TABLE IF EXISTS  auth_resource ;
CREATE TABLE  auth_resource
(
     id           bigint       not null auto_increment comment '资源ID',
     name         varchar(50)  not null comment '资源名称',
     code         varchar(50)  not null comment '资源编码',
     uri          varchar(255) not null comment '访问地址URL',
     type         varchar(20)  comment '类型 资源的类别',
     method       varchar(10)  not null comment '访问方式 GET POST PUT DELETE PATCH',
     status       smallint(4)  not null default 1 comment '状态   1:正常、9：过滤(保护排除)',
     description  varchar(255) comment '资源描述',
     gmt_create   timestamp    default current_timestamp comment '创建时间',
     gmt_update   datetime     default current_timestamp on update current_timestamp comment '更新时间',
     primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS  auth_role ;
CREATE TABLE  auth_role
(
     id           bigint       not null auto_increment comment '角色ID',
     name         varchar(50)  not null comment '角色名称',
     code         varchar(50)  not null comment '角色编码',
     status       smallint(4)  not null default 1 comment '状态   1:正常、9：禁用',
     description  varchar(255) comment '角色描述',
     gmt_create   timestamp    default current_timestamp comment '创建时间',
     gmt_update   datetime     default current_timestamp on update current_timestamp comment '更新时间',
     primary key ( id )
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auth_role_resource_bind
-- ----------------------------
DROP TABLE IF EXISTS auth_role_resource_bind;
CREATE TABLE auth_role_resource_bind
(
    id          bigint not null auto_increment comment '主键ID',
    role_id     bigint not null comment '角色ID',
    resource_id bigint not null comment '资源ID',
    gmt_create   timestamp    default current_timestamp comment '创建时间',
    gmt_update   datetime     default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key unique_bind (role_id, resource_id)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auth_user
-- ----------------------------
DROP TABLE IF EXISTS  auth_user ;
CREATE TABLE auth_user
(
    id           bigint      not null auto_increment comment '主键ID',
    username     varchar(50) not null comment '用户名(nick_name)',
    password     varchar(50) not null comment '密码(MD5(密码+盐))',
    salt         varchar(20) comment '盐',
    avatar       varchar(100) comment '头像',
    phone        varchar(20) comment '电话号码(唯一)',
    email        varchar(50) comment '邮件地址(唯一)',
    sex          tinyint(4) comment '性别(1.男 2.女)',
    status       tinyint(4)  not null default 1 comment '账户状态(1.正常 2.锁定 3.删除 4.非法)',
    create_where tinyint(4) comment '创建来源(1.web 2.android 3.ios 4.win 5.mac 6.linux)',
    gmt_create   timestamp    default current_timestamp comment '创建时间',
    gmt_update   datetime     default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique (username, phone, email)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for auth_user_role_bind
-- ----------------------------
DROP TABLE IF EXISTS  auth_user_role_bind ;
CREATE TABLE auth_user_role_bind
(
    id         bigint not null auto_increment comment '主键ID',
    user_id    bigint not null comment '用户ID',
    role_id    bigint not null comment '角色ID',
    gmt_create   timestamp    default current_timestamp comment '创建时间',
    gmt_update   datetime     default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key unique_bind (user_id, role_id)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;