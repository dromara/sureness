package com.usthe.sureness.processor.exception;

/**
 * 认证异常：过期的凭证
 * @author tomsun28
 * @date 19:22 2019-03-11
 */
public class ExpiredCredentialsException extends SurenessAuthenticationException {

    public ExpiredCredentialsException(String message) {
        super(message);
    }
}
