package com.usthe.sureness.sample.tom.dao;

import com.usthe.sureness.sample.tom.pojo.entity.AuthUserDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tomsun28
 * @date 16:43 2019-07-27
 */
public interface AuthUserDao extends JpaRepository<AuthUserDO, Long> {
}
