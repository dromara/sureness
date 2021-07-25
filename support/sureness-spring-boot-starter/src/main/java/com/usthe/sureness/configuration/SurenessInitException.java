package com.usthe.sureness.configuration;

import com.usthe.sureness.processor.exception.ExtSurenessException;

/**
 * sureness init exception
 * @author tomsun28
 * @date 2021/7/24 14:36
 */
public class SurenessInitException extends ExtSurenessException {

    public SurenessInitException(String message) {
        super(message);
    }
}
