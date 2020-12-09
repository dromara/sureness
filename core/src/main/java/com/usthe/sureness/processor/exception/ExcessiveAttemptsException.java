package com.usthe.sureness.processor.exception;

/**
 * Authentication exception: Too many attempts after regular authentication failure
 * @author tomsun28
 * @date 19:24 2019-03-11
 */
public class ExcessiveAttemptsException extends SurenessAuthenticationException {

    public ExcessiveAttemptsException(String message) {
        super(message);
    }
}
