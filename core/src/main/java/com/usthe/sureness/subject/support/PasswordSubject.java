package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.Subject;

import java.util.List;

/**
 * 支持 BASIC AUTH和其他账户密码认证鉴权形式的Subject
 * @author tomsun28
 * @date 12:42 2019-03-14
 */
public class PasswordSubject implements Subject {

    private static final long serialVersionUID = 1L;

    /** 用户标识 **/
    private String appId;

    /** 账户密码 **/
    private String password;

    /** 访问用户的IP **/
    private String remoteHost;

    /** 所拥有的角色 在解析完jwt之后把用户角色放到这里 **/
    private List<String> ownRoles;

    /** 所访问资源地址 **/
    private String targetUri;

    /** 所访问资源他支持的角色 **/
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

    public static Builder builder(Subject auToken) {
        return new Builder(auToken);
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
        public Builder(Subject auToken) {
            this.appId = String.valueOf(auToken.getPrincipal());
            this.password = String.valueOf(auToken.getCredentials());
            this.ownRoles = (List<String>) auToken.getOwnRoles();
            this.targetUri = String.valueOf(auToken.getTargetResource());
            this.supportRoles = (List<String>) auToken.getSupportRoles();
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
