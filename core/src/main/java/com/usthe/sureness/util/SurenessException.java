package com.usthe.sureness.util;

/**
 * @author tomsun28
 * @date 22:40 2019-01-23
 */
public class SurenessException extends RuntimeException {

    public SurenessException() {
        super();
    }

    public SurenessException(String message) {
        super(message);
    }

    public SurenessException(String message, Throwable cause) {
        super(message, cause);
    }

    public SurenessException(Throwable cause) {
        super(cause);
    }

}
