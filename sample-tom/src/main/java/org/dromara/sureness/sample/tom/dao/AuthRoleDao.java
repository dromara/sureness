package org.dromara.sureness.sample.tom.dao;

import org.dromara.sureness.sample.tom.pojo.entity.AuthRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author tomsun28
 * @date 16:42 2019-07-27
 */
public interface AuthRoleDao extends JpaRepository<AuthRoleDO, Long> {

    /**
     * Query the role owned by the current user
     * @param username username
     * @return role list
     */
    @Query("select ar.name from AuthRoleDO ar, AuthUserDO au, AuthUserRoleBindDO bind " +
            "where ar.id = bind.roleId and au.id = bind.userId and au.username = :username")
    List<String> findAccountOwnRoles(@Param("username") String username);
}
