package com.usthe.sureness.subject;


/* *
 * @Author tomsun28
 * @Description   AuthenticationToken   AuthorizationToken
 * @Date 21:58 2019-01-22
 */
public interface SubjectAuToken {

    Object getPrincipal();

    Object getCredentials();

    Object getRoles();

    Object getTargetResource();

}
