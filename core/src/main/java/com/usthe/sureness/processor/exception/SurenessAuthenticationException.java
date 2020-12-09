package com.usthe.sureness.processor.exception;

/**
 * Authentication exception
 * Basic exceptions, exceptions related to custom authentication need to inherit
 * @author tomsun28
 * @date 12:59 2019-03-11
 */
public class SurenessAuthenticationException extends BaseSurenessException {

    public SurenessAuthenticationException(String message) {
        super(message);
    }
}
