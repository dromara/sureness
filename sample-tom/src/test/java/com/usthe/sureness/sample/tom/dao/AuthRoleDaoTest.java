package com.usthe.sureness.sample.tom.dao;

import com.usthe.sureness.sample.tom.TomApplicationTest;
import com.usthe.sureness.sample.tom.pojo.entity.AuthRoleDO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author tomsun28
 * @date 23:59 2019-07-30
 */
@DisplayName("数据库表Role操作测试")
class AuthRoleDaoTest extends TomApplicationTest {

    @Autowired
    private AuthRoleDao authRoleDao;

    @DisplayName("插入数据Role应成功")
    @Test
    @Transactional
    public void shouldSuccessWhenInsertRole() {
        AuthRoleDO authRole = AuthRoleDO.builder()
                .name("管理员").code("role_admin")
                .status(1).build();
        authRole = authRoleDao.save(authRole);
        Assertions.assertThat(authRole).isNotNull();
    }

    @DisplayName("删除数据Role应成功")
    @Test
    @Transactional
    public void shouldSuccessWhenDeleteRole() {
        AuthRoleDO authRole = AuthRoleDO.builder()
                .name("管理员").code("role_admin")
                .status(1).build();
        authRole = authRoleDao.save(authRole);
        authRoleDao.delete(authRole);
        Assertions.assertThat(authRoleDao.findById(authRole.getId()).isPresent()).isFalse();
    }

}