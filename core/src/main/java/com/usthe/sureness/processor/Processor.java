package com.usthe.sureness.processor;

import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.processor.exception.SurenessAuthorizationException;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.subject.Subject;

/**
 * 处理器，处理传进来的认证对象,对其进行认证账号与权限
 * @author tomsun28
 * @date 21:54 2019-03-06
 */
public interface Processor {

    /**
     * description 判断此Processor是否支持对应的AuTokenClass
     * 支持才能让此Processor处理对应的AuTokenClass
     *
     * @param var 1
     * @return boolean
     */
    boolean canSupportAuTokenClass(Class<?> var);


    /**
     * description 获取此Processor能支持的AuTokenClass
     *
     * @return java.lang.Class?
     */
    Class<?> getSupportAuTokenClass();

    /**
     * description 进入处理
     * @param var 1
     * @throws SurenessAuthenticationException when
     * @throws SurenessAuthorizationException when
     * @return com.usthe.sureness.subject.Subject
     */
    SubjectSum process(Subject var) throws SurenessAuthenticationException, SurenessAuthorizationException;
}
