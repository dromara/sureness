package com.usthe.sureness.subject;


import com.usthe.sureness.subject.support.SurenessSubjectSum;

import java.io.Serializable;
import java.util.List;

/**
 * AuthenticationToken AuthorizationToken subject
 * @author tomsun28
 * @date 21:58 2019-01-22
 */
public interface Subject extends Serializable {

    /**
     * account appId, eg:username
     *
     * @return identifier appId
     */
    Object getPrincipal();

    /**
     * set account appId, eg:username
     * @param var1 principal
     */
    void setPrincipal(Object var1);

    /**
     * map for principals, key-value
     * eg: id-idValue, customName-value, issuer-value
     * @return principal map info
     */
    PrincipalMap getPrincipalMap();

    /**
     * set map for principals, key-value
     * @param var1 principal map info
     */
    void setPrincipalMap(PrincipalMap var1);

    /**
     * account credential, eg:password
     *
     * @return credential
     */
    Object getCredential();

    /**
     * set account credential, eg:password
     * @param var1 credential
     */
    void setCredential(Object var1);

    /**
     * get the roles owned by this account
     *
     * @return roles
     */
    Object getOwnRoles();

    /**
     * set the roles owned by this account
     * @param var1 own roles
     */
    void setOwnRoles(Object var1);

    /**
     * get the target resource uri which this account want access
     *
     * @return resource uri
     */
    Object getTargetResource();

    /**
     * set the target resource uri which this account want access
     * @param var1 resource uri
     */
    void setTargetResource(Object var1);

    /**
     * get the Roles which can access this resource above-targetUri
     *
     * @return roles
     */
    Object getSupportRoles();

    /**
     * set the Roles which can access this resource above-targetUri
     *
     * @param var1 support roles
     */
    void setSupportRoles(Object var1);

    /**
     *
     * Simplify content subject to create subjectSummary
     *
     * @return com.usthe.sureness.subject.Subject
     */
    @SuppressWarnings("unchecked")
    default SubjectSum generateSubjectSummary() {
        String principal = (String)getPrincipal();
        PrincipalMap principalMap = getPrincipalMap();
        List<String> roles = (List<String>)getOwnRoles();
        String targetUri = (String)getTargetResource();
        return   SurenessSubjectSum.builder()
                .setTargetResource(targetUri)
                .setRoles(roles)
                .setPrincipal(principal)
                .setPrincipalMap(principalMap)
                .build();
    }
}
