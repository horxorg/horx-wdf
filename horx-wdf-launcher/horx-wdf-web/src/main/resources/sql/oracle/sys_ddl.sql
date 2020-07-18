/*==============================================================*/
/* DBMS name:      ORACLE                                       */
/*==============================================================*/


create sequence seq_wdf_access_log;

create sequence seq_wdf_data_auth;

create sequence seq_wdf_data_auth_dtl;

create sequence seq_wdf_data_log;

create sequence seq_wdf_data_perm
start with 1000;

create sequence seq_wdf_dict
start with 1000;

create sequence seq_wdf_dict_item
start with 10000;

create sequence seq_wdf_menu
start with 1000;

create sequence seq_wdf_menu_auth;

create sequence seq_wdf_org;

create sequence seq_wdf_role;

create sequence seq_wdf_session;

create sequence seq_wdf_session_attr;

create sequence seq_wdf_user
start with 2;

create sequence seq_wdf_user_role;

/*==============================================================*/
/* Table: wdf_access_log                                      */
/*==============================================================*/
create table wdf_access_log
(
   id                 NUMBER               not null,
   user_id            NUMBER,
   org_id             NUMBER,
   trace_id           VARCHAR2(50),
   environment        VARCHAR2(30),
   url                VARCHAR2(100),
   http_method        VARCHAR2(10),
   permission_code    VARCHAR2(100),
   role_id            VARCHAR2(30),
   client_ip          VARCHAR2(50),
   user_agent         VARCHAR2(200),
   class_name         VARCHAR2(200),
   method_name        VARCHAR2(50),
   server_ip          VARCHAR2(50),
   start_time         TIMESTAMP,
   end_time           TIMESTAMP,
   duration           NUMBER,
   is_success         NUMBER(1),
   constraint pk_wdf_access_log primary key (id)
);

comment on table wdf_access_log is
'访问日志';

comment on column wdf_access_log.id is
'ID';

comment on column wdf_access_log.user_id is
'用户ID';

comment on column wdf_access_log.org_id is
'用户所属机构ID';

comment on column wdf_access_log.trace_id is
'跟踪ID';

comment on column wdf_access_log.environment is
'环境';

comment on column wdf_access_log.url is
'URL';

comment on column wdf_access_log.http_method is
'http方法';

comment on column wdf_access_log.permission_code is
'权限项';

comment on column wdf_access_log.role_id is
'角色';

comment on column wdf_access_log.client_ip is
'客户端IP';

comment on column wdf_access_log.user_agent is
'userAgent';

comment on column wdf_access_log.class_name is
'类名';

comment on column wdf_access_log.method_name is
'方法名';

comment on column wdf_access_log.server_ip is
'服务器IP';

comment on column wdf_access_log.start_time is
'开始时间';

comment on column wdf_access_log.end_time is
'结束时间';

comment on column wdf_access_log.duration is
'耗时';

comment on column wdf_access_log.is_success is
'是否访问成功';

/*==============================================================*/
/* Index: idx_wdf_access_log_trace_id                         */
/*==============================================================*/
create index idx_wdf_access_log_trace_id on wdf_access_log (
   trace_id ASC
);

/*==============================================================*/
/* Index: idx_wdf_access_log_start_time                       */
/*==============================================================*/
create index idx_wdf_access_log_start_time on wdf_access_log (
   start_time ASC
);

/*==============================================================*/
/* Table: wdf_data_auth                                       */
/*==============================================================*/
create table wdf_data_auth
(
   id                 NUMBER               not null,
   data_perm_id       NUMBER               not null,
   obj_type           VARCHAR2(30)         not null,
   obj_id             NUMBER               not null,
   auth_type          VARCHAR2(30)         not null,
   dtl_count          NUMBER               default 0,
   is_enabled         NUMBER(1)            default 1,
   remarks            NVARCHAR2(500),
   is_deleted         NUMBER(1)            default 0,
   del_rid            NUMBER               default 0,
   create_time        DATE,
   create_user_id     NUMBER,
   modify_time        DATE,
   modify_user_id     NUMBER,
   trace_id           VARCHAR2(50),
   constraint pk_wdf_data_grant primary key (id)
);

comment on table wdf_data_auth is
'数据权限授权';

comment on column wdf_data_auth.id is
'ID';

comment on column wdf_data_auth.data_perm_id is
'数据权限定义ID';

