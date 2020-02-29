package com.usthe.sureness.processor.exception;

/**
 * 认证异常，为基础异常，自定义认证相关的sureness异常需要继承此类
 * @author tomsun28
 * @date 12:59 2019-03-11
 */
public class SurenessAuthenticationException extends BaseSurenessException {

    public SurenessAuthenticationException(String message) {
        super(message);
    }
}
