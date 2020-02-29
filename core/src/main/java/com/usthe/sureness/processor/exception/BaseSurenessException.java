package com.usthe.sureness.processor.exception;

/**
 * sureness基础异常,其他异常继承此类
 * @author tomsun28
 * @date 22:40 2019-01-23
 */
public class BaseSurenessException extends RuntimeException {

    public BaseSurenessException(String message) {
        super(message);
    }

}
