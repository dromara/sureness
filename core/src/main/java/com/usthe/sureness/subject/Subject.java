package com.usthe.sureness.subject;

import javax.naming.AuthenticationException;
import java.util.Collection;

/* *
 * @Author tomsun28
 * @Description
 * @Date 22:59 2019-01-09
 */
public interface Subject {

    void login(SubjectAuToken var1) throws AuthenticationException;

    void logout();
    // 是否认证
    boolean isAuthenticated();
    // 是否有权限
    boolean isAuthorizated();

    Object getPrincipal();

    boolean hasRole(String var1);

    boolean hasAllRoles(Collection<String> var1);

    Object getRoles();

    Object cloneImage();

}
