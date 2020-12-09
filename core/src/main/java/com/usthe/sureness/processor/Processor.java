package com.usthe.sureness.processor;

import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.processor.exception.SurenessAuthorizationException;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.subject.Subject;

/**
 * processor
 * Process the incoming authentication object, and verify the account and authority of it
 * @author tomsun28
 * @date 21:54 2019-03-06
 */
public interface Processor {

    /**
     * Determine whether this Processor supports the corresponding SubjectClass
     *
     * @param var subjectClass
     * @return support true, else false
     */
    boolean canSupportSubjectClass(Class<?> var);


    /**
     * Get the subjectClass supported by this processor
     *
     * @return java.lang.Class? subjectClass
     */
    Class<?> getSupportSubjectClass();

    /**
     * process the subject
     * @param var subject
     * @throws SurenessAuthenticationException when Authentication error
     * @throws SurenessAuthorizationException when Authorization error
     * @return com.usthe.sureness.subject.Subject
     */
    SubjectSum process(Subject var) throws SurenessAuthenticationException, SurenessAuthorizationException;
}
