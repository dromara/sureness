package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.SubjectAuToken;

import java.util.List;

/**
 * @author tomsun28
 * @date 12:42 2019-03-14
 */
public class PasswordSubjectToken implements SubjectAuToken {

    private static final long serialVersionUID = 1L;
    private String appId;
    private String password;
    private String host;
    private String targetUri;
    private List<String> roles;
    private List<String> supportRoles;


    @Override
    public Object getPrincipal() {
        return this.appId;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public Object getOwnRoles() {
        return this.roles;
    }

    @Override
    public Object getTargetResource() {
        return this.targetUri;
    }

    @Override
    public Object getSupportRoles() {
        return supportRoles;
    }

    @Override
    public void setSupportRoles(Object var1) {
    }
}
