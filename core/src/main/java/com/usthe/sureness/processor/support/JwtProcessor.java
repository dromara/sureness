package com.usthe.sureness.processor.support;

import com.usthe.sureness.processor.BaseProcessor;

/**
 * @author tomsun28
 * @date 12:36 2019-03-13
 */
public class JwtProcessor extends BaseProcessor {

    @Override
    public boolean authenticated() {
        return false;
    }

    @Override
    public boolean authorized() {
        return false;
    }
}
