package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.PrincipalMap;
import com.usthe.sureness.subject.Subject;

import java.util.List;

/**
 * the subject support jwt auth
 * @author tomsun28
 * @date 12:28 2019-03-14
 */
public class JwtSubject implements Subject {

    private static final long serialVersionUID = 1L;

    /** appId **/
    private String appId;

    /** json web token **/
    private String jwt;

    /** remote ip **/
    private String remoteHost;

    /** remote device **/
    private String userAgent;

    /** the roles which this user owned **/
    private List<String> ownRoles;

    /** the uri resource which this user want access **/
    private String targetUri;

    /** the Roles which can access this resource above-targetUri **/
    private List<String> supportRoles;

    /** the map for principal, add your custom principal **/
    private PrincipalMap principalMap;

    private JwtSubject(Builder builder) {
        this.appId = builder.appId;
        this.jwt = builder.jwt;
        this.remoteHost = builder.remoteHost;
        this.userAgent = builder.userAgent;
        this.ownRoles = builder.ownRoles;
        this.supportRoles = builder.supportRoles;
        this.targetUri = builder.targetUri;
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
        return this.jwt;
    }

    @Override
    public void setCredential(Object var1) {
        this.jwt = (String) var1;
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
        return this.supportRoles;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setSupportRoles(Object var1) {
        this.supportRoles = (List<String>) var1;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public static Builder builder(String jwt) {
        return new Builder(jwt);
    }

    public static Builder builder(Subject subject) {
        return new Builder(subject);
    }

    public static class Builder {

        private String appId;
        private String jwt;
        private String remoteHost;
        private String userAgent;
        private List<String> ownRoles;
        private String targetUri;
        private List<String> supportRoles;
        private PrincipalMap principalMap;

        public Builder(String jwt) {
            this.jwt = jwt;
        }

        @SuppressWarnings("unchecked")
        public Builder(Subject subject) {
            this.appId = String.valueOf(subject.getPrincipal());
            this.jwt = String.valueOf(subject.getCredential());
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

        public Builder setCredentials(String jwt) {
            this.jwt = jwt;
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

        public Builder setUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public JwtSubject build() {
            return new JwtSubject(this);
        }

    }
}
