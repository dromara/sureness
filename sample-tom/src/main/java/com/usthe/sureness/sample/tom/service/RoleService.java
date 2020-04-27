package com.usthe.sureness.sample.tom.service;

import com.usthe.sureness.sample.tom.pojo.entity.AuthResourceDO;
import com.usthe.sureness.sample.tom.pojo.entity.AuthRoleDO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * @author tomsun28
 * @date 00:14 2019-08-01
 */
public interface RoleService {

    /**
     * 判断此角色是否存在
     * @param authRole 角色
     * @return 已存在true 不存在false
     */
    boolean isRoleExist(AuthRoleDO authRole);

    /**
     * 增加角色
     * @param authRole 角色
     * @return 增加成功true 失败false
     */
    boolean addRole(AuthRoleDO authRole);

    /**
     * 更新角色
     * @param authRole 角色
     * @return 更新成功返回true 失败false
     */
    boolean updateRole(AuthRoleDO authRole);

    /**
     * 删除角色
     * @param roleId 角色ID
     * @return 删除成功true 不存在失败false
     */
    boolean deleteRole(Long roleId);

    /**
     * 获取所有角色
     * @return 角色list
     */
    Optional<List<AuthRoleDO>> getAllRole();

    /**
     * 获取角色的分页
     * @param currentPage 当前页码
     * @param pageSize 页大小
     * @return 角色的分页
     */
    Page<AuthRoleDO> getPageRole(Integer currentPage, Integer pageSize);

    /**
     * 获取角色所拥有API资源的分页
     * @param roleId 角色ID
     * @param currentPage 当前页码
     * @param pageSize 页大小
     * @return 角色的分页
     */
    Page<AuthResourceDO> getPageResourceOwnRole(Long roleId, Integer currentPage, Integer pageSize);

    /**
     * 将资源授权给角色
     * @param roleId 角色ID
     * @param resourceId 资源ID
     */
    void authorityRoleResource(Long roleId, Long resourceId);

    /**
     * 删除授权给角色此资源
     * @param roleId 角色ID
     * @param resourceId 资源ID
     */
    void deleteAuthorityRoleResource(Long roleId, Long resourceId);
}
