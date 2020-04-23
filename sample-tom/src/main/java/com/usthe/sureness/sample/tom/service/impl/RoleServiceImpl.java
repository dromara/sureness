package com.usthe.sureness.sample.tom.service.impl;

import com.usthe.sureness.sample.tom.dao.AuthResourceDao;
import com.usthe.sureness.sample.tom.dao.AuthRoleDao;
import com.usthe.sureness.sample.tom.pojo.entity.AuthResourceDO;
import com.usthe.sureness.sample.tom.pojo.entity.AuthRoleDO;
import com.usthe.sureness.sample.tom.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author tomsun28
 * @date 13:10 2019-08-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private AuthRoleDao authRoleDao;

    @Autowired
    private AuthResourceDao authResourceDao;

    @Override
    public boolean isRoleExist(AuthRoleDO authRole) {
        AuthRoleDO role = AuthRoleDO.builder()
                .name(authRole.getName()).code(authRole.getCode()).build();
        return authRoleDao.exists(Example.of(role));
    }

    @Override
    public boolean addRole(AuthRoleDO authRole) {
        if (isRoleExist(authRole)) {
            return false;
        } else {
            authRoleDao.saveAndFlush(authRole);
            return true;
        }
    }

    @Override
    public boolean updateRole(AuthRoleDO authRole) {
        if (authRoleDao.existsById(authRole.getId())) {
            authRoleDao.saveAndFlush(authRole);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteRole(Long roleId) {
        if (authRoleDao.existsById(roleId)) {
            authRoleDao.deleteById(roleId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<List<AuthRoleDO>> getAllRole() {
        List<AuthRoleDO> roleList = authRoleDao.findAll();
        return Optional.of(roleList);
    }

    @Override
    public Page<AuthRoleDO> getPageRole(Integer currentPage, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);
        return authRoleDao.findAll(pageRequest);
    }

    @Override
    public Page<AuthResourceDO> getPageResourceOwnRole(Long roleId, Integer currentPage, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, Sort.Direction.ASC, "id");
        return authResourceDao.findRoleOwnResource(roleId, pageRequest);
    }
}
