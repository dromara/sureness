package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.SubjectAuToken;

/**
 * @author tomsun28
 * @date 12:28 2019-03-14
 */
public class JwtSubjectToken implements SubjectAuToken {


    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getOwnRoles() {
        return null;
    }

    @Override
    public Object getTargetResource() {
        return null;
    }

    @Override
    public Object getSupportRoles() {
        return null;
    }

    @Override
    public void setSupportRoles(Object var1) {

    }
}
