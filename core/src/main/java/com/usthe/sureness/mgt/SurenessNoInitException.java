package com.usthe.sureness.mgt;


import com.usthe.sureness.processor.exception.ExtSurenessException;

/**
 * sureness not init exception
 * @author tomsun28
 * @date 18:00 2019-03-10
 */
public class SurenessNoInitException extends ExtSurenessException {

    public SurenessNoInitException(String message) {
        super(message);
    }
}
