-- ----------------------------
-- Records of auth_resource
-- ----------------------------
insert into auth_resource values (101, '用户登录', 'ACCOUNT_LOGIN', '/account/login', 'account', 'POST', 1, null, null, null);
insert into auth_resource values (112, '用户注册', 'ACCOUNT_REGISTER', '/account/register', 'account', 'POST', 1, null, null, null);
insert into auth_resource values (139, '授权资源给角色', 'ROLE_AUTHORITY_RESOURCE', '/role/authority/resource', 'authority', 'POST', 1, null, null, null);
insert into auth_resource values (140, '删除角色的授权资源', 'ROLE_AUTHORITY_RESOURCE', '/role/authority/resource', 'authority', 'DELETE', 1, null, null, null);
insert into auth_resource values (142, '添加角色', 'ADD_ROLE', '/role', 'role', 'POST', 1, null, null, null);
insert into auth_resource values (143, '更新角色', 'UPDATE_ROLE', '/role', 'role', 'PUT', 1, null, null, null);
insert into auth_resource values (144, '删除角色', 'DELETE_ROLE', '/role', 'role', 'DELETE', 1, null, null, null);
insert into auth_resource values (145, '日志记录', 'LOG_WATCH', '/index/log', 'log', 'POST', 1, null, null, null);

-- ----------------------------
-- Records of auth_role
-- ----------------------------
insert into auth_role values (100, '管理员角色', 'role_admin', 1, null, null, null);
insert into auth_role values (102, '用户角色', 'role_user', 1, null, null, null);
insert into auth_role values (103, '访客角色', 'role_guest', 1, null, null, null);
insert into auth_role values (104, '非角色', 'role_anon', 1, null, null, null);

-- ----------------------------
-- Records of auth_role_resource
-- ----------------------------
insert into auth_role_resource_bind values (1, 100, 101, null, null);
insert into auth_role_resource_bind values (3, 102, 112, null, null);
insert into auth_role_resource_bind values (6, 103, 139, null, null);
insert into auth_role_resource_bind values (8, 104, 140, null, null);
insert into auth_role_resource_bind values (9, 104, 143, null, null);

-- ----------------------------
-- Records of auth_user
-- ----------------------------
insert into  auth_user  values (111, 'admin', 'admin', '7777', null, null, null, null, 1, 1, null, null);


-- ----------------------------
-- Records of auth_user_role
-- ----------------------------
insert into  auth_user_role_bind  values (12, 111, 103, null, null);