package com.usthe.sureness.util;

/* *
 * @Author tomsun28
 * @Description
 * @Date 22:40 2019-01-23
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
