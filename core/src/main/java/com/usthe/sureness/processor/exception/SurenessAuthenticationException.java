package com.usthe.sureness.processor.exception;

import com.usthe.sureness.util.BaseSurenessException;

/** 认证异常
 * @author tomsun28
 * @date 12:59 2019-03-11
 */
public class SurenessAuthenticationException extends BaseSurenessException {

    public SurenessAuthenticationException(String message) {
        super(message);
    }
}
