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
     * account credential, eg:password
     *
     * @return credential
     */
    Object getCredential();

    /**
     * get the roles owned by this account
     *
     * @return roles
     */
    Object getOwnRoles();

    /**
     * get the target resource uri which this account want access
     *
     * @return resource uri
     */
    Object getTargetResource();

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
        List<String> roles = (List<String>)getOwnRoles();
        String targetUri = (String)getTargetResource();
        return   SurenessSubjectSum.builder()
                .setTargetResource(targetUri)
                .setRoles(roles)
                .setPrincipal(principal)
                .build();
    }
}
