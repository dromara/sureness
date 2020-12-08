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
@DisplayName("database table Role test")
class AuthRoleDaoTest extends TomApplicationTest {

    @Autowired
    private AuthRoleDao authRoleDao;

    @DisplayName("insert date in role should success")
    @Test
    @Transactional
    public void shouldSuccessWhenInsertRole() {
        AuthRoleDO authRole = AuthRoleDO.builder()
                .name("admin").code("role_admin")
                .status(1).build();
        authRole = authRoleDao.save(authRole);
        Assertions.assertThat(authRole).isNotNull();
    }

    @DisplayName("delete data in role should success")
    @Test
    @Transactional
    public void shouldSuccessWhenDeleteRole() {
        AuthRoleDO authRole = AuthRoleDO.builder()
                .name("admin").code("role_admin")
                .status(1).build();
        authRole = authRoleDao.save(authRole);
        authRoleDao.delete(authRole);
        Assertions.assertThat(authRoleDao.findById(authRole.getId()).isPresent()).isFalse();
    }

}