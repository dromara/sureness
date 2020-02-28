package com.usthe.sureness.subject;

import java.io.Serializable;
import java.util.Collection;

/**
 * Subject 简单概要 包含一些基本信息内容
 * @author tomsun28
 * @date 22:59 2019-01-09
 */
public interface SubjectSum extends Serializable {

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
