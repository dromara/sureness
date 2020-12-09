package com.usthe.sureness.processor.exception;

/**
 * sureness basic exception, other exceptions inherit this exception
 * @author tomsun28
 * @date 22:40 2019-01-23
 */
public class BaseSurenessException extends RuntimeException {

    public BaseSurenessException(String message) {
        super(message);
    }

}
