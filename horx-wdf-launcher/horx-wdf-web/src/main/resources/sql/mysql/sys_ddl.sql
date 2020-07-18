/*==============================================================*/
/* DBMS name:      MySQL                                        */
/*==============================================================*/


/*==============================================================*/
/* Table: wdf_access_log                                        */
/*==============================================================*/
create table wdf_access_log
(
   id                   bigint not null auto_increment comment 'ID',
   user_id              bigint comment '用户ID',
   org_id               bigint comment '用户所属机构ID',
   trace_id             varchar(50) comment '跟踪ID',
   environment          varchar(30) comment '环境',
   url                  varchar(100) comment 'URL',
   http_method          varchar(10) comment 'http方法',
   permission_code      varchar(100) comment '权限项',
   role_id              varchar(30) comment '角色',
   client_ip            varchar(50) comment '客户端IP',
   user_agent           varchar(200) comment 'userAgent',
   class_name           varchar(200) comment '类名',
   method_name          varchar(50) comment '方法名',
   server_ip            varchar(50) comment '服务器IP',
   start_time           datetime(6) comment '开始时间',
   end_time             datetime(6) comment '结束时间',
   duration             bigint comment '耗时',
   is_success           decimal(1) comment '是否访问成功',
   primary key (id)
)
engine = InnoDB comment '访问日志';

/*==============================================================*/
/* Index: idx_wdf_access_log_trace_id                           */
/*==============================================================*/
create index idx_wdf_access_log_trace_id on wdf_access_log
(
   trace_id
);

/*==============================================================*/
/* Index: idx_wdf_access_log_start_time                         */
/*==============================================================*/
create index idx_wdf_access_log_start_time on wdf_access_log
(
   start_time
);

/*==============================================================*/
/* Table: wdf_data_auth                                         */
/*==============================================================*/
create table wdf_data_auth
(
   id                   bigint not null auto_increment comment 'ID',
   data_perm_id         bigint not null comment '数据权限定义ID',
   obj_type             varchar(30) not null comment '授予对象类型',
   obj_id               bigint not null comment '授予对象ID',
   auth_type            varchar(30) not null comment '授权类型值',
   dtl_count            int default 0 comment '授权明细项总数',
   is_enabled           decimal(1) default 1 comment '是否有效',
   remarks              varchar(500) comment '备注',
   is_deleted           decimal(1) default 0 comment '是否删除',
   del_rid              bigint default 0 comment '删除辅助主键',
   create_time          datetime(6) comment '创建时间',
   create_user_id       bigint comment '创建用户ID',
   modify_time          datetime(6) comment '最后修改时间',
   modify_user_id       bigint comment '最后修改用户ID',
   trace_id             varchar(50) comment '跟踪ID',
   primary key (id),
   unique key uk_wdf_data_auth (data_perm_id, obj_type, obj_id, del_rid)
)
engine = InnoDB comment '数据权限授权';

/*==============================================================*/
/* Table: wdf_data_auth_dtl                                     */
/*==============================================================*/
create table wdf_data_auth_dtl
(
   id                   bigint not null auto_increment comment 'ID',
   auth_id              bigint not null comment '授权ID',
   auth_value           varchar(50) not null comment '授权项值',
   checked_type         decimal(1) not null default 1 comment '节点选中类型（1本节点，2本级及子项）',
   is_deleted           decimal(1) default 0 comment '是否删除',
   create_time          datetime(6) comment '创建时间',
   create_user_id       bigint comment '创建用户ID',
   modify_time          datetime(6) comment '最后修改时间',
   modify_user_id       bigint comment '最后修改用户ID',
   trace_id             varchar(50) comment '跟踪ID',
   primary key (id)
)
engine = InnoDB comment '数据权限授权明细';

/*==============================================================*/
/* Table: wdf_data_log                                          */
/*==============================================================*/
create table wdf_data_log
(
   id                   bigint not null auto_increment comment 'ID',
   user_id              bigint comment '用户ID',
   org_id               bigint comment '用户所属机构ID',
   trace_id             varchar(50) comment '跟踪ID',
   biz_type             varchar(10) comment '业务类型',
   biz_name             varchar(50) comment '业务名称',
   operation_type       varchar(20) comment '操作类型',
   data                 text comment '数据',
   description          varchar(100) comment '描述',
   class_name           varchar(200) comment '类名',
   method_name          varchar(50) comment '方法名',
   server_ip            varchar(50) comment '服务器IP',
   start_time           datetime(6) comment '开始时间',
   end_time             datetime(6) comment '结束时间',
   duration             bigint comment '耗时',
   primary key (id)
)
engine = InnoDB comment '数据操作日志';

