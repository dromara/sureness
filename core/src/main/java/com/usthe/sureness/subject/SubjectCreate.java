package com.usthe.sureness.subject;

/**
 * subject creator interface
 * creator subject
 * @author tomsun28
 * @date 23:44 2020-02-27
 */
public interface SubjectCreate {


    /**
     * By obtaining concise input context information,
     * determine whether it can support this type of subject creation
     * @param context request context
     * @return can support return true, else false
     */
    boolean canSupportSubject(Object context);

    /**
     * Create a subject by obtaining the context content
     * @param context request context
     * @return subject return null when can not create by context
     */
    Subject createSubject(Object context);
}
