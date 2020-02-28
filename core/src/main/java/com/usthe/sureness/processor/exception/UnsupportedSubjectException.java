package com.usthe.sureness.processor.exception;

/**
 * 不支持的TOKEN类型
 * 出现此错误说明加载 subjectCreator没有将最基本的NoneSubjectCreator加载
 * 导致根据请求信息创建不了特点subject后兜底的NoneSubject也创建不了
 * @author tomsun28
 * @date 19:23 2019-03-11
 */
public class UnsupportedSubjectException extends SurenessAuthenticationException {

    public UnsupportedSubjectException(String message) {
        super(message);
    }
}
