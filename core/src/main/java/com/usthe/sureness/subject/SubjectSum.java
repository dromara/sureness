package com.usthe.sureness.subject;

import java.io.Serializable;
import java.util.Collection;

/**
 * Subject summary, contains some basic information content
 * @author tomsun28
 * @date 22:59 2019-01-09
 */
public interface SubjectSum extends Serializable {

    /**
     * Get the account of the authentication object
     * @return java.lang.Object
     */
    Object getPrincipal();

    /**
     * map for principals, key-value
     * eg: id-idValue, customName-value, issuer-value
     * @return principal map info
     */
    PrincipalMap getPrincipalMap();

    /**
     * Determine whether it has role - var1
     *
     * @param var1 role
     * @return boolean has-true, no have - false
     */
    boolean hasRole(String var1);

    /**
     * Determine whether it has all roles - var1
     * @param var1 role list
     * @return boolean has-true, no have - false
     */
    boolean hasAllRoles(Collection<String> var1);

    /**
     * get the roles owned by it
     *
     * @return java.lang.Object
     */
    Object getRoles();

    /**
     * get the target resource uri which it want access
     * @return java.lang.Object
     */
    Object getTargetResource();

}
