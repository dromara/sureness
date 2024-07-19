package org.dromara.sureness.processor.support;

import org.dromara.sureness.processor.BaseProcessor;
import org.dromara.sureness.processor.exception.SurenessAuthenticationException;
import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.support.SessionSubject;

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
