package com.usthe.sureness.sample.tom.service;

import com.usthe.sureness.sample.tom.pojo.entity.AuthResourceDO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * @author tomsun28
 * @date 00:13 2019-08-01
 */
public interface ResourceService {

    /**
     * 增加uri资源
     * @param authResource 资源
     * @return 增加成功true 失败false
     */
    boolean addResource(AuthResourceDO authResource);

    /**
     * 判断此资源是否已经存在
     * @param authResource 资源
     * @return 已经存在true 不存在false
     */
    boolean isResourceExist(AuthResourceDO authResource);

    /**
     * 更新uri资源
     * @param authResource 资源
     * @return 更新成功true 失败false
     */
    boolean updateResource(AuthResourceDO authResource);

    /**
     * 删除指定uri资源 不存在也是删除成功
     * @param resourceId 资源ID
     * @return 删除成功true 失败false
     */
    boolean deleteResource(Long resourceId);

    /**
     * 获取所有资源
     * @return 资源list
     */
    Optional<List<AuthResourceDO>> getAllResource();

    /**
     * 获取分页的资源页
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @return 资源的分页
     */
    Optional<Page<AuthResourceDO>> getPageResource(Integer currentPage, Integer pageSize);
}
