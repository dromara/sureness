package com.usthe.sureness.processor.exception;

/**
 * Authentication exception: Bad credentials
 * @author tomsun28
 * @date 19:21 2019-03-11
 */
public class IncorrectCredentialsException extends SurenessAuthenticationException {

    public IncorrectCredentialsException(String message) {
        super(message);
    }
}
