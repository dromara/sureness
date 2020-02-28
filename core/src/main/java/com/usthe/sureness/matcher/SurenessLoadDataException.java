package com.usthe.sureness.matcher;

import com.usthe.sureness.processor.exception.ExtSurenessException;

/**
 * 数据源加载异常
 * @author tomsun28
 * @date 00:00 2019-03-11
 */
public class SurenessLoadDataException extends ExtSurenessException {

    public SurenessLoadDataException(String message) {
        super(message);
    }
}
