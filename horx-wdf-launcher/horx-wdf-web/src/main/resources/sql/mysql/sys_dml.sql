-- user
insert into wdf_user(id,name,username,password,pwd_encode_type,status,is_built_in) values(1,'管理员','admin','admin12345','01','01',1);

-- menu
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code) values(999,-1,'系统菜单','01',1,'Fd',1,0,'menu');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,icon_content) values(1,999,'系统管理','01',2,'Fd-1',10,1,'sys','layui-icon-component');

-- menu，用户管理
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code,icon_content,url) values(2,1,'用户管理','01',3,'Fd-1-2',10,1,'sys.user',
'sys.user.query,sys.user.modifyPwd','layui-icon-user','page/sys/user/list');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(3,2,'新增用户','02',4,'Fd-1-2-3',10,1,'sys.user.create','sys.user.create,sys.role.usable');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(4,2,'修改用户','02',4,'Fd-1-2-4',20,1,'sys.user.modify','sys.user.modify,sys.role.usable');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(5,2,'删除用户','02',4,'Fd-1-2-5',30,1,'sys.user.remove','sys.user.remove');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(6,2,'用户数据权限','02',4,'Fd-1-2-6',40,1,'sys.dataAuthority.user','sys.dataAuthority.user');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(7,2,'解锁用户','02',4,'Fd-1-2-7',50,1,'sys.user.unlock','sys.user.unlock');

insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code,icon_content,url) values(8,1,'在线用户','01',3,'Fd-1-8',20,1,'sys.onlineUser',
'sys.onlineUser.query','layui-icon-user','page/sys/onlineUser/list');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(9,8,'强制下线','02',4,'Fd-1-8-9',10,1,'sys.onlineUser.offline','sys.onlineUser.offline');

-- menu，机构管理
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code,icon_content,url) values(10,1,'机构管理','01',3,'Fd-1-A',30,1,'sys.org','sys.org.query','layui-icon-senior','page/sys/org/list');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(11,10,'新增机构','02',4,'Fd-1-A-B',10,1,'sys.org.create','sys.org.create');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(12,10,'修改机构','02',4,'Fd-1-A-C',20,1,'sys.org.modify','sys.org.modify');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(13,10,'删除机构','02',4,'Fd-1-A-D',30,1,'sys.org.remove','sys.org.remove');

-- menu，角色管理
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code,icon_content,url) values(14,1,'角色管理','01',3,'Fd-1-E',40,1,'sys.role','sys.role.query,sys.role.detail,sys.dataAuthority.role.detail,sys.role.usable','layui-icon-auz','page/sys/role/list');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(15,14,'新增角色','02',4,'Fd-1-E-F',10,1,'sys.role.create','sys.role.create,sys.role.edit');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(16,14,'修改角色','02',4,'Fd-1-E-G',20,1,'sys.role.modify','sys.role.modify,sys.role.edit');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(17,14,'删除角色','02',4,'Fd-1-E-H',30,1,'sys.role.remove','sys.role.remove');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(18,14,'角色数据权限','02',4,'Fd-1-E-I',40,1,'sys.dataAuthority.role','sys.dataAuthority.role');

insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code,icon_content,url) values(19,1,'授权可用角色','01',3,'Fd-1-J',50,1,'sys.role.usable','sys.role.usable,sys.role.detail,sys.dataAuthority.role.detail','layui-icon-auz','page/sys/role/usable/list');

-- menu，菜单管理
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code,icon_content,url) values(20,1,'菜单管理','01',3,'Fd-1-K',60,1,'sys.menu','sys.menu.query','layui-icon-template-1','page/sys/menu/list');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(21,20,'新增菜单','02',4,'Fd-1-K-L',10,1,'sys.menu.create','sys.menu.create');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(22,20,'修改菜单','02',4,'Fd-1-K-M',20,1,'sys.menu.modify','sys.menu.modify');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(23,20,'删除菜单','02',4,'Fd-1-K-N',30,1,'sys.menu.remove','sys.menu.remove');

