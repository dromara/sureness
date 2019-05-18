package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.SubjectAuToken;
import java.util.List;

/**
 * 默认的认证TOKEN
 * @author tomsun28
 * @date 23:28 2019-01-23
 */
public class DefaultSubjectAuToken implements SubjectAuToken {

    /** 日志操作 **/
    private static final long serialVersionUID = 1L;

    /** 用户表示ID **/
    private String appId;

    /** 用户账户秘钥 **/
    private String credential;

    /** 用户所拥有角色 **/
    private List<String> ownRoles;

    /** url===httpMethod **/
    private String targetUri;

    /** 访问此资源所支持的角色 **/
    private List<String> supportRoles;

    private DefaultSubjectAuToken(Builder builder) {
        this.appId = builder.appId;
        this.credential = builder.credential;
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
        return this.credential;
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
        return this.supportRoles;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setSupportRoles(Object var1) {
        this.supportRoles = (List<String>) var1;
    }


    public static Builder builder(String appId, String credential) {
        return new Builder(appId, credential);
    }

    public static Builder builder(SubjectAuToken auToken) {
        return new Builder(auToken);
    }

    public static class Builder {

        private String appId;
        private String credential;
        private List<String> ownRoles;
        private String targetUri;
        private List<String> supportRoles;

        public Builder(String appId, String credential) {
            this.appId = appId;
            this.credential = credential;
        }

        @SuppressWarnings("unchecked")
        public Builder(SubjectAuToken auToken) {
            this.appId = String.valueOf(auToken.getPrincipal());
            this.credential = String.valueOf(auToken.getCredentials());
            this.ownRoles = (List<String>) auToken.getOwnRoles();
            this.targetUri = String.valueOf(auToken.getTargetResource());
            this.supportRoles = (List<String>) auToken.getSupportRoles();
        }

        public Builder setPrincipal(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setCredentials(String credential) {
            this.credential = credential;
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

        public DefaultSubjectAuToken build() {
            return new DefaultSubjectAuToken(this);
        }

    }
}
