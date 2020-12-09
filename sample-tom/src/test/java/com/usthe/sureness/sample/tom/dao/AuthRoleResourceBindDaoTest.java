package com.usthe.sureness.sample.tom.dao;

import com.usthe.sureness.sample.tom.TomApplicationTest;
import com.usthe.sureness.sample.tom.pojo.entity.AuthResourceDO;
import com.usthe.sureness.sample.tom.pojo.entity.AuthRoleDO;
import com.usthe.sureness.sample.tom.pojo.entity.AuthRoleResourceBindDO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author tomsun28
 * @date 00:10 2019-07-31
 */
@DisplayName("database table RoleResourceBind test")
class AuthRoleResourceBindDaoTest extends TomApplicationTest {

    @Autowired
    private AuthRoleResourceBindDao roleResourceBindDao;

    @Autowired
    private AuthResourceDao resourceDao;

    @Autowired
    private AuthRoleDao roleDao;

    @DisplayName("insert data in bind should success")
    @Test
    @Transactional
    public void shouldSuccessWhenInsertBind() {
        AuthRoleResourceBindDO roleResourceBind = AuthRoleResourceBindDO.builder()
                .roleId(123L).resourceId(234L).build();
        roleResourceBind = roleResourceBindDao.save(roleResourceBind);
        Assertions.assertThat(roleResourceBind).isNotNull();
    }

    @DisplayName("delete data in bind should success")
    @Test
    @Transactional
    public void shouldSuccessWhenDeleteBind() {
        AuthRoleResourceBindDO roleResourceBind = AuthRoleResourceBindDO.builder()
                .roleId(123L).resourceId(234L).build();
        roleResourceBind = roleResourceBindDao.save(roleResourceBind);
        roleResourceBindDao.delete(roleResourceBind);
        Assertions.assertThat(roleResourceBindDao.findById(roleResourceBind.getId()).isPresent()).isFalse();
    }

    @DisplayName("select resource which the role has with using RoleResourceBind should success")
    @Test
    @Transactional
    public void shouldFindResourceWhenUseRoleBind() {
        AuthResourceDO resource1 = AuthResourceDO.builder()
                .name("role_manager").code("ROLE_MANAGE")
                .uri("/index/role").status(1)
                .method("post")
                .build();
        resourceDao.save(resource1);
        AuthResourceDO resource2 = AuthResourceDO.builder()
                .name("resource_manager").code("RESOURCE_MANAGE")
                .uri("/index/resource").status(1)
                .method("get")
                .build();
        resourceDao.save(resource2);
        AuthRoleDO authRole = AuthRoleDO.builder()
                .name("admin").code("role_admin")
                .status(1).build();
        roleDao.save(authRole);
        AuthRoleResourceBindDO roleResourceBind = AuthRoleResourceBindDO.builder()
                .roleId(authRole.getId()).resourceId(resource1.getId()).build();
        roleResourceBindDao.save(roleResourceBind);
        roleResourceBind = AuthRoleResourceBindDO.builder()
                .roleId(authRole.getId()).resourceId(resource2.getId()).build();
        roleResourceBindDao.save(roleResourceBind);
        Assertions.assertThat(roleResourceBindDao.findRoleBindResourceList(authRole.getId())).isNotNull()
                .size().isEqualTo(2);
    }

}