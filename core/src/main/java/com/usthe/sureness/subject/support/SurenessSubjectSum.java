package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.SubjectSum;
import java.util.Collection;
import java.util.List;

/**
 * @author tomsun28
 * @date 22:03 2019-01-22
 */
public class SurenessSubjectSum implements SubjectSum {

    private static final long serialVersionUID = 1L;
    /**
     * username appId phoneNum email
     */
    private String principal;
    /**
     * 当前账户所拥有的角色
     */
    private List<String> roles;
    /**
     * 当前账户这次请求他所请求的资源(即url===method)
     */
    private String targetResource;


    private SurenessSubjectSum(Builder builder) {
        this.principal = builder.principal;
        this.roles = builder.roles;
        this.targetResource = builder.targetResource;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean hasRole(String var1) {
        return roles.contains(var1);
    }

    @Override
    public boolean hasAllRoles(Collection<String> var1) {
        return roles.containsAll(var1);
    }

    @Override
    public Object getRoles() {
        return roles;
    }

    @Override
    public Object getTargetResource() {
        return targetResource;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String principal;
        private List<String> roles;
        private String targetResource;

        public Builder setPrincipal(String principal) {
            this.principal = principal;
            return this;
        }

        public Builder setTargetResource(String targetResource) {
            this.targetResource = targetResource;
            return this;
        }

        public Builder setRoles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public SurenessSubjectSum build() {
            return new SurenessSubjectSum(this);
        }


    }

}