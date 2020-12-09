package com.usthe.sureness.processor.exception;

/**
 * Extended Sureness exception.
 * Related to the internal configuration of sureness or the component itself.
 * @author tomsun28
 * @date 16:30 2020-02-28
 */
public class ExtSurenessException extends RuntimeException {

    public ExtSurenessException(String message) {
        super(message);
    }
}
