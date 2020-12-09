package com.usthe.sureness.processor.exception;

/**
 * Authorization exception
 * Basic exceptions, exceptions related to custom authorization need to inherit
 * @author tomsun28
 * @date 13:00 2019-03-11
 */
public class SurenessAuthorizationException extends BaseSurenessException {

    public SurenessAuthorizationException(String message) {
        super(message);
    }
}
