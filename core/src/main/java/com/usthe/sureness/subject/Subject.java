package com.usthe.sureness.subject;

import javax.naming.AuthenticationException;
import java.util.Collection;

/**
 * @author tomsun28
 * @date 22:59 2019-01-09
 */
public interface Subject {

    /**
     * description TODO
     *
     * @param var1 1
     * @throws AuthenticationException  when
     */
    void login(SubjectAuToken var1) throws AuthenticationException;

    /**
     * description TODO
     */
    void logout();


    /**
     * 是否认证
     * @return boolean
     */
    boolean isAuthenticated();
    /**
     * description 是否有权限
     * @return boolean
     */
    boolean isAuthorizated();

    /**
     * description TODO
     * @return java.lang.Object
     */
    Object getPrincipal();

    /**
     * description TODO
     *
     * @param var1 1
     * @return boolean
     */
    boolean hasRole(String var1);

    /**
     * description TODO
     *
     * @param var1 1
     * @return boolean
     */
    boolean hasAllRoles(Collection<String> var1);

    /**
     * description TODO
     *
     * @return java.lang.Object
     */
    Object getRoles();

    /**
     * description TODO
     *
     * @return java.lang.Object
     */
    Object cloneImage();

}
