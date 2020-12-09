package com.usthe.sureness.matcher;

import com.usthe.sureness.processor.exception.ExtSurenessException;

/**
 * datasource load exception
 * @author tomsun28
 * @date 00:00 2019-03-11
 */
public class SurenessLoadDataException extends ExtSurenessException {

    public SurenessLoadDataException(String message) {
        super(message);
    }
}
