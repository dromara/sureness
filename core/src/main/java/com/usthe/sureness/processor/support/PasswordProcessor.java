package com.usthe.sureness.processor.support;

import com.usthe.sureness.processor.BaseProcessor;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import com.usthe.sureness.subject.support.PasswordSubjectToken;

/**
 * 支持 username password 类型token的处理器实例
 * @author tomsun28
 * @date 12:38 2019-03-13
 */
public class PasswordProcessor extends BaseProcessor {

    @Override
    public boolean canSupportAuTokenClass(Class<?> var) {
        return var != null && var == PasswordSubjectToken.class;
    }

    @Override
    public Class<?> getSupportAuTokenClass() {
        // 这里只支持passwordToken
        // username/appId/email/phoneNum + password
        return PasswordSubjectToken.class;
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
