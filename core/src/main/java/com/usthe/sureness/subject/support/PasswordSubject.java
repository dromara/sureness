package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.PrincipalMap;
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

    /** the map for principal, add your custom principal **/
    private PrincipalMap principalMap;

    private PasswordSubject(Builder builder) {
        this.appId = builder.appId;
        this.password = builder.password;
        this.remoteHost = builder.remoteHost;
        this.ownRoles = builder.ownRoles;
        this.targetUri = builder.targetUri;
        this.supportRoles = builder.supportRoles;
        this.principalMap = builder.principalMap;
    }

    @Override
    public Object getPrincipal() {
        return this.appId;
    }

    @Override
    public void setPrincipal(Object var1) {
        this.appId = (String) var1;
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
        return this.password;
    }

    @Override
    public void setCredential(Object var1) {
        this.password = (String) var1;
    }

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
        private PrincipalMap principalMap;

        public Builder(String appId, String password) {
            this.appId = appId;
            this.password = password;
        }

        @SuppressWarnings("unchecked")
        public Builder(Subject subject) {
            this.appId = String.valueOf(subject.getPrincipal());
            this.password = String.valueOf(subject.getCredential());
            this.ownRoles = (List<String>) subject.getOwnRoles();
            this.targetUri = String.valueOf(subject.getTargetResource());
            this.supportRoles = (List<String>) subject.getSupportRoles();
            this.principalMap = subject.getPrincipalMap();
        }

        public Builder setPrincipal(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setPrincipalMap(PrincipalMap principalMap) {
            this.principalMap = principalMap;
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
