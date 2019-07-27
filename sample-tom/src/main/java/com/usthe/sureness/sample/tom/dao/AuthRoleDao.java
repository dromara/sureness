package com.usthe.sureness.sample.tom.dao;

import com.usthe.sureness.sample.tom.pojo.entity.AuthRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tomsun28
 * @date 16:42 2019-07-27
 */
public interface AuthRoleDao extends JpaRepository<AuthRoleDO, Long> {

}
