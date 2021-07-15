package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.PrincipalMap;
import com.usthe.sureness.subject.Subject;

import java.util.List;

/**
 * the session subject
 * @author tomsun28
 * @date 2021/4/6 19:49
 */
public class SessionSubject implements Subject {

    private static final long serialVersionUID = 1L;

    /** username, appId identifier **/
    private String principal;

    /** the map for principal, add your custom principal **/
    private PrincipalMap principalMap;

    /** the roles which this user owned **/
    private List<String> ownRoles;

    /** remote ip **/
    private String remoteHost;

    /** the uri resource which this user want access **/
    private String targetUri;

    /** the Roles which can access this resource above-targetUri **/
    private List<String> supportRoles;

    public SessionSubject(Builder builder) {
        this.principal = builder.principal;
        this.remoteHost = builder.remoteHost;
        this.ownRoles = builder.ownRoles;
        this.targetUri = builder.targetUri;
        this.supportRoles = builder.supportRoles;
        this.principalMap = builder.principalMap;
    }


    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setPrincipal(Object var1) {
        this.principal = (String) var1;
    }

    @Override
    public PrincipalMap getPrincipalMap() {
        return this.principalMap;
    }

    @Override
    public void setPrincipalMap(PrincipalMap var1) {
        this.principalMap = var1;
    }

    @Override
    public Object getCredential() {
        return null;
    }

    @Override
    public void setCredential(Object var1) {}

    @Override
    public Object getOwnRoles() {
        return this.ownRoles;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setOwnRoles(Object var1) {
        this.ownRoles = (List<String>) var1;
    }

    @Override
    public Object getTargetResource() {
        return this.targetUri;
    }

    @Override
    public void setTargetResource(Object var1) {
        this.targetUri = (String) var1;
    }

    @Override
    public Object getSupportRoles() {
        return this.supportRoles;
    }

    public String getRemoteHost() {
        return this.remoteHost;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void setSupportRoles(Object var1) {
        this.supportRoles = (List<String>) var1;
    }

    public static Builder builder(String principal, List<String> ownRoles) {
        return new Builder(principal, ownRoles);
    }

    public static Builder builder(Subject subject) {
        return new Builder(subject);
    }

    public static class Builder {

        private String principal;
        private String remoteHost;
        private List<String> ownRoles;
        private String targetUri;
        private List<String> supportRoles;
        private PrincipalMap principalMap;

        public Builder(String principal, List<String> ownRoles) {
            this.principal = principal;
            this.ownRoles = ownRoles;
        }

        @SuppressWarnings("unchecked")
        public Builder(Subject subject) {
            this.principal = String.valueOf(subject.getPrincipal());
            this.ownRoles = (List<String>) subject.getOwnRoles();
            this.targetUri = String.valueOf(subject.getTargetResource());
            this.supportRoles = (List<String>) subject.getSupportRoles();
            this.principalMap = subject.getPrincipalMap();
        }

        public Builder setPrincipal(String principal) {
            this.principal = principal;
            return this;
        }

        public Builder setPrincipalMap(PrincipalMap principalMap) {
            this.principalMap = principalMap;
            return this;
        }


        public Builder setTargetResource(String targetUri) {
            this.targetUri = targetUri;
            return this;
        }

        public Builder setOwnRoles(List<String> ownRoles) {
            this.ownRoles = ownRoles;
            return this;
        }

        public Builder setSupportRoles(List<String> supportRoles) {
            this.supportRoles = supportRoles;
            return this;
        }

        public Builder setRemoteHost(String remoteHost) {
            this.remoteHost = remoteHost;
            return this;
        }

        public SessionSubject build() {
            return new SessionSubject(this);
        }
    }
}
