package com.usthe.sureness.processor.exception;

/**
 * Authorization exception: No permission to access the resource
 * @author tomsun28
 * @date 19:25 2019-03-11
 */
public class UnauthorizedException extends SurenessAuthorizationException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
