package com.usthe.sureness.sample.tom.dao;

import com.usthe.sureness.sample.tom.pojo.entity.AuthResourceDO;
import com.usthe.sureness.sample.tom.pojo.entity.AuthRoleResourceBindDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * @author tomsun28
 * @date 16:43 2019-07-27
 */
public interface AuthRoleResourceBindDao extends JpaRepository<AuthRoleResourceBindDO, Long> {

    /**
     * 查询当前角色拥有的资源
     * @param roleId 角色ID
     * @return 资源list
     */
    @Query("select rs from AuthResourceDO rs, AuthRoleResourceBindDO bind " +
            "where rs.id = bind.resourceId and bind.roleId = :roleId")
    List<AuthResourceDO> findRoleBindResourceList(@Param("roleId") Long roleId);

    /**
     * delete record which roleId and resource equals this
     * @param roleId roleID
     * @param resourceId resourceId
     */
    @Query("delete from AuthRoleResourceBindDO bind " +
            "where bind.roleId = :roleId and bind.resourceId = :resourceId")
    void deleteRoleResourceBind(@Param("roleId") Long roleId,@Param("resourceId") Long resourceId);
}
