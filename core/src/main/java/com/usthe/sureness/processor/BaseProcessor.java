package com.usthe.sureness.processor;

import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.processor.exception.SurenessAuthorizationException;
import com.usthe.sureness.processor.exception.UnauthorizedException;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.subject.Subject;

import java.util.List;

/**
 * abstract processor
 *
 * @author tomsun28
 * @date 12:28 2019-03-13
 */
public abstract class BaseProcessor implements Processor {

    /**
     * Determine whether this Processor supports the corresponding SubjectClass
     *
     * @param var subjectClass
     * @return support true, else false
     */
    @Override
    public abstract boolean canSupportSubjectClass(Class<?> var);

    /**
     * Get the subjectClass supported by this processor
     *
     * @return java.lang.Class? subjectClass
     */
    @Override
    public abstract Class<?> getSupportSubjectClass();

    @Override
    public SubjectSum process(Subject var) throws SurenessAuthenticationException, SurenessAuthorizationException {
        Subject subject = authenticated(var);
        authorized(subject);
        return subject.generateSubjectSummary();
    }

    /**
     * The interface that the authentication will call to complete the authentication
     *
     * @param var subject
     * @return Subject subject
     * @throws SurenessAuthenticationException when authenticate error
     */
    public abstract Subject authenticated(Subject var) throws SurenessAuthenticationException;

    /**
     * The interface that the authorization will call, where the authorization is completed
     *
     * @param var subject
     * @throws SurenessAuthorizationException when authorize error
     */
    @SuppressWarnings("unchecked")
    public void authorized(Subject var) throws SurenessAuthorizationException {
        List<String> ownRoles = (List<String>) var.getOwnRoles();
        List<String> supportRoles = (List<String>) var.getSupportRoles();
        // if null, note that not config this resource
        if (supportRoles == null) {
            return;
        }
        // if config, ownRole must contain the supportRole item
        if (ownRoles != null && supportRoles.stream().anyMatch(ownRoles::contains)) {
            return;
        }
        throw new UnauthorizedException("do not have the role to access resource");
    }
}
