package com.usthe.sureness.processor.exception;

/**
 * 鉴权异常：基础异常，自定义鉴权相关的异常需要继承此类
 * @author tomsun28
 * @date 13:00 2019-03-11
 */
public class SurenessAuthorizationException extends BaseSurenessException {

    public SurenessAuthorizationException(String message) {
        super(message);
    }
}
