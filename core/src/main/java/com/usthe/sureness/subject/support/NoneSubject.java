package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.PrincipalMap;
import com.usthe.sureness.subject.Subject;

import java.util.List;

/**
 * Subject without authentication information
 * @author tomsun28
 * @date 21:03 2019-05-26
 */
public class NoneSubject implements Subject {

    private static final long serialVersionUID = 1L;

    /** remote ip **/
    private String remoteHost;

    /** remote device **/
    private String userAgent;

    /** the uri resource which this user want access **/
    private String targetUri;

    /** the Roles which can access this resource above-targetUri **/
    private List<String> supportRoles;

    private NoneSubject(Builder builder) {
        this.remoteHost = builder.remoteHost;
        this.userAgent = builder.userAgent;
        this.targetUri = builder.targetUri;
        this.supportRoles = builder.supportRoles;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public void setPrincipal(Object var1) {

    }

    @Override
    public PrincipalMap getPrincipalMap() {
        return null;
    }

    @Override
    public void setPrincipalMap(PrincipalMap var1) {

    }

    @Override
    public Object getCredential() {
        return null;
    }

    @Override
    public void setCredential(Object var1) {

    }

    @Override
    public Object getOwnRoles() {
        return null;
    }

    @Override
    public void setOwnRoles(Object var1) {}

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

    @Override
    @SuppressWarnings("unchecked")
    public void setSupportRoles(Object var1) {
        this.supportRoles = (List<String>)var1;
    }

    public String getRemoteHost() {
        return this.remoteHost;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String remoteHost;

        private String userAgent;

        private String targetUri;

        private List<String> supportRoles;

        public Builder setRemoteHost(String remoteHost) {
            this.remoteHost = remoteHost;
            return this;
        }

        public Builder setUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder setTargetUri(String targetUri) {
            this.targetUri = targetUri;
            return this;
        }

        public Builder setSupportRoles(List<String> supportRoles) {
            this.supportRoles = supportRoles;
            return this;
        }

        public NoneSubject build() {
            return new NoneSubject(this);
        }
    }
}
