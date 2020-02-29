package com.usthe.sureness.processor.exception;

/**
 * 鉴权异常：没有访问对应资源的权限异常
 * @author tomsun28
 * @date 19:25 2019-03-11
 */
public class UnauthorizedException extends SurenessAuthorizationException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
