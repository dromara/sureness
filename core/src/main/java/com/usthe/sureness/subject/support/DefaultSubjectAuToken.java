package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.SubjectAuToken;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tomsun28
 * @date 23:28 2019-01-23
 */
public class DefaultSubjectAuToken implements SubjectAuToken {

    private static final long serialVersionUID = 1L;

    private String appId;
    private String credential;
    private List<String> roles;
    private List<String> supportRoles;
    /**
     *  url===httpMethod
     */
    private String targetUri;

    private DefaultSubjectAuToken(Builder builder) {
        this.appId = builder.appId;
        this.credential = builder.credential;
        this.roles = builder.roles;
        this.targetUri = builder.targetUri;
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
        return this.roles;
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
        this.supportRoles = (List<String>) var1;
    }


    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String appId;
        private String credential;
        private List<String> roles;
        private String targetUri;

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

        public Builder addRole(String role) {
            if (roles == null) {
                this.roles = new LinkedList<>();
            }
            this.roles.add(role);
            return this;
        }

        public Builder addRoles(List<String> roles) {
            if (this.roles == null) {
                this.roles = new LinkedList<>();
            }
            this.roles.addAll(roles);
            return this;
        }

        public DefaultSubjectAuToken build() {
            return new DefaultSubjectAuToken(this);
        }

    }
}