comment on column wdf_data_auth.obj_type is
'授予对象类型';

comment on column wdf_data_auth.obj_id is
'授予对象ID';

comment on column wdf_data_auth.auth_type is
'授权类型值';

comment on column wdf_data_auth.dtl_count is
'授权明细项总数';

comment on column wdf_data_auth.is_enabled is
'是否有效';

comment on column wdf_data_auth.remarks is
'备注';

comment on column wdf_data_auth.is_deleted is
'是否删除';

comment on column wdf_data_auth.del_rid is
'删除辅助主键';

comment on column wdf_data_auth.create_time is
'创建时间';

comment on column wdf_data_auth.create_user_id is
'创建用户ID';

comment on column wdf_data_auth.modify_time is
'最后修改时间';

comment on column wdf_data_auth.modify_user_id is
'最后修改用户ID';

comment on column wdf_data_auth.trace_id is
'跟踪ID';

alter table wdf_data_auth
   add constraint uk_wdf_data_auth unique (data_perm_id, obj_type, obj_id, del_rid);

/*==============================================================*/
/* Table: wdf_data_auth_dtl                                   */
/*==============================================================*/
create table wdf_data_auth_dtl
(
   id                 NUMBER               not null,
   auth_id            NUMBER               not null,
   auth_value         VARCHAR2(50)         not null,
   checked_type       NUMBER(1)            default 1 not null,
   is_deleted         NUMBER(1)            default 0,
   create_time        DATE,
   create_user_id     NUMBER,
   modify_time        DATE,
   modify_user_id     NUMBER,
   trace_id           VARCHAR2(50),
   constraint pk_wdf_data_auth_dtl primary key (id)
);

comment on table wdf_data_auth_dtl is
'数据权限授权明细';

comment on column wdf_data_auth_dtl.id is
'ID';

comment on column wdf_data_auth_dtl.auth_id is
'授权ID';

comment on column wdf_data_auth_dtl.auth_value is
'授权项值';

comment on column wdf_data_auth_dtl.checked_type is
'节点选中类型（1本节点，2本级及子项）';

comment on column wdf_data_auth_dtl.is_deleted is
'是否删除';

comment on column wdf_data_auth_dtl.create_time is
'创建时间';

comment on column wdf_data_auth_dtl.create_user_id is
'创建用户ID';

comment on column wdf_data_auth_dtl.modify_time is
'最后修改时间';

comment on column wdf_data_auth_dtl.modify_user_id is
'最后修改用户ID';

comment on column wdf_data_auth_dtl.trace_id is
'跟踪ID';

/*==============================================================*/
/* Table: wdf_data_log                                        */
/*==============================================================*/
create table wdf_data_log
(
   id                 NUMBER               not null,
   user_id            NUMBER,
   org_id             NUMBER,
   trace_id           VARCHAR2(50),
   biz_type           VARCHAR2(10),
   biz_name           NVARCHAR2(50),
   operation_type     VARCHAR2(20),
   data               CLOB,
   description        NVARCHAR2(100),
   class_name         VARCHAR2(200),
   method_name        VARCHAR2(50),
   server_ip          VARCHAR2(50),
   start_time         TIMESTAMP,
   end_time           TIMESTAMP,
   duration           NUMBER,
   constraint PK_WDF_DATA_LOG primary key (id)
);

comment on table wdf_data_log is
'数据操作日志';

comment on column wdf_data_log.id is
'ID';

comment on column wdf_data_log.user_id is
'用户ID';

comment on column wdf_data_log.org_id is
'用户所属机构ID';

comment on column wdf_data_log.trace_id is
'跟踪ID';

comment on column wdf_data_log.biz_type is
'业务类型';

comment on column wdf_data_log.biz_name is
'业务名称';

comment on column wdf_data_log.operation_type is
'操作类型';

comment on column wdf_data_log.data is
'数据';

comment on column wdf_data_log.description is
'描述';

comment on column wdf_data_log.class_name is
'类名';

comment on column wdf_data_log.method_name is
'方法名';

comment on column wdf_data_log.server_ip is
'服务器IP';

comment on column wdf_data_log.start_time is
'开始时间';

comment on column wdf_data_log.end_time is
'结束时间';

comment on column wdf_data_log.duration is
'耗时';

/*==============================================================*/
/* Index: idx_wdf_data_log_trace_id                           */
/*==============================================================*/
create index idx_wdf_data_log_trace_id on wdf_data_log (
   trace_id ASC
);

