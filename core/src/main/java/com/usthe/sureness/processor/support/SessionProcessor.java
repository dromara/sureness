package com.usthe.sureness.processor.support;

import com.usthe.sureness.processor.BaseProcessor;
import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.support.SessionSubject;

/**
 * process session auth
 * @author tomsun28
 * @date 2021/4/6 20:17
 */
public class SessionProcessor extends BaseProcessor {


    @Override
    public boolean canSupportSubjectClass(Class<?> var) {
        return var == SessionSubject.class;
    }

    @Override
    public Class<?> getSupportSubjectClass() {
        return SessionSubject.class;
    }

    @Override
    public Subject authenticated(Subject var) throws SurenessAuthenticationException {
        return var;
    }
}
