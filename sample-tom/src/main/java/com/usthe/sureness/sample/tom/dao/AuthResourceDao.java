package com.usthe.sureness.sample.tom.dao;

import com.usthe.sureness.sample.tom.pojo.entity.AuthResourceDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author tomsun28
 * @date 16:40 2019-07-27
 */
public interface AuthResourceDao extends JpaRepository<AuthResourceDO, Long> {

    /**
     * 获取uri资源与其对应角色关系链 eg: /api/v2/host===post===[role2,role3,role4]
     * @return 资源角色链set
     */
    @Query(value = "SELECT  CONCAT(LOWER(res.uri),\"===\",LOWER(res.method),\"===[\",IFNULL(GROUP_CONCAT(DISTINCT role.code),\"\"),\"]\") " +
            "FROM auth_resource res " +
            "LEFT JOIN auth_role_resource_bind bind on res.id = bind.resource_id " +
            "LEFT JOIN auth_role role on role.id = bind.role_id " +
            "where res.status = 1 " +
            "group by res.id", nativeQuery = true)
    Optional<List<String>> getEnableResourcePathRoleData();



    /**
     * 获取禁用的uri资源 eg: /api/v2/host===post
     * @return 资源链set
     */
    @Query("select CONCAT(LOWER(resource.uri),'===', resource.method) " +
            "from AuthResourceDO resource where resource.status = 9 order by resource.id")
    Optional<List<String>> getDisableResourcePathData();

    /**
     * 获取分页形式的当前角色拥有的可用API资源
     * @param roleId 角色ID
     * @param request page
     * @return api resource list
     */
    @Query("select distinct resource from AuthResourceDO resource " +
            "left join AuthRoleResourceBindDO bind on bind.resourceId = resource.id " +
            "where bind.roleId = :roleId and resource.status = 1 " +
            "order by resource.id asc")
    Page<AuthResourceDO> findRoleOwnResource(@Param("roleId") Long roleId, Pageable request);
}