/*==============================================================*/
/* Index: idx_wdf_data_log_trace_id                             */
/*==============================================================*/
create index idx_wdf_data_log_trace_id on wdf_data_log
(
   trace_id
);

/*==============================================================*/
/* Index: idx_wdf_data_log_start_time                           */
/*==============================================================*/
create index idx_wdf_data_log_start_time on wdf_data_log
(
   start_time
);

/*==============================================================*/
/* Table: wdf_data_perm                                         */
/*==============================================================*/
create table wdf_data_perm
(
   id                   bigint not null auto_increment comment 'ID',
   code                 varchar(30) not null comment '编码',
   name                 varchar(100) not null comment '名称',
   obj_type             varchar(30) comment '权限对象类型',
   obj_id               bigint comment '权限对象ID',
   is_enabled           decimal(1) default 1 comment '是否有效',
   display_seq          bigint comment '显示顺序',
   remarks              varchar(500) comment '备注',
   is_deleted           decimal(1) default 0 comment '是否删除',
   del_rid              bigint default 0 comment '删除辅助主键',
   create_time          datetime(6) comment '创建时间',
   create_user_id       bigint comment '创建用户ID',
   modify_time          datetime(6) comment '最后修改时间',
   modify_user_id       bigint comment '最后修改用户ID',
   trace_id             varchar(50) comment '跟踪ID',
   primary key (id),
   unique key uk_wdf_data_perm_code (code, del_rid)
)
engine = InnoDB comment '数据权限定义';

/*==============================================================*/
/* Table: wdf_dict                                              */
/*==============================================================*/
create table wdf_dict
(
   id                   bigint not null auto_increment comment 'ID',
   code                 varchar(30) not null comment '编码',
   name                 varchar(100) not null comment '名称',
   biz_type             varchar(30) not null comment '业务类型',
   is_tree_data         decimal(1) not null default 0 comment '是否树形结构',
   remarks              varchar(500) comment '备注',
   is_deleted           decimal(1) default 0 comment '是否删除',
   del_rid              bigint default 0 comment '删除辅助主键',
   create_time          datetime(6) comment '创建时间',
   create_user_id       bigint comment '创建用户ID',
   modify_time          datetime(6) comment '最后修改时间',
   modify_user_id       bigint comment '最后修改用户ID',
   trace_id             varchar(50) comment '跟踪ID',
   primary key (id),
   unique key uk_wdf_dict_code (code, del_rid)
)
engine = InnoDB comment '字典';

/*==============================================================*/
/* Table: wdf_dict_item                                         */
/*==============================================================*/
create table wdf_dict_item
(
   id                   bigint not null auto_increment comment 'ID',
   dict_id              bigint not null comment '字典ID',
   parent_id            bigint comment '父ID',
   code                 varchar(30) not null comment '编码',
   name                 varchar(100) not null comment '名称',
   full_code            varchar(200) comment '完整编码',
   simple_code          varchar(100) default '0' comment '简化码',
   level_num            decimal(3) comment '层级',
   level_code           varchar(200) comment '层级编码',
   is_enabled           decimal(1) default 1 comment '是否有效',
   display_seq          bigint comment '显示顺序',
   remarks              varchar(500) comment '备注',
   is_deleted           decimal(1) default 0 comment '是否删除',
   del_rid              bigint default 0 comment '删除辅助主键',
   create_time          datetime(6) comment '创建时间',
   create_user_id       bigint comment '创建用户ID',
   modify_time          datetime(6) comment '最后修改时间',
   modify_user_id       bigint comment '最后修改用户ID',
   trace_id             varchar(50) comment '跟踪ID',
   primary key (id),
   unique key uk_wdf_dict_item_code (dict_id, code, del_rid)
)
engine = InnoDB comment '字典项';

