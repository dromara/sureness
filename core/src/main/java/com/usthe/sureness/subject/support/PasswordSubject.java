package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.Subject;

import java.util.List;

/**
 * the subject support BASIC AUTH or other username-password AUTH
 * @author tomsun28
 * @date 12:42 2019-03-14
 */
public class PasswordSubject implements Subject {

    private static final long serialVersionUID = 1L;

    /** username **/
    private String appId;

    /** password **/
    private String password;

    /** remote IP **/
    private String remoteHost;

    /** the roles which this user owned **/
    private List<String> ownRoles;

    /** the uri resource which this user want access **/
    private String targetUri;

    /** the Roles which can access this resource above-targetUri **/
    private List<String> supportRoles;


    private PasswordSubject(Builder builder) {
        this.appId = builder.appId;
        this.password = builder.password;
        this.remoteHost = builder.remoteHost;
        this.ownRoles = builder.ownRoles;
        this.targetUri = builder.targetUri;
        this.supportRoles = builder.supportRoles;
    }

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
        return this.ownRoles;
    }

    @Override
    public Object getTargetResource() {
        return this.targetUri;
    }

    @Override
    public Object getSupportRoles() {
        return supportRoles;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setSupportRoles(Object var1) {
        this.supportRoles = (List<String>) var1;
    }

    public static Builder builder(String appId, String password) {
        return new Builder(appId, password);
    }

    public static Builder builder(Subject subject) {
        return new Builder(subject);
    }

    public static class Builder {

        private String appId;
        private String password;
        private String remoteHost;
        private List<String> ownRoles;
        private String targetUri;
        private List<String> supportRoles;

        public Builder(String appId, String password) {
            this.appId = appId;
            this.password = password;
        }

        @SuppressWarnings("unchecked")
        public Builder(Subject subject) {
            this.appId = String.valueOf(subject.getPrincipal());
            this.password = String.valueOf(subject.getCredentials());
            this.ownRoles = (List<String>) subject.getOwnRoles();
            this.targetUri = String.valueOf(subject.getTargetResource());
            this.supportRoles = (List<String>) subject.getSupportRoles();
        }

        public Builder setPrincipal(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setCredentials(String password) {
            this.password = password;
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

        public PasswordSubject build() {
            return new PasswordSubject(this);
        }

    }
}
