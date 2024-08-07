package org.dromara.sureness.sample.tom.dao;


import org.assertj.core.api.Assertions;
import org.dromara.sureness.sample.tom.TomApplicationTest;
import org.dromara.sureness.sample.tom.dao.AuthUserDao;
import org.dromara.sureness.sample.tom.pojo.entity.AuthUserDO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tomsun28
 * @date 00:46 2019-07-31
 */
@DisplayName("database table User test")
class AuthUserDaoTest extends TomApplicationTest {

    @Autowired
    private AuthUserDao authUserDao;

    @DisplayName("insert data in User should success")
    @Test
    @Transactional
    public void shouldSuccessWhenInsertUser() {
        AuthUserDO authUser = AuthUserDO.builder()
                .username("tom").password("ha123")
                .status(1).build();
        authUser = authUserDao.save(authUser);
        Assertions.assertThat(authUser).isNotNull();
    }

    @DisplayName("delete data in User should success")
    @Test
    @Transactional
    public void shouldSuccessWhenDeleteUser() {
        AuthUserDO authUser = AuthUserDO.builder()
                .username("tom").password("ha123")
                .status(1).build();
        authUser = authUserDao.save(authUser);
        authUserDao.delete(authUser);
        Assertions.assertThat(authUserDao.findById(authUser.getId()).isPresent()).isFalse();
    }
}