/*==============================================================*/
/* Index: idx_wdf_data_log_start_time                         */
/*==============================================================*/
create index idx_wdf_data_log_start_time on wdf_data_log (
   start_time ASC
);

/*==============================================================*/
/* Table: wdf_data_perm                                       */
/*==============================================================*/
create table wdf_data_perm
(
   id                 NUMBER               not null,
   code               VARCHAR2(30)         not null,
   name               NVARCHAR2(100)       not null,
   obj_type           VARCHAR2(30),
   obj_id             NUMBER,
   is_enabled         NUMBER(1)            default 1,
   display_seq        NUMBER,
   remarks            NVARCHAR2(500),
   is_deleted         NUMBER(1)            default 0,
   del_rid            NUMBER               default 0,
   create_time        DATE,
   create_user_id     NUMBER,
   modify_time        DATE,
   modify_user_id     NUMBER,
   trace_id           VARCHAR2(50),
   constraint pk_wdf_data_perm primary key (id)
);

comment on table wdf_data_perm is
'数据权限定义';

comment on column wdf_data_perm.id is
'ID';

comment on column wdf_data_perm.code is
'编码';

comment on column wdf_data_perm.name is
'名称';

comment on column wdf_data_perm.obj_type is
'权限对象类型';

comment on column wdf_data_perm.obj_id is
'权限对象ID';

comment on column wdf_data_perm.is_enabled is
'是否有效';

comment on column wdf_data_perm.display_seq is
'显示顺序';

comment on column wdf_data_perm.remarks is
'备注';

comment on column wdf_data_perm.is_deleted is
'是否删除';

comment on column wdf_data_perm.del_rid is
'删除辅助主键';

comment on column wdf_data_perm.create_time is
'创建时间';

comment on column wdf_data_perm.create_user_id is
'创建用户ID';

comment on column wdf_data_perm.modify_time is
'最后修改时间';

comment on column wdf_data_perm.modify_user_id is
'最后修改用户ID';

comment on column wdf_data_perm.trace_id is
'跟踪ID';

alter table wdf_data_perm
   add constraint uk_wdf_data_perm_code unique (code, del_rid);

/*==============================================================*/
/* Table: wdf_dict                                            */
/*==============================================================*/
create table wdf_dict
(
   id                 NUMBER               not null,
   code               VARCHAR2(30)         not null,
   name               NVARCHAR2(100)       not null,
   biz_type           VARCHAR2(30)         not null,
   is_tree_data       NUMBER(1)            default 0 not null,
   remarks            NVARCHAR2(500),
   is_deleted         NUMBER(1)            default 0,
   del_rid            NUMBER               default 0,
   create_time        DATE,
   create_user_id     NUMBER,
   modify_time        DATE,
   modify_user_id     NUMBER,
   trace_id           VARCHAR2(50),
   constraint pk_wdf_dict primary key (id)
);

comment on table wdf_dict is
'字典';

comment on column wdf_dict.id is
'ID';

comment on column wdf_dict.code is
'编码';

comment on column wdf_dict.name is
'名称';

comment on column wdf_dict.biz_type is
'业务类型';

comment on column wdf_dict.is_tree_data is
'是否树形结构';

comment on column wdf_dict.remarks is
'备注';

comment on column wdf_dict.is_deleted is
'是否删除';

comment on column wdf_dict.del_rid is
'删除辅助主键';

comment on column wdf_dict.create_time is
'创建时间';

comment on column wdf_dict.create_user_id is
'创建用户ID';

comment on column wdf_dict.modify_time is
'最后修改时间';

comment on column wdf_dict.modify_user_id is
'最后修改用户ID';

comment on column wdf_dict.trace_id is
'跟踪ID';

alter table wdf_dict
   add constraint uk_wdf_dict_code unique (code, del_rid);

/*==============================================================*/
/* Table: wdf_dict_item                                       */
/*==============================================================*/
create table wdf_dict_item
(
   id                 NUMBER               not null,
   dict_id            NUMBER               not null,
   parent_id          NUMBER,
   code               VARCHAR2(30)         not null,
   name               NVARCHAR2(100)       not null,
   full_code          VARCHAR2(200),
   simple_code        VARCHAR2(100)        default '0',
   level_num          NUMBER(3),
   level_code         VARCHAR2(200),
   is_enabled         NUMBER(1)            default 1,
   display_seq        NUMBER,
   remarks            NVARCHAR2(500),
   is_deleted         NUMBER(1)            default 0,
   del_rid            NUMBER               default 0,
   create_time        DATE,
   create_user_id     NUMBER,
   modify_time        DATE,
   modify_user_id     NUMBER,
   trace_id           VARCHAR2(50),
   constraint pk_wdf_dict_item primary key (id)
);