-- menu，字典管理
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code,icon_content,url) values(24,1,'字典管理','01',3,'Fd-1-O',70,1,'sys.dict','sys.dict.query','layui-icon-tabs','page/sys/dict/list');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(25,24,'新增字典','02',4,'Fd-1-O-P',10,1,'sys.dict.create','sys.dict.create,sys.dict.item');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(26,24,'修改字典','02',4,'Fd-1-O-Q',20,1,'sys.dict.modify','sys.dict.modify,sys.dict.item');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(27,24,'删除字典','02',4,'Fd-1-O-R',30,1,'sys.dict.remove','sys.dict.remove');

-- menu，数据权限定义
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code,icon_content,url) values(28,1,'数据权限定义','01',3,'Fd-1-S',80,1,'sys.dataPermission','sys.dataPermission.query','layui-icon-vercode','page/sys/dataPermission/list');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(29,28,'新增数据权限定义','02',4,'Fd-1-S-T',10,1,'sys.dataPermission.create','sys.dataPermission.create,sys.dict.query');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(30,28,'修改数据权限定义','02',4,'Fd-1-S-U',20,1,'sys.dataPermission.modify','sys.dataPermission.modify');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(31,28,'删除数据权限定义','02',4,'Fd-1-S-V',30,1,'sys.dataPermission.remove','sys.dataPermission.remove');
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code) values(32,28,'数据权限默认权限','02',4,'Fd-1-S-W',40,1,'sys.dataAuthority.default','sys.dataAuthority.default');

-- menu，系统访问日志
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code,icon_content,url) values(33,1,'系统访问日志','01',3,'Fd-1-X',90,1,'sys.accessLog','sys.accessLog.query','layui-icon-log','page/sys/accessLog/list');

-- menu，数据操作日志
insert into wdf_menu(id,parent_id,name,type,level_num,level_code,display_seq,is_built_in,code,permission_code,icon_content,url) values(34,1,'数据操作日志','01',3,'Fd-1-Y',100,1,'sys.dataLog','sys.dataLog.query','layui-icon-log','page/sys/dataLog/list');


-- dict，字典业务类型
insert into wdf_dict(id,code,name,biz_type,is_tree_data) values(999,'BizType','业务类型','sys',0);
insert into wdf_dict_item(id,dict_id,code,name,full_code,simple_code,display_seq) values(9999,999,'sys','系统管理','XiTongGuanLi','XTGL',10);

-- dict，机构类型
insert into wdf_dict(id,code,name,biz_type,is_tree_data) values(1,'OrgType','机构类型','sys',0);
insert into wdf_dict_item(id,dict_id,code,name,full_code,simple_code,display_seq) values(1,1,'01','单位','DanWei','DW',10);
insert into wdf_dict_item(id,dict_id,code,name,full_code,simple_code,display_seq) values(2,1,'02','部门','BuMen','BM',20);

-- dict，用户状态
insert into wdf_dict(id,code,name,biz_type,is_tree_data) values(3,'UserStatus','用户状态','sys',0);
insert into wdf_dict_item(id,dict_id,code,name,full_code,simple_code,display_seq) values(6,3,'01','启用','QiYong','QY',10);
insert into wdf_dict_item(id,dict_id,code,name,full_code,simple_code,display_seq) values(7,3,'02','停用','TingYong','TY',20);

-- dict，数据权限类型
insert into wdf_dict(id,code,name,biz_type,is_tree_data) values(2,'DataPermissionType','数据权限类型','sys',0);
insert into wdf_dict_item(id,dict_id,code,name,full_code,simple_code,display_seq) values(3,2,'user','用户','YongHu','YH',10);
insert into wdf_dict_item(id,dict_id,code,name,full_code,simple_code,display_seq) values(4,2,'org','机构','JiGou','JG',20);
insert into wdf_dict_item(id,dict_id,code,name,full_code,simple_code,display_seq) values(5,2,'dict','字典','ZiDian','ZD',30);

-- 数据权限，字典业务类型
insert into wdf_data_perm(id,code,name,obj_type,obj_id,is_enabled,display_seq) values(1,'sys.dict.bizType','字典业务类型','dict',999,1,10);

-- 数据权限，系统管理机构权限
insert into wdf_data_perm(id,code,name,obj_type,obj_id,is_enabled,display_seq) values(2,'sys.org','系统管理机构权限','org',null,1,20);

