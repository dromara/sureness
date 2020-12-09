package com.usthe.sureness.processor.exception;

/**
 * Authentication exception: Disable lock account exception
 * @author tomsun28
 * @date 19:23 2019-03-11
 */
public class DisabledAccountException extends SurenessAuthenticationException {

    public DisabledAccountException(String message) {
        super(message);
    }
}