comment on table wdf_dict_item is
'字典项';

comment on column wdf_dict_item.id is
'ID';

comment on column wdf_dict_item.dict_id is
'字典ID';

comment on column wdf_dict_item.parent_id is
'父ID';

comment on column wdf_dict_item.code is
'编码';

comment on column wdf_dict_item.name is
'名称';

comment on column wdf_dict_item.full_code is
'完整编码';

comment on column wdf_dict_item.simple_code is
'简化码';

comment on column wdf_dict_item.level_num is
'层级';

comment on column wdf_dict_item.level_code is
'层级编码';

comment on column wdf_dict_item.is_enabled is
'是否有效';

comment on column wdf_dict_item.display_seq is
'显示顺序';

comment on column wdf_dict_item.remarks is
'备注';

comment on column wdf_dict_item.is_deleted is
'是否删除';

comment on column wdf_dict_item.del_rid is
'删除辅助主键';

comment on column wdf_dict_item.create_time is
'创建时间';

comment on column wdf_dict_item.create_user_id is
'创建用户ID';

comment on column wdf_dict_item.modify_time is
'最后修改时间';

comment on column wdf_dict_item.modify_user_id is
'最后修改用户ID';

comment on column wdf_dict_item.trace_id is
'跟踪ID';

alter table wdf_dict_item
   add constraint uk_wdf_dict_item_code unique (dict_id, code, del_rid);

/*==============================================================*/
/* Table: wdf_menu                                            */
/*==============================================================*/
create table wdf_menu
(
   id                 NUMBER               not null,
   parent_id          NUMBER,
   code               VARCHAR2(100),
   name               NVARCHAR2(100),
   type               VARCHAR2(2),
   icon_type          VARCHAR2(2),
   icon_content       VARCHAR2(100),
   url                VARCHAR2(100),
   permission_code    VARCHAR2(200),
   level_num          NUMBER(3),
   level_code         VARCHAR2(200),
   is_visible         NUMBER(1)            default 1,
   is_enabled         NUMBER(1)            default 1,
   display_seq        NUMBER,
   is_built_in        NUMBER(1)            default 0,
   remarks            NVARCHAR2(500),
   is_deleted         NUMBER(1)            default 0,
   del_rid            NUMBER               default 0,
   create_time        DATE,
   create_user_id     NUMBER,
   modify_time        DATE,
   modify_user_id     NUMBER,
   trace_id           VARCHAR2(50),
   constraint pk_wdf_menu primary key (id)
);

comment on table wdf_menu is
'菜单';

comment on column wdf_menu.id is
'ID';

comment on column wdf_menu.parent_id is
'父菜单ID';

comment on column wdf_menu.code is
'菜单标识';

comment on column wdf_menu.name is
'名称';

comment on column wdf_menu.type is
'类型';

comment on column wdf_menu.icon_type is
'图标类型';

comment on column wdf_menu.icon_content is
'图标内容';

comment on column wdf_menu.url is
'URL';

comment on column wdf_menu.permission_code is
'权限项';

comment on column wdf_menu.level_num is
'层级';

comment on column wdf_menu.level_code is
'层级编码';

comment on column wdf_menu.is_visible is
'是否可见';

comment on column wdf_menu.is_enabled is
'是否有效';

comment on column wdf_menu.display_seq is
'显示顺序';

comment on column wdf_menu.is_built_in is
'是否系统内置';

comment on column wdf_menu.remarks is
'备注';

comment on column wdf_menu.is_deleted is
'是否删除';

comment on column wdf_menu.del_rid is
'删除辅助主键';

comment on column wdf_menu.create_time is
'创建时间';

comment on column wdf_menu.create_user_id is
'创建用户ID';

comment on column wdf_menu.modify_time is
'最后修改时间';

comment on column wdf_menu.modify_user_id is
'最后修改用户ID';

comment on column wdf_menu.trace_id is
'跟踪ID';

alter table wdf_menu
   add constraint uk_wdf_menu_level_code unique (level_code);

