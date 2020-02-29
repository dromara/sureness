package com.usthe.sureness.processor.exception;

/**
 * 认证异常：错误的凭证
 * @author tomsun28
 * @date 19:21 2019-03-11
 */
public class IncorrectCredentialsException extends SurenessAuthenticationException {

    public IncorrectCredentialsException(String message) {
        super(message);
    }
}
