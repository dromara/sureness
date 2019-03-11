package com.usthe.sureness.processor.exception;

/**
 * 不支持的TOKEN类型
 * @author tomsun28
 * @date 19:23 2019-03-11
 */
public class UnsupportedTokenException extends SurenessAuthenticationException {

    public UnsupportedTokenException(String message) {
        super(message);
    }
}