alter table wdf_menu
   add constraint uk_wdf_menu_code unique (code, del_rid);

/*==============================================================*/
/* Index: idx_wdf_menu_parent_id                              */
/*==============================================================*/
create index idx_wdf_menu_parent_id on wdf_menu (
   parent_id ASC
);

/*==============================================================*/
/* Table: wdf_menu_auth                                       */
/*==============================================================*/
create table wdf_menu_auth
(
   id                 NUMBER               not null,
   obj_type           VARCHAR2(30)         not null,
   obj_id             NUMBER               not null,
   menu_id            NUMBER               not null,
   checked_type       NUMBER(1)            default 1 not null,
   is_deleted         NUMBER(1)            default 0,
   create_time        DATE,
   create_user_id     NUMBER,
   modify_time        DATE,
   modify_user_id     NUMBER,
   trace_id           VARCHAR2(50),
   constraint pk_wdf_menu_auth primary key (id)
);

comment on table wdf_menu_auth is
'菜单授权';

comment on column wdf_menu_auth.id is
'ID';

comment on column wdf_menu_auth.obj_type is
'授予对象类型';

comment on column wdf_menu_auth.obj_id is
'授予对象ID';

comment on column wdf_menu_auth.menu_id is
'菜单ID';

comment on column wdf_menu_auth.checked_type is
'节点选中类型（1本节点，2本级及子项）';

comment on column wdf_menu_auth.is_deleted is
'是否删除';

comment on column wdf_menu_auth.create_time is
'创建时间';

comment on column wdf_menu_auth.create_user_id is
'创建用户ID';

comment on column wdf_menu_auth.modify_time is
'最后修改时间';

comment on column wdf_menu_auth.modify_user_id is
'最后修改用户ID';

comment on column wdf_menu_auth.trace_id is
'跟踪ID';

/*==============================================================*/
/* Index: idx_wdf_role_menu                                   */
/*==============================================================*/
create index idx_wdf_role_menu on wdf_menu_auth (
   obj_id ASC,
   menu_id ASC
);

/*==============================================================*/
/* Table: wdf_org                                             */
/*==============================================================*/
create table wdf_org
(
   id                 NUMBER               not null,
   parent_id          NUMBER,
   code               VARCHAR2(30),
   name               NVARCHAR2(100),
   type               VARCHAR2(2),
   level_num          NUMBER(3),
   level_code         VARCHAR2(200),
   is_enabled         NUMBER(1)            default 1,
   establish_date     DATE,
   cancel_date        DATE,
   display_seq        NUMBER,
   remarks            NVARCHAR2(500),
   is_deleted         NUMBER(1)            default 0,
   del_rid            NUMBER               default 0,
   create_time        DATE,
   create_user_id     NUMBER,
   create_org_id      NUMBER,
   modify_time        DATE,
   modify_user_id     NUMBER,
   trace_id           VARCHAR2(50),
   constraint pk_wdf_org primary key (id)
);

comment on table wdf_org is
'机构';

comment on column wdf_org.id is
'ID';

comment on column wdf_org.parent_id is
'父机构ID';

comment on column wdf_org.code is
'编码';

comment on column wdf_org.name is
'名称';

comment on column wdf_org.type is
'类型';

comment on column wdf_org.level_num is
'层级';

comment on column wdf_org.level_code is
'层级编码';

comment on column wdf_org.is_enabled is
'是否有效';

comment on column wdf_org.establish_date is
'成立日期';

comment on column wdf_org.cancel_date is
'撤销日期';

comment on column wdf_org.display_seq is
'显示顺序';

comment on column wdf_org.remarks is
'备注';

comment on column wdf_org.is_deleted is
'是否删除';

comment on column wdf_org.del_rid is
'删除辅助主键';

comment on column wdf_org.create_time is
'创建时间';

comment on column wdf_org.create_user_id is
'创建用户ID';

comment on column wdf_org.create_org_id is
'创建用户所属机构ID';

comment on column wdf_org.modify_time is
'最后修改时间';

comment on column wdf_org.modify_user_id is
'最后修改用户ID';

comment on column wdf_org.trace_id is
'跟踪ID';

alter table wdf_org
   add constraint uk_wdf_org_level_code unique (level_code);

alter table wdf_org
   add constraint uk_wdf_org_code unique (code, del_rid);

/*==============================================================*/
/* Index: idx_wdf_org_parent_id                               */
/*==============================================================*/
create index idx_wdf_org_parent_id on wdf_org (
   parent_id ASC
);

