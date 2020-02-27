package com.usthe.sureness.sample.tom.dao;

import com.usthe.sureness.sample.tom.pojo.entity.AuthResourceDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
//    @Query("select 1 from AuthResourceDO group by ")
//    Optional<Set<String>> getPathRoleData();
}
