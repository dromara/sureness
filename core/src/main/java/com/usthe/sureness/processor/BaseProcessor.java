package com.usthe.sureness.processor;

import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.processor.exception.SurenessAuthorizationException;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.support.SurenessSubjectSum;
import com.usthe.sureness.util.ThreadContext;

import java.util.List;

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
    public SubjectSum process(Subject var) throws SurenessAuthenticationException, SurenessAuthorizationException {
        authorized(authenticated(var));
        return createSubject(var);
    }
    /**
     * description 认证会调用的接口，在这里面完成认证
     * @param var 1
     * @return SubjectAuToken auToken
     * @throws SurenessAuthenticationException when发生认证相关异常
     */
    public abstract Subject authenticated (Subject var) throws SurenessAuthenticationException;

    /**
     * description 鉴权会调用的接口，在这里面完成鉴权
     * @param var 1
     * @throws SurenessAuthorizationException when发生鉴权相关异常
     */
    public abstract void authorized(Subject var) throws SurenessAuthorizationException;

    /**
     * description 当认证鉴权完成通过后，通过token创建subject
     *
     * @param var 1
     * @return com.usthe.sureness.subject.Subject
     */
    @SuppressWarnings("unchecked")
    private SubjectSum createSubject(Subject var) {
        SurenessSubjectSum subject = SurenessSubjectSum.builder()
                .setPrincipal(String.valueOf(var.getPrincipal()))
                .setTargetResource(String.valueOf(var.getTargetResource()))
                .setRoles((List<String>) var.getOwnRoles())
                .build();
        // 将subject 绑定到localThread变量中
        ThreadContext.bind(subject);
        // 如果是网关认证中心, 之后可以考虑把subject绑定到request请求中,供子系统使用
        return subject;
    }
}
