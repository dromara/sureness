package com.usthe.sureness.processor.exception;

/**
 * 认证异常：禁用锁定账户异常
 * @author tomsun28
 * @date 19:23 2019-03-11
 */
public class DisabledAccountException extends SurenessAuthenticationException {

    public DisabledAccountException(String message) {
        super(message);
    }
}
