package com.usthe.sureness.subject;


/**
 *    AuthenticationToken   AuthorizationToken
 * @author tomsun28
 * @date 21:58 2019-01-22
 */
public interface SubjectAuToken {

    /**
     * description TODO
     *
     * @return java.lang.Object
     */
    Object getPrincipal();

    /**
     * description TODO
     *
     * @return java.lang.Object
     */
    Object getCredentials();

    /**
     * description TODO
     *
     * @return java.lang.Object
     */
    Object getRoles();

    /**
     * description TODO
     *
     * @return java.lang.Object
     */
    Object getTargetResource();

}
