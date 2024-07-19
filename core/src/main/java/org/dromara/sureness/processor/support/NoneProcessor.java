package org.dromara.sureness.processor.support;

import org.dromara.sureness.processor.BaseProcessor;
import org.dromara.sureness.processor.exception.SurenessAuthenticationException;
import org.dromara.sureness.processor.exception.UnknownAccountException;
import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.support.NoneSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * the processor support nonToken
 * @author tomsun28
 * @date 21:12 2019-05-26
 */
public class NoneProcessor extends BaseProcessor {

    /** logger **/
    private static final Logger logger = LoggerFactory.getLogger(NoneProcessor.class);

    @Override
    public boolean canSupportSubjectClass(Class<?> var) {
        return var == NoneSubject.class;
    }

    @Override
    public Class<?> getSupportSubjectClass() {
        return NoneSubject.class;
    }

    @Override
    public Subject authenticated(Subject var) throws SurenessAuthenticationException {
        throw new UnknownAccountException("the request do not have the auth detail, please input your auth");
    }
}
