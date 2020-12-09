package com.usthe.sureness.processor.exception;

/**
 * Authentication exception: Unknown account exception
 * @author tomsun28
 * @date 19:22 2019-03-11
 */
public class UnknownAccountException extends SurenessAuthenticationException {

    public UnknownAccountException(String message) {
        super(message);
    }
}
