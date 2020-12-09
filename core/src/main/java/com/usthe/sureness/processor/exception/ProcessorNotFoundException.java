package com.usthe.sureness.processor.exception;

/**
 * Authentication exception: there is no processor support this subject
 * @author tomsun28
 * @date 12:50 2019-03-12
 */
public class ProcessorNotFoundException extends BaseSurenessException {

    public ProcessorNotFoundException(String message) {
        super(message);
    }
}
