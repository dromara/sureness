package com.usthe.sureness.sample.tom.dao;


import com.usthe.sureness.sample.tom.TomApplicationTest;
import com.usthe.sureness.sample.tom.pojo.entity.AuthUserDO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tomsun28
 * @date 00:46 2019-07-31
 */
@DisplayName("数据库表User操作测试")
class AuthUserDaoTest extends TomApplicationTest {

    @Autowired
    private AuthUserDao authUserDao;

    @DisplayName("插入数据User应成功")
    @Test
    @Transactional
    public void shouldSuccessWhenInsertUser() {
        AuthUserDO authUser = AuthUserDO.builder()
                .username("tom").password("ha123")
                .status(1).build();
        authUser = authUserDao.save(authUser);
        Assertions.assertThat(authUser).isNotNull();
    }

    @DisplayName("删除数据User应成功")
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