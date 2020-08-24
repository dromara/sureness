use tom ;
-- ----------------------------
-- Records of auth_resource
-- ----------------------------
insert into auth_resource (id, name, code, uri, type, method, status, description) values (101, '用户获取token', 'ACCOUNT_TOKEN', '/auth/token', 'account', 'POST', 9, null);
insert into auth_resource (id, name, code, uri, type, method, status, description) values (102, '用户注册', 'ACCOUNT_REGISTER', '/auth/register', 'account', 'POST', 9, null);
insert into auth_resource (id, name, code, uri, type, method, status, description) values (103, '新增资源', 'ADD_RESOURCE', '/resource', 'resource', 'POST', 1, null);
insert into auth_resource (id, name, code, uri, type, method, status, description) values (104, '更新资源', 'UPDATE_RESOURCE', '/resource', 'resource', 'PUT', 1, null);
insert into auth_resource (id, name, code, uri, type, method, status, description) values (105, '删除资源', 'DELETE_RESOURCE', '/resource/*', 'resource', 'DELETE', 1, null);
insert into auth_resource (id, name, code, uri, type, method, status, description) values (106, '获取资源', 'GET_RESOURCES', '/resource/*/*', 'resource', 'GET', 1, null);
insert into auth_resource (id, name, code, uri, type, method, status, description) values (107, '添加角色', 'ADD_ROLE', '/role', 'role', 'POST', 1, null);
insert into auth_resource (id, name, code, uri, type, method, status, description) values (108, '更新角色', 'UPDATE_ROLE', '/role', 'role', 'PUT', 1, null);
insert into auth_resource (id, name, code, uri, type, method, status, description) values (109, '删除角色', 'DELETE_ROLE', '/role/*', 'role', 'DELETE', 1, null);
insert into auth_resource (id, name, code, uri, type, method, status, description) values (110, '获取角色', 'GET_ROLES', '/role/*/*', 'role', 'GET', 1, null);

-- ----------------------------
-- Records of auth_role
-- ----------------------------
insert into auth_role (id, name, code, status, description) values (100, '管理员角色', 'role_admin', 1, null);
insert into auth_role (id, name, code, status, description) values (102, '用户角色', 'role_user', 1, null);
insert into auth_role (id, name, code, status, description) values (103, '访客角色', 'role_guest', 1, null);

-- ----------------------------
-- Records of auth_role_resource_bind
-- ----------------------------
-- role_admin拥有所有资源
insert into auth_role_resource_bind (id, role_id, resource_id) values (1, 100, 101);
insert into auth_role_resource_bind (id, role_id, resource_id) values (2, 100, 102);
insert into auth_role_resource_bind (id, role_id, resource_id) values (3, 100, 103);
insert into auth_role_resource_bind (id, role_id, resource_id) values (4, 100, 104);
insert into auth_role_resource_bind (id, role_id, resource_id) values (5, 100, 105);
insert into auth_role_resource_bind (id, role_id, resource_id) values (6, 100, 106);
insert into auth_role_resource_bind (id, role_id, resource_id) values (7, 100, 107);
insert into auth_role_resource_bind (id, role_id, resource_id) values (8, 100, 108);
insert into auth_role_resource_bind (id, role_id, resource_id) values (9, 100, 109);
insert into auth_role_resource_bind (id, role_id, resource_id) values (10, 100, 110);

-- role_user拥有查看新增修改资源，不拥有删除资源
insert into auth_role_resource_bind (id, role_id, resource_id) values (11, 102, 103);
insert into auth_role_resource_bind (id, role_id, resource_id) values (12, 102, 104);
insert into auth_role_resource_bind (id, role_id, resource_id) values (13, 102, 106);
insert into auth_role_resource_bind (id, role_id, resource_id) values (14, 102, 107);
insert into auth_role_resource_bind (id, role_id, resource_id) values (15, 102, 108);
insert into auth_role_resource_bind (id, role_id, resource_id) values (16, 102, 110);

-- role_guest拥有查看资源
insert into auth_role_resource_bind (id, role_id, resource_id) values (17, 103, 106);
insert into auth_role_resource_bind (id, role_id, resource_id) values (18, 103, 110);


-- ----------------------------
-- Records of auth_user
-- ----------------------------
insert into  auth_user (id, username, password, salt, avatar, phone, email, sex, status, create_where) values (111, 'admin', 'admin', null, null, null, null, null, 1, 1);
insert into  auth_user (id, username, password, salt, avatar, phone, email, sex, status, create_where) values (112, 'user', 'user', null, null, null, null, null, 1, 1);
insert into  auth_user (id, username, password, salt, avatar, phone, email, sex, status, create_where) values (113, 'guest', 'guest', null, null, null, null, null, 1, 1);
insert into  auth_user (id, username, password, salt, avatar, phone, email, sex, status, create_where) values (114, 'noRole', 'noRole', null, null, null, null, null, 1, 1);



-- ----------------------------
-- Records of auth_user_role_bind
-- ----------------------------
insert into  auth_user_role_bind (id, user_id, role_id) values (12, 111, 100);
insert into  auth_user_role_bind (id, user_id, role_id) values (13, 111, 102);
insert into  auth_user_role_bind (id, user_id, role_id) values (14, 112, 102);
insert into  auth_user_role_bind (id, user_id, role_id) values (15, 113, 103);