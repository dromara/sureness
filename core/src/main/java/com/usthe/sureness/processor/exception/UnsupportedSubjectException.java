package com.usthe.sureness.processor.exception;

/**
 * 认证异常：不支持的TOKEN类型
 * 出现此错误说明加载的 subjectCreator list都无法根据请求创建对应的subject
 * @author tomsun28
 * @date 19:23 2019-03-11
 */
public class UnsupportedSubjectException extends SurenessAuthenticationException {

    public UnsupportedSubjectException(String message) {
        super(message);
    }
}
