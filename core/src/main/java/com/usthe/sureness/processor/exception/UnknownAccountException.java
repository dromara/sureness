package com.usthe.sureness.processor.exception;

/**
 * 未知的账户
 * @author tomsun28
 * @date 19:22 2019-03-11
 */
public class UnknownAccountException extends SurenessAuthenticationException {

    public UnknownAccountException(String message) {
        super(message);
    }
}
