package com.usthe.sureness.processor.exception;

/**
 * 认证异常：常规认证失败后尝试次数太多异常
 * @author tomsun28
 * @date 19:24 2019-03-11
 */
public class ExcessiveAttemptsException extends SurenessAuthenticationException {

    public ExcessiveAttemptsException(String message) {
        super(message);
    }
}
