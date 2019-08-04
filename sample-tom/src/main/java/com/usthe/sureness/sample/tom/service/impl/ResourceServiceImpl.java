package com.usthe.sureness.sample.tom.service.impl;

import com.usthe.sureness.sample.tom.pojo.entity.AuthResourceDO;
import com.usthe.sureness.sample.tom.service.ResourceService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author tomsun28
 * @date 13:09 2019-08-04
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    @Override
    public boolean addResource(AuthResourceDO authResource) {
        return false;
    }

    @Override
    public boolean isResourceExist(AuthResourceDO authResource) {
        return false;
    }

    @Override
    public boolean updateResource(AuthResourceDO authResource) {
        return false;
    }

    @Override
    public boolean deleteResource(Long resourceId) {
        return false;
    }

    @Override
    public Optional<List<AuthResourceDO>> getAllResource() {
        return Optional.empty();
    }

    @Override
    public Optional<Page<AuthResourceDO>> getPageResource(Integer currentPage, Integer pageSize) {
        return Optional.empty();
    }
}
