package com.usthe.sureness.sample.tom.dao;

import com.usthe.sureness.sample.tom.TomApplicationTest;
import com.usthe.sureness.sample.tom.pojo.entity.AuthRoleDO;
import com.usthe.sureness.sample.tom.pojo.entity.AuthUserDO;
import com.usthe.sureness.sample.tom.pojo.entity.AuthUserRoleBindDO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author tomsun28
 * @date 00:52 2019-07-31
 */
@DisplayName("database table UserRoleBind test")
class AuthUserRoleBindDaoTest extends TomApplicationTest {

    @Autowired
    private AuthUserRoleBindDao userRoleBindDao;

    @Autowired
    private AuthUserDao userDao;

    @Autowired
    private AuthRoleDao roleDao;

    @DisplayName("insert data in UserRoleBind should success")
    @Test
    @Transactional
    public void shouldSuccessWhenInsertBind() {
        AuthUserRoleBindDO userRoleBind = AuthUserRoleBindDO.builder()
                .userId(123L).roleId(232L).build();
        userRoleBind = userRoleBindDao.save(userRoleBind);
        Assertions.assertThat(userRoleBind).isNotNull();
    }

    @DisplayName("delete data in UserRoleBind shoudl success")
    @Test
    @Transactional
    public void shouldSuccessWhenDeleteBind() {
        AuthUserRoleBindDO userRoleBind = AuthUserRoleBindDO.builder()
                .userId(123L).roleId(232L).build();
        userRoleBind = userRoleBindDao.save(userRoleBind);
        userRoleBindDao.delete(userRoleBind);
        Assertions.assertThat(userRoleBindDao.findById(userRoleBind.getId()).isPresent()).isFalse();
    }

    @DisplayName("select roles which the user has with using UserRoleBind should success")
    @Test
    @Transactional
    public void shouldFindRoleWhenUseUserBind() {
        AuthRoleDO authRole1 = AuthRoleDO.builder()
                .name("admin").code("role_admin")
                .status(1).build();
        roleDao.save(authRole1);
        AuthRoleDO authRole2 = AuthRoleDO.builder()
                .name("controller").code("role_access")
                .status(1).build();
        roleDao.save(authRole2);
        AuthUserDO authUser = AuthUserDO.builder()
                .username("tom").password("1234")
                .status(1).build();
        userDao.save(authUser);
        AuthUserRoleBindDO userRoleBind = AuthUserRoleBindDO.builder()
                .userId(authUser.getId()).roleId(authRole1.getId()).build();
        userRoleBindDao.save(userRoleBind);
        userRoleBind = AuthUserRoleBindDO.builder()
                .userId(authUser.getId()).roleId(authRole2.getId()).build();
        userRoleBindDao.save(userRoleBind);
        Assertions.assertThat(userRoleBindDao.findUserBindRoleList(authUser.getId())).isNotNull()
                .size().isEqualTo(2);
    }

}