package com.usthe.sureness.processor.exception;

/**
 * 认证异常：认证鉴权中没有找到对应token支持的Processor
 * @author tomsun28
 * @date 12:50 2019-03-12
 */
public class ProcessorNotFoundException extends BaseSurenessException {

    public ProcessorNotFoundException(String message) {
        super(message);
    }
}
