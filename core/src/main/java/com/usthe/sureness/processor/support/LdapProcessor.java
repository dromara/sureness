package com.usthe.sureness.processor.support;

import com.usthe.sureness.processor.BaseProcessor;
import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.subject.Subject;

/**
 * @author Ed
 * @create 2021-08-15 11:31
 */
public class LdapProcessor extends BaseProcessor {
    @Override
    public boolean canSupportSubjectClass(Class<?> var) {
        return false;
    }

    @Override
    public Class<?> getSupportSubjectClass() {
        return null;
    }

    @Override
    public Subject authenticated(Subject var) throws SurenessAuthenticationException {
        return null;
    }
}
