package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.Subject;

import java.util.List;

/**
 * 无认证信息的subject
 * @author tomsun28
 * @date 21:03 2019-05-26
 */
public class NoneSubject implements Subject {

    private static final long serialVersionUID = 1L;

    /** 访问用户的IP **/
    private String remoteHost;

    /** 访问用户的设备信息 **/
    private String userAgent;

    /** 所访问资源地址 **/
    private String targetUri;

    /** 所访问资源他支持的角色 **/
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
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getOwnRoles() {
        return null;
    }

    @Override
    public Object getTargetResource() {
        return this.targetUri;
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

        /** 访问用户的IP **/
        private String remoteHost;

        /** 访问用户的设备信息 **/
        private String userAgent;

        /** 所访问资源地址 **/
        private String targetUri;

        /** 所访问资源他支持的角色 **/
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
