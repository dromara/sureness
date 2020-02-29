package com.usthe.sureness.sample.tom.service.impl;

import com.usthe.sureness.sample.tom.dao.AuthRoleDao;
import com.usthe.sureness.sample.tom.pojo.entity.AuthRoleDO;
import com.usthe.sureness.sample.tom.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author tomsun28
 * @date 13:10 2019-08-04
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private AuthRoleDao authRoleDao;

    @Override
    public boolean isRoleExist(AuthRoleDO authRole) {
        return authRoleDao.existsById(authRole.getId());
    }

    @Override
    public boolean addRole(AuthRoleDO authRole) {
        return authRoleDao.save(authRole) != null;
    }

    @Override
    public boolean updateRole(AuthRoleDO authRole) {
        return authRoleDao.save(authRole) != null;
    }

    @Override
    public boolean deleteRole(Long roleId) {
        authRoleDao.deleteById(roleId);
        return true;
    }

    @Override
    public Optional<List<AuthRoleDO>> getAllRole() {
        List<AuthRoleDO> roleList = authRoleDao.findAll();
        return Optional.of(roleList);
    }

    @Override
    public Optional<Page<AuthRoleDO>> getPageRole(Integer currentPage, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);
        Page<AuthRoleDO> page = authRoleDao.findAll(pageRequest);
        return Optional.of(page);
    }
}
