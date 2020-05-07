package com.usthe.sureness.sample.tom.dao;

import com.usthe.sureness.sample.tom.TomApplicationTest;
import com.usthe.sureness.sample.tom.pojo.entity.AuthResourceDO;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * @author tomsun28
 * @date 22:35 2019-07-27
 */
@DisplayName("数据库表Resource操作测试")
class AuthResourceDaoTest extends TomApplicationTest {

    @Autowired
    private AuthResourceDao authResourceDao;

    @DisplayName("插入数据Resource应正确")
    @Test
    @Transactional
    public void shouldSuccessWhenInsertResource() {
        AuthResourceDO resource = AuthResourceDO.builder()
                .name("角色管理").code("ROLE_MANAGE")
                .uri("/index/role").status(1)
                .method("post").build();
        resource = authResourceDao.save(resource);
        Assertions.assertThat(resource).isNotNull();
    }

    /**
     * todo 这里加事务有点问题  resourceUpdate在数据库改变后,resource也会被改变 why?
     */
    @DisplayName("更新数据Resource应正确")
    @Test
    public void shouldSuccessWhenUpdateResource() {
        AuthResourceDO resource = AuthResourceDO.builder()
                .name("角色管理").code("ROLE_MANAGE")
                .uri("/index/role").status(1)
                .method("post").build();
        resource  = authResourceDao.saveAndFlush(resource);
        AuthResourceDO resourceUpdate = AuthResourceDO.builder()
                .id(resource.getId())
                .name("角色管理2").code("ROLE_MANAGE2")
                .uri("/index/role2").status(2)
                .method("get").build();
        resourceUpdate = authResourceDao.saveAndFlush(resourceUpdate);
        authResourceDao.delete(resourceUpdate);
        Assertions.assertThat(resourceUpdate).isNotNull().isNotEqualTo(resource);
    }

    @DisplayName("删除数据Resource应正确")
    @Test
    @Transactional
    public void shouldSuccessWhenDeleteResource() {
        AuthResourceDO resource = AuthResourceDO.builder()
                .name("角色管理").code("ROLE_MANAGE")
                .uri("/index/role").status(1)
                .method("post").build();
        resource = authResourceDao.save(resource);
        authResourceDao.delete(resource);
        Assertions.assertThat(authResourceDao.findById(resource.getId()).isPresent()).isFalse();
    }

    @DisplayName("查询数据Resource===Method===Role应正确")
    @Test
    @Transactional
    public void shouldReturnSuccessWhenGetEnableResourcePathRoleData() {
        Optional<List<String>> optional = authResourceDao.getEnableResourcePathRoleData();
        Assertions.assertThat(optional.isPresent()).isTrue();
    }

    @DisplayName("查询disable数据Resource===Method应正确")
    @Test
    @Transactional
    public void shouldSuccessWhenGetDisableResourcePathData() {
        AuthResourceDO resource = AuthResourceDO.builder()
                .name("角色管理").code("ROLE_MANAGE")
                .uri("/index/role/book").status(9)
                .method("post").build();
        authResourceDao.save(resource);
        Optional<List<String>> optional = authResourceDao.getDisableResourcePathData();
        Assertions.assertThat(optional.isPresent()).isTrue();
    }

}