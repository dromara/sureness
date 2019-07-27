package com.usthe.sureness.sample.tom.dao;

import com.usthe.sureness.sample.tom.pojo.entity.AuthUserRoleBindDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tomsun28
 * @date 16:44 2019-07-27
 */
public interface AuthUserRoleBindDao extends JpaRepository<AuthUserRoleBindDO, Long> {

}
