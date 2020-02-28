package com.usthe.sureness.processor.exception;

/**
 * 扩展Sureness异常 此类异常与其子异常同 sureness 内部配置或组件本身异常相关
 * @author tomsun28
 * @date 16:30 2020-02-28
 */
public class ExtSurenessException extends RuntimeException {

    public ExtSurenessException(String message) {
        super(message);
    }
}
