package com.usthe.sureness.subject;


import java.io.Serializable;

/**
 *    AuthenticationToken   AuthorizationToken
 * @author tomsun28
 * @date 21:58 2019-01-22
 */
public interface Subject extends Serializable {

    /**
     * description 账户名  string
     *
     * @return 账户标识
     */
    Object getPrincipal();

    /**
     * description 认证证书
     *
     * @return 对应账户的认证证书或秘钥
     */
    Object getCredentials();

    /**
     * description 对应账户所拥有的角色
     *
     * @return 角色信息
     */
    Object getOwnRoles();

    /**
     * description 需要访问的资源
     *
     * @return 资源信息
     */
    Object getTargetResource();

    /**
     * description 获取token 在url-role树中匹配出来的roles
     * 访问 getTargetResource() 所支持的 roles
     *
     * @return 访问此资源所需的角色信息
     */
    Object getSupportRoles();

    /**
     * description 设置所匹配出的role
     *
     * @param var1 所支持角色
     */
    void setSupportRoles(Object var1);

}