/*==============================================================*/
/* Table: wdf_menu                                              */
/*==============================================================*/
create table wdf_menu
(
   id                   bigint not null auto_increment comment 'ID',
   parent_id            bigint comment '父菜单ID',
   code                 varchar(100) comment '菜单标识',
   name                 varchar(100) comment '名称',
   type                 varchar(2) comment '类型',
   icon_type            varchar(2) comment '图标类型',
   icon_content         varchar(100) comment '图标内容',
   url                  varchar(100) comment 'URL',
   permission_code      varchar(200) comment '权限项',
   level_num            decimal(3) comment '层级',
   level_code           varchar(200) comment '层级编码',
   is_visible           decimal(1) default 1 comment '是否可见',
   is_enabled           decimal(1) default 1 comment '是否有效',
   display_seq          bigint comment '显示顺序',
   is_built_in          decimal(1) default 0 comment '是否系统内置',
   remarks              varchar(500) comment '备注',
   is_deleted           decimal(1) default 0 comment '是否删除',
   del_rid              bigint default 0 comment '删除辅助主键',
   create_time          datetime(6) comment '创建时间',
   create_user_id       bigint comment '创建用户ID',
   modify_time          datetime(6) comment '最后修改时间',
   modify_user_id       bigint comment '最后修改用户ID',
   trace_id             varchar(50) comment '跟踪ID',
   primary key (id),
   unique key uk_wdf_menu_level_code (level_code),
   unique key uk_wdf_menu_code (code, del_rid)
)
engine = InnoDB comment '菜单';

/*==============================================================*/
/* Index: idx_wdf_menu_parent_id                                */
/*==============================================================*/
create index idx_wdf_menu_parent_id on wdf_menu
(
   parent_id
);

/*==============================================================*/
/* Table: wdf_menu_auth                                         */
/*==============================================================*/
create table wdf_menu_auth
(
   id                   bigint not null auto_increment comment 'ID',
   obj_type             varchar(30) not null comment '授予对象类型',
   obj_id               bigint not null comment '授予对象ID',
   menu_id              bigint not null comment '菜单ID',
   checked_type         decimal(1) not null default 1 comment '节点选中类型（1本节点，2本级及子项）',
   is_deleted           decimal(1) default 0 comment '是否删除',
   create_time          datetime(6) comment '创建时间',
   create_user_id       bigint comment '创建用户ID',
   modify_time          datetime(6) comment '最后修改时间',
   modify_user_id       bigint comment '最后修改用户ID',
   trace_id             varchar(50) comment '跟踪ID',
   primary key (id)
)
engine = InnoDB comment '菜单授权';

/*==============================================================*/
/* Index: idx_wdf_role_menu                                     */
/*==============================================================*/
create index idx_wdf_role_menu on wdf_menu_auth
(
   obj_id,
   menu_id
);

/*==============================================================*/
/* Table: wdf_org                                               */
/*==============================================================*/
create table wdf_org
(
   id                   bigint not null auto_increment comment 'ID',
   parent_id            bigint comment '父机构ID',
   code                 varchar(30) comment '编码',
   name                 varchar(100) comment '名称',
   type                 varchar(2) comment '类型',
   level_num            decimal(3) comment '层级',
   level_code           varchar(200) comment '层级编码',
   is_enabled           decimal(1) default 1 comment '是否有效',
   establish_date       datetime comment '成立日期',
   cancel_date          datetime comment '撤销日期',
   display_seq          bigint comment '显示顺序',
   remarks              varchar(500) comment '备注',
   is_deleted           decimal(1) default 0 comment '是否删除',
   del_rid              bigint default 0 comment '删除辅助主键',
   create_time          datetime(6) comment '创建时间',
   create_user_id       bigint comment '创建用户ID',
   create_org_id        bigint comment '创建用户所属机构ID',
   modify_time          datetime(6) comment '最后修改时间',
   modify_user_id       bigint comment '最后修改用户ID',
   trace_id             varchar(50) comment '跟踪ID',
   primary key (id),
   unique key uk_wdf_org_level_code (level_code),
   unique key uk_wdf_org_code (code, del_rid)
)
engine = InnoDB comment '机构';

/*==============================================================*/
/* Index: idx_wdf_org_parent_id                                 */
/*==============================================================*/
create index idx_wdf_org_parent_id on wdf_org
(
   parent_id
);

/*==============================================================*/
/* Table: wdf_role                                              */
/*==============================================================*/
create table wdf_role
(
   id                   bigint not null auto_increment comment 'ID',
   code                 varchar(30) comment '编码',
   name                 varchar(100) comment '名称',
   org_id               bigint comment '所属机构ID',
   is_sub_usable        decimal(1) default 0 comment '下级授权可用',
   is_enabled           decimal(1) default 1 comment '是否有效',
   remarks              varchar(500) comment '备注',
   is_deleted           decimal(1) default 0 comment '是否删除',
   del_rid              bigint default 0 comment '删除辅助主键',
   create_time          datetime(6) comment '创建时间',
   create_user_id       bigint comment '创建用户ID',
   create_org_id        bigint comment '创建用户所属机构ID',
   modify_time          datetime(6) comment '最后修改时间',
   modify_user_id       bigint comment '最后修改用户ID',
   trace_id             varchar(50) comment '跟踪ID',
   primary key (id),
   unique key uk_wdf_role_code (code, del_rid)
)
engine = InnoDB comment '角色';

