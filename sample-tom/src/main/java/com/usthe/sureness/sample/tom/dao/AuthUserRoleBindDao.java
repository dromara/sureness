package com.usthe.sureness.sample.tom.dao;

import com.usthe.sureness.sample.tom.pojo.entity.AuthRoleDO;
import com.usthe.sureness.sample.tom.pojo.entity.AuthUserRoleBindDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author tomsun28
 * @date 16:44 2019-07-27
 */
public interface AuthUserRoleBindDao extends JpaRepository<AuthUserRoleBindDO, Long> {

    /**
     * Query the role owned by the current user
     * @param userId userId
     * @return role list
     */
    @Query("select ar from AuthRoleDO ar, AuthUserRoleBindDO bind " +
            "where ar.id = bind.roleId and bind.userId = :userId")
    List<AuthRoleDO> findUserBindRoleList(@Param("userId") Long userId);
}
