package com.usthe.sureness.sample.tom.service.impl;

import com.usthe.sureness.sample.tom.dao.AuthResourceDao;
import com.usthe.sureness.sample.tom.pojo.entity.AuthResourceDO;
import com.usthe.sureness.sample.tom.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author tomsun28
 * @date 13:09 2019-08-04
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private AuthResourceDao authResourceDao;

    @Override
    public boolean addResource(AuthResourceDO authResource) {
        return authResourceDao.save(authResource) != null;
    }

    @Override
    public boolean isResourceExist(AuthResourceDO authResource) {
        return authResourceDao.existsById(authResource.getId());
    }

    @Override
    public boolean updateResource(AuthResourceDO authResource) {
        return authResourceDao.save(authResource) != null;
    }

    @Override
    public boolean deleteResource(Long resourceId) {
        authResourceDao.deleteById(resourceId);
        return true;
    }

    @Override
    public Optional<List<AuthResourceDO>> getAllResource() {
        List<AuthResourceDO> resourceList = authResourceDao.findAll();
        return Optional.of(resourceList);
    }

    @Override
    public Optional<Page<AuthResourceDO>> getPageResource(Integer currentPage, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(currentPage, pageSize);
        Page<AuthResourceDO> page = authResourceDao.findAll(pageRequest);
        return Optional.of(page);
    }
}
