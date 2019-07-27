package com.usthe.sureness.sample.tom.dao;

import com.usthe.sureness.sample.tom.pojo.entity.AuthResourceDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tomsun28
 * @date 16:40 2019-07-27
 */
public interface AuthResourceDao extends JpaRepository<AuthResourceDO, Long> {

}
