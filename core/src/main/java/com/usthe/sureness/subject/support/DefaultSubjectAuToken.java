package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.SubjectAuToken;
import java.util.LinkedList;
import java.util.List;

/* *
 * @Author tomsun28
 * @Description
 * @Date 23:28 2019-01-23
 */
public class DefaultSubjectAuToken implements SubjectAuToken {

    private String appId;
    private String credential;
    private List<String> roles;
    // url===httpMethod
    private String targetUri;

    private DefaultSubjectAuToken(Builder builder) {
        this.appId = builder.appId;
        this.credential = builder.credential;
        this.roles = builder.roles;
        this.targetUri = builder.targetUri;
    }

    public Object getPrincipal() {
        return this.appId;
    }

    public Object getCredentials() {
        return this.credential;
    }

    public Object getRoles() {
        return this.roles;
    }

    public Object getTargetResource() {
        return this.targetUri;
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
                this.roles = new LinkedList<String>();
            }
            this.roles.add(role);
            return this;
        }

        public Builder addRoles(List<String> roles) {
            if (this.roles == null) {
                this.roles = new LinkedList<String>();
            }
            this.roles.addAll(roles);
            return this;
        }

        public DefaultSubjectAuToken build() {
            return new DefaultSubjectAuToken(this);
        }

    }
}
