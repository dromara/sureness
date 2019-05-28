package com.usthe.sureness.subject;

/**
 * 包含subject的基础信息  可以通过subjectContext创建subject
 * @author tomsun28
 * @date 22:57 2019-01-23
 */
@Deprecated
public interface SubjectContext {

    /**
     * description TODO
     *
     * @return java.lang.String
     */
    String getUri();
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

}
