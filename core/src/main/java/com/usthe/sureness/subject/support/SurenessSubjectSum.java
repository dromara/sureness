package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.PrincipalMap;
import com.usthe.sureness.subject.SubjectSum;
import java.util.Collection;
import java.util.List;

/**
 * subject summary
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
     * map for principals, key-value
     */
    private PrincipalMap principalMap;
    /**
     * the roles which this user owned
     */
    private List<String> roles;
    /**
     * the uri resource which this user want access(url===method)
     */
    private String targetResource;


    private SurenessSubjectSum(Builder builder) {
        this.principal = builder.principal;
        this.principalMap = builder.principalMap;
        this.roles = builder.roles;
        this.targetResource = builder.targetResource;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public PrincipalMap getPrincipalMap() {
        return principalMap;
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

    @Override
    public String toString() {
        return "SurenessSubjectSum{" +
                "principal='" + principal + '\'' +
                ", principalMap=" + principalMap +
                ", roles=" + roles +
                ", targetResource='" + targetResource + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String principal;
        private PrincipalMap principalMap;
        private List<String> roles;
        private String targetResource;

        public Builder setPrincipal(String principal) {
            this.principal = principal;
            return this;
        }

        public Builder setPrincipalMap(PrincipalMap principalMap) {
            this.principalMap = principalMap;
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