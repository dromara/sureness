package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.naming.AuthenticationException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author tomsun28
 * @date 22:03 2019-01-22
 */
public class DefaultSubject implements Subject, Serializable {

    private static final long serialVersionUID = 1L;

    private String principal;
    private List<String> roles;
    private String targetResource;

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean hasRole(String var1) {
        return roles.contains(var1);
    }

    @Override
    public boolean hasAllRoles(Collection<String> var1) {
        return roles.containsAll(var1);
    }

    @Override
    public Object getRoles() {
        return roles;
    }

    @Override
    public Object getTargetResource() {
        return targetResource;
    }

    public DefaultSubject setPrincipal(String principal) {
        this.principal = principal;
        return this;
    }

    public DefaultSubject setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    public DefaultSubject setTargetResource(String targetResource) {
        this.targetResource = targetResource;
        return this;
    }


}