/*==============================================================*/
/* Table: wdf_role                                            */
/*==============================================================*/
create table wdf_role
(
   id                 NUMBER               not null,
   code               VARCHAR2(30),
   name               NVARCHAR2(100),
   org_id             NUMBER,
   is_sub_usable      NUMBER(1)            default 0,
   is_enabled         NUMBER(1)            default 1,
   remarks            NVARCHAR2(500),
   is_deleted         NUMBER(1)            default 0,
   del_rid            NUMBER               default 0,
   create_time        DATE,
   create_user_id     NUMBER,
   create_org_id      NUMBER,
   modify_time        DATE,
   modify_user_id     NUMBER,
   trace_id           VARCHAR2(50),
   constraint pk_wdf_role primary key (id)
);

comment on table wdf_role is
'角色';

comment on column wdf_role.id is
'ID';

comment on column wdf_role.code is
'编码';

comment on column wdf_role.name is
'名称';

comment on column wdf_role.org_id is
'所属机构ID';

comment on column wdf_role.is_sub_usable is
'下级授权可用';

comment on column wdf_role.is_enabled is
'是否有效';

comment on column wdf_role.remarks is
'备注';

comment on column wdf_role.is_deleted is
'是否删除';

comment on column wdf_role.del_rid is
'删除辅助主键';

comment on column wdf_role.create_time is
'创建时间';

comment on column wdf_role.create_user_id is
'创建用户ID';

comment on column wdf_role.create_org_id is
'创建用户所属机构ID';

comment on column wdf_role.modify_time is
'最后修改时间';

comment on column wdf_role.modify_user_id is
'最后修改用户ID';

comment on column wdf_role.trace_id is
'跟踪ID';

alter table wdf_role
   add constraint uk_wdf_role_code unique (code, del_rid);

/*==============================================================*/
/* Table: wdf_session                                         */
/*==============================================================*/
create table wdf_session
(
   id                 NUMBER               not null,
   session_key        VARCHAR2(50)         not null,
   create_time        DATE                 not null,
   last_access_time   DATE                 not null,
   inactive_interval  NUMBER               not null,
   expired_time       DATE                 not null,
   client_ip          VARCHAR2(50),
   user_id            NUMBER,
   constraint pk_wdf_session primary key (id)
);

comment on table wdf_session is
'会话';

comment on column wdf_session.id is
'ID';

comment on column wdf_session.session_key is
'会话Key';

comment on column wdf_session.create_time is
'创建时间';

comment on column wdf_session.last_access_time is
'最后访问系统时间';

comment on column wdf_session.inactive_interval is
'会话失效间隔';

comment on column wdf_session.expired_time is
'会话失效时间';

comment on column wdf_session.client_ip is
'客户端IP';

comment on column wdf_session.user_id is
'用户ID';

alter table wdf_session
   add constraint uk_wdf_session_key unique (session_key);

/*==============================================================*/
/* Index: idx_wdf_session_user_id                             */
/*==============================================================*/
create index idx_wdf_session_user_id on wdf_session (
   user_id ASC
);

/*==============================================================*/
/* Table: wdf_session_attr                                    */
/*==============================================================*/
create table wdf_session_attr
(
   id                 NUMBER               not null,
   session_id         NUMBER               not null,
   attr_key           VARCHAR2(50)         not null,
   attr_type          VARCHAR2(50)         not null,
   attr_value         NVARCHAR2(500)       not null,
   constraint pk_wdf_session_attr primary key (id)
);

comment on table wdf_session_attr is
'会话属性';

comment on column wdf_session_attr.id is
'ID';

comment on column wdf_session_attr.session_id is
'会话表ID';

comment on column wdf_session_attr.attr_key is
'属性名';

comment on column wdf_session_attr.attr_type is
'属性类型';

comment on column wdf_session_attr.attr_value is
'属性值';

alter table wdf_session_attr
   add constraint uk_wdf_session_attr unique (session_id, attr_key);