/*==============================================================*/
/* Table: wdf_session                                           */
/*==============================================================*/
create table wdf_session
(
   id                   bigint not null auto_increment comment 'ID',
   session_key          varchar(50) not null comment '会话Key',
   create_time          datetime(6) not null comment '创建时间',
   last_access_time     datetime(6) not null comment '最后访问系统时间',
   inactive_interval    int not null comment '会话失效间隔',
   expired_time         datetime(6) not null comment '会话失效时间',
   client_ip            varchar(50) comment '客户端IP',
   user_id              bigint comment '用户ID',
   primary key (id),
   unique key uk_wdf_session_key (session_key)
)
engine = InnoDB comment '会话';

/*==============================================================*/
/* Index: idx_wdf_session_user_id                               */
/*==============================================================*/
create index idx_wdf_session_user_id on wdf_session
(
   user_id
);

/*==============================================================*/
/* Table: wdf_session_attr                                      */
/*==============================================================*/
create table wdf_session_attr
(
   id                   bigint not null auto_increment comment 'ID',
   session_id           bigint not null comment '会话表ID',
   attr_key             varchar(50) not null comment '属性名',
   attr_type            varchar(50) not null comment '属性类型',
   attr_value           varchar(500) not null comment '属性值',
   primary key (id),
   unique key uk_wdf_session_attr_session_key (session_id, attr_key)
)
engine = InnoDB comment '会话属性';

/*==============================================================*/
/* Table: wdf_user                                              */
/*==============================================================*/
create table wdf_user
(
   id                   bigint not null auto_increment comment 'ID',
   name                 varchar(30) not null comment '用户姓名',
   username             varchar(30) comment '登录名',
   password             varchar(128) comment '密码',
   pwd_salt             varchar(50) comment '密码盐',
   pwd_strength         decimal(1) comment '密码强度',
   pwd_encode_type      varchar(20) comment '密码加密方式',
   pwd_modify_time      datetime comment '密码最后修改时间',
   pwd_err_times        decimal(2) default 0 comment '密码错误次数',
   pwd_err_lock_time    datetime comment '密码错误锁定时间',
   pwd_err_unlock_time  datetime comment '密码错误解锁时间',
   org_id               bigint comment '所属机构ID',
   mobile               varchar(20) comment '手机号',
   phone                varchar(50) comment '固定电话',
   email                varchar(30) comment '邮箱',
   status               varchar(2) comment '状态',
   last_login_time      datetime comment '最后登录时间',
   last_login_ip        varchar(20) comment '最后登录IP',
   is_built_in          decimal(1) default 0 comment '是否系统内置',
   remarks              varchar(500) comment '备注',
   is_deleted           decimal(1) default 0 comment '是否删除',
   del_rid              bigint default 0 comment '删除辅助主键',
   create_time          datetime(6) comment '创建时间',
   create_user_id       bigint comment '创建用户ID',
   create_org_id        bigint comment '创建用户所属机构ID',
   modify_time          datetime(6) comment '最后修改时间',
   modify_user_id       bigint comment '最后修改用户ID',
   trace_id             varchar(50) comment '跟踪ID',
   primary key (id),
   unique key uk_wdf_user_username (username, del_rid)
)
engine = InnoDB comment '用户';

/*==============================================================*/
/* Table: wdf_user_role                                         */
/*==============================================================*/
create table wdf_user_role
(
   id                   bigint not null auto_increment comment 'ID',
   user_id              bigint not null comment '用户ID',
   role_id              bigint not null comment '角色ID',
   is_enabled           decimal(1) not null default 1 comment '是否有效',
   remarks              varchar(500) comment '备注',
   is_deleted           decimal(1) not null default 0 comment '是否删除',
   del_rid              bigint not null default 0 comment '删除辅助主键',
   create_time          datetime(6) comment '创建时间',
   create_user_id       bigint comment '创建用户ID',
   modify_time          datetime(6) comment '最后修改时间',
   modify_user_id       bigint comment '最后修改用户ID',
   trace_id             varchar(50) comment '跟踪ID',
   primary key (id),
   unique key uk_wdf_user_role (user_id, role_id, del_rid)
)
engine = InnoDB comment '用户-角色';

