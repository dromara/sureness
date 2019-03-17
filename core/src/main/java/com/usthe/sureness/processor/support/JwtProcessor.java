package com.usthe.sureness.processor.support;

import com.usthe.sureness.processor.BaseProcessor;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import com.usthe.sureness.subject.support.JwtSubjectToken;

/**
 * 支持 appId + jwt 的token的处理器实例
 * @author tomsun28
 * @date 12:36 2019-03-13
 */
public class JwtProcessor extends BaseProcessor {

    @Override
    public boolean canSupportAuTokenClass(Class<?> var) {
        return var != null && var == JwtSubjectToken.class;
    }

    @Override
    public Class<?> getSupportAuTokenClass() {
        return JwtSubjectToken.class;
    }

    @Override
    public boolean authenticated(SubjectAuToken var) {
        return false;
    }

    @Override
    public boolean authorized(SubjectAuToken var) {
        return false;
    }

    @Override
    public Subject createSubject(SubjectAuToken var) {
        return null;
    }

}