/*==============================================================*/
/* Table: wdf_user                                            */
/*==============================================================*/
create table wdf_user
(
   id                 NUMBER               not null,
   name               NVARCHAR2(30)        not null,
   username           VARCHAR2(30),
   password           VARCHAR2(128),
   pwd_salt           VARCHAR2(50),
   pwd_strength       NUMBER(1),
   pwd_encode_type    VARCHAR2(20),
   pwd_modify_time    DATE,
   pwd_err_times      NUMBER(2)            default 0,
   pwd_err_lock_time  DATE,
   pwd_err_unlock_time DATE,
   org_id             NUMBER,
   mobile             VARCHAR2(20),
   phone              VARCHAR2(50),
   email              VARCHAR2(30),
   status             VARCHAR2(2),
   last_login_time    DATE,
   last_login_ip      VARCHAR2(20),
   is_built_in        NUMBER(1)            default 0,
   remarks            NVARCHAR2(500),
   is_deleted         NUMBER(1)            default 0,
   del_rid            NUMBER               default 0,
   create_time        DATE,
   create_user_id     NUMBER,
   create_org_id      NUMBER,
   modify_time        DATE,
   modify_user_id     NUMBER,
   trace_id           VARCHAR2(50),
   constraint pk_wdf_user primary key (id)
);

comment on table wdf_user is
'用户';

comment on column wdf_user.id is
'ID';

comment on column wdf_user.name is
'用户姓名';

comment on column wdf_user.username is
'登录名';

comment on column wdf_user.password is
'密码';

comment on column wdf_user.pwd_salt is
'密码盐';

comment on column wdf_user.pwd_strength is
'密码强度';

comment on column wdf_user.pwd_encode_type is
'密码加密方式';

comment on column wdf_user.pwd_modify_time is
'密码最后修改时间';

comment on column wdf_user.pwd_err_times is
'密码错误次数';

comment on column wdf_user.pwd_err_lock_time is
'密码错误锁定时间';

comment on column wdf_user.pwd_err_unlock_time is
'密码错误解锁时间';

comment on column wdf_user.org_id is
'所属机构ID';

comment on column wdf_user.mobile is
'手机号';

comment on column wdf_user.phone is
'固定电话';

comment on column wdf_user.email is
'邮箱';

comment on column wdf_user.status is
'状态';

comment on column wdf_user.last_login_time is
'最后登录时间';

comment on column wdf_user.last_login_ip is
'最后登录IP';

comment on column wdf_user.is_built_in is
'是否系统内置';

comment on column wdf_user.remarks is
'备注';

comment on column wdf_user.is_deleted is
'是否删除';

comment on column wdf_user.del_rid is
'删除辅助主键';

comment on column wdf_user.create_time is
'创建时间';

comment on column wdf_user.create_user_id is
'创建用户ID';

comment on column wdf_user.create_org_id is
'创建用户所属机构ID';

comment on column wdf_user.modify_time is
'最后修改时间';

comment on column wdf_user.modify_user_id is
'最后修改用户ID';

comment on column wdf_user.trace_id is
'跟踪ID';

alter table wdf_user
   add constraint uk_wdf_user_username unique (username, del_rid);

/*==============================================================*/
/* Table: wdf_user_role                                       */
/*==============================================================*/
create table wdf_user_role
(
   id                 NUMBER               not null,
   user_id            NUMBER               not null,
   role_id            NUMBER               not null,
   is_enabled         NUMBER(1)            default 1 not null,
   remarks            NVARCHAR2(500),
   is_deleted         NUMBER(1)            default 0 not null,
   del_rid            NUMBER               default 0 not null,
   create_time        DATE,
   create_user_id     NUMBER,
   modify_time        DATE,
   modify_user_id     NUMBER,
   trace_id           VARCHAR2(50),
   constraint pk_wdf_user_role primary key (id)
);

comment on table wdf_user_role is
'用户-角色';

comment on column wdf_user_role.id is
'ID';

comment on column wdf_user_role.user_id is
'用户ID';

comment on column wdf_user_role.role_id is
'角色ID';

comment on column wdf_user_role.is_enabled is
'是否有效';

comment on column wdf_user_role.remarks is
'备注';

comment on column wdf_user_role.is_deleted is
'是否删除';

comment on column wdf_user_role.del_rid is
'删除辅助主键';

comment on column wdf_user_role.create_time is
'创建时间';

comment on column wdf_user_role.create_user_id is
'创建用户ID';

comment on column wdf_user_role.modify_time is
'最后修改时间';

comment on column wdf_user_role.modify_user_id is
'最后修改用户ID';

comment on column wdf_user_role.trace_id is
'跟踪ID';

alter table wdf_user_role
   add constraint uk_wdf_user_role unique (user_id, role_id, del_rid);

