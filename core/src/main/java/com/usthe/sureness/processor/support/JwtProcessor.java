package com.usthe.sureness.processor.support;

import com.usthe.sureness.processor.BaseProcessor;
import com.usthe.sureness.subject.support.JwtSubjectToken;

/**
 * @author tomsun28
 * @date 12:36 2019-03-13
 */
public class JwtProcessor extends BaseProcessor {

    @Override
    public Class<?> getSupportAuTokenClass() {
        return JwtSubjectToken.class;
    }

    @Override
    public boolean authenticated() {
        return false;
    }

    @Override
    public boolean authorized() {
        return false;
    }
}
