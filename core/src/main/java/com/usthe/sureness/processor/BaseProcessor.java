package com.usthe.sureness.processor;

import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.processor.exception.SurenessAuthorizationException;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;

/**
 * @author tomsun28
 * @date 12:28 2019-03-13
 */
public abstract class BaseProcessor implements Processor{

    /**
     * description 判断此Processor是否支持对应的AuTokenClass
     * 支持才能让此Processor处理对应的AuTokenClass
     *
     * @param var 1
     * @return boolean
     */
    @Override
    public abstract boolean canSupportAuTokenClass(Class<?> var);

    /**
     * description 获取此Processor能支持的AuTokenClass
     *
     * @return java.lang.Class<?>
     */
    @Override
    public abstract Class<?> getSupportAuTokenClass();

    @Override
    public Subject process(SubjectAuToken var) throws SurenessAuthenticationException, SurenessAuthorizationException {
        if (authenticated(var) && authorized(var)) {
            return createSubject(var);
        }
        return null;
    }
    /**
     * description 认证会调用的接口，在这里面完成认证
     * @param var 1
     * @return boolean
     * @throws SurenessAuthenticationException when发生认证相关异常
     */
    public abstract boolean authenticated (SubjectAuToken var) throws SurenessAuthenticationException;

    /**
     * description 鉴权会调用的接口，在这里面完成鉴权
     * @param var 1
     * @return boolean
     * @throws SurenessAuthorizationException when发生鉴权相关异常
     */
    public abstract boolean authorized(SubjectAuToken var) throws SurenessAuthorizationException;

    /**
     * description 当认证鉴权完成通过后，通过token创建subject
     *
     * @param var 1
     * @return com.usthe.sureness.subject.Subject
     */
    public abstract Subject createSubject(SubjectAuToken var);
}
