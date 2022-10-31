package sureness.service;


import org.springframework.data.domain.Page;
import sureness.pojo.entity.AuthResourceDO;
import sureness.pojo.entity.AuthRoleDO;

import java.util.List;
import java.util.Optional;

/**
 * @author tomsun28
 * @date 00:14 2019-08-01
 */
public interface RoleService {

    /**
     * Determine whether the role already exists
     * @param authRole role
     * @return existed-true no-false
     */
    boolean isRoleExist(AuthRoleDO authRole);

    /**
     * add role
     * @param authRole role
     * @return add success-true failed-false
     */
    boolean addRole(AuthRoleDO authRole);

    /**
     * update role
     * @param authRole role
     * @return success-true failed-false
     */
    boolean updateRole(AuthRoleDO authRole);

    /**
     * delete role
     * @param roleId role ID
     * @return success-true failed-false
     */
    boolean deleteRole(Long roleId);

    /**
     * get all role list
     * @return role list
     */
    Optional<List<AuthRoleDO>> getAllRole();

    /**
     * get roles page
     * @param currentPage current page
     * @param pageSize page size
     * @return Page of roles
     */
    Page<AuthRoleDO> getPageRole(Integer currentPage, Integer pageSize);

    /**
     * get pageable resources which this role owned
     * @param roleId role ID
     * @param currentPage current page
     * @param pageSize page size
     * @return Page of resources
     */
    Page<AuthResourceDO> getPageResourceOwnRole(Long roleId, Integer currentPage, Integer pageSize);

    /**
     * get pageable resources which this role not owned
     * @param roleId role ID
     * @param currentPage current page
     * @param pageSize page size
     * @return Page of resources
     */
    Page<AuthResourceDO> getPageResourceNotOwnRole(Long roleId, Integer currentPage, Integer pageSize);

    /**
     * authority this resource to this role
     * @param roleId role ID
     * @param resourceId resource ID
     */
    void authorityRoleResource(Long roleId, Long resourceId);

    /**
     * unAuthority this resource in this role
     * @param roleId role ID
     * @param resourceId resource ID
     */
    void deleteAuthorityRoleResource(Long roleId, Long resourceId);

}
