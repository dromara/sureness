package com.usthe.sureness.processor;

import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.processor.exception.SurenessAuthorizationException;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;

/**
 * @author tomsun28
 * @date 12:28 2019-03-13
 */
public abstract class BaseProcessor implements Processor{

    @Override
    public boolean canSupportAuTokenClass(Class<?> var) {
        return false;
    }

    @Override
    public Class<?> getSupportAuTokenClass() {
        return null;
    }

    @Override
    public Subject process(SubjectAuToken var) throws SurenessAuthenticationException, SurenessAuthorizationException {
        if (authenticated() && authorized()) {
            return SurenessSecurityManager.getInstance().getSubjectFactory().createSubject(var);
        }
        return null;
    }
    /**
     * description 认证
     *
     * @return boolean
     */
    public abstract boolean authenticated ();

    /**
     * description 鉴权
     *
     * @return boolean
     */
    public abstract boolean authorized();
}
