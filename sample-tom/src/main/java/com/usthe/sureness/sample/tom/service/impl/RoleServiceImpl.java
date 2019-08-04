package com.usthe.sureness.sample.tom.service.impl;

import com.usthe.sureness.sample.tom.pojo.entity.AuthRoleDO;
import com.usthe.sureness.sample.tom.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author tomsun28
 * @date 13:10 2019-08-04
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public boolean isRoleExist(AuthRoleDO authRole) {
        return false;
    }

    @Override
    public boolean addRole(AuthRoleDO authRole) {
        return false;
    }

    @Override
    public boolean updateRole(AuthRoleDO authRole) {
        return false;
    }

    @Override
    public boolean deleteRole(Long roleId) {
        return false;
    }

    @Override
    public Optional<List<AuthRoleDO>> getAllRole() {
        return Optional.empty();
    }

    @Override
    public Optional<Page<AuthRoleDO>> getPageRole(Integer currentPage, Integer pageSize) {
        return Optional.empty();
    }
}
