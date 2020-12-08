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
@DisplayName("database table Resource test")
class AuthResourceDaoTest extends TomApplicationTest {

    @Autowired
    private AuthResourceDao authResourceDao;

    @DisplayName("insert data in resource should success")
    @Test
    @Transactional
    public void shouldSuccessWhenInsertResource() {
        AuthResourceDO resource = AuthResourceDO.builder()
                .name("role_manager").code("ROLE_MANAGE")
                .uri("/index/role").status(1)
                .method("post").build();
        resource = authResourceDao.save(resource);
        Assertions.assertThat(resource).isNotNull();
    }

    /**
     * todo something error happen, resource would change when resourceUpdate update in @Transactional, why?
     */
    @DisplayName("update resource data should success")
    @Test
    public void shouldSuccessWhenUpdateResource() {
        AuthResourceDO resource = AuthResourceDO.builder()
                .name("role_manager").code("ROLE_MANAGE")
                .uri("/index/role").status(1)
                .method("post").build();
        resource  = authResourceDao.saveAndFlush(resource);
        AuthResourceDO resourceUpdate = AuthResourceDO.builder()
                .id(resource.getId())
                .name("role_manager2").code("ROLE_MANAGE2")
                .uri("/index/role2").status(2)
                .method("get").build();
        resourceUpdate = authResourceDao.saveAndFlush(resourceUpdate);
        authResourceDao.delete(resourceUpdate);
        Assertions.assertThat(resourceUpdate).isNotNull().isNotEqualTo(resource);
    }

    @DisplayName("delete resource data should success")
    @Test
    @Transactional
    public void shouldSuccessWhenDeleteResource() {
        AuthResourceDO resource = AuthResourceDO.builder()
                .name("role_manager").code("ROLE_MANAGE")
                .uri("/index/role").status(1)
                .method("post").build();
        resource = authResourceDao.save(resource);
        authResourceDao.delete(resource);
        Assertions.assertThat(authResourceDao.findById(resource.getId()).isPresent()).isFalse();
    }

    @DisplayName("select data:Resource===Method===Role should success")
    @Test
    @Transactional
    public void shouldReturnSuccessWhenGetEnableResourcePathRoleData() {
        Optional<List<String>> optional = authResourceDao.getEnableResourcePathRoleData();
        Assertions.assertThat(optional.isPresent()).isTrue();
    }

    @DisplayName("select data:Resource===Method should success")
    @Test
    @Transactional
    public void shouldSuccessWhenGetDisableResourcePathData() {
        AuthResourceDO resource = AuthResourceDO.builder()
                .name("role_manager").code("ROLE_MANAGE")
                .uri("/index/role/book").status(9)
                .method("post").build();
        authResourceDao.save(resource);
        Optional<List<String>> optional = authResourceDao.getDisableResourcePathData();
        Assertions.assertThat(optional.isPresent()).isTrue();
    }

}