package com.usthe.sureness.subject;

import javax.naming.AuthenticationException;
import java.util.Collection;

/**
 * Subject 只有内容  没有认证鉴权动作
 * 此subject只有在 认证鉴权 成功后下发
 * @author tomsun28
 * @date 22:59 2019-01-09
 */
public interface Subject {

    /**
     * description 获取认证对象的账户
     * @return java.lang.Object
     */
    Object getPrincipal();

    /**
     * description 是否有特定角色
     *
     * @param var1 1
     * @return boolean
     */
    boolean hasRole(String var1);

    /**
     * description 是否有对应全部角色
     *
     * @param var1 1
     * @return boolean
     */
    boolean hasAllRoles(Collection<String> var1);

    /**
     * description 获取拥有的角色
     *
     * @return java.lang.Object
     */
    Object getRoles();

    /**
     * description 获取这次想访问目标资源
     *
     * @return java.lang.Object
     */
    Object getTargetResource();

}
