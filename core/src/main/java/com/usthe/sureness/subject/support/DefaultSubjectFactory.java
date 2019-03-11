package com.usthe.sureness.subject.support;

import com.usthe.sureness.mgt.DefaultSecurityManager;
import com.usthe.sureness.mgt.SurenessNoInitException;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import com.usthe.sureness.subject.SubjectFactory;
import com.usthe.sureness.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * @author tomsun28
 * @date 00:40 2019-01-24
 */
public class DefaultSubjectFactory implements SubjectFactory {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultSecurityManager.class);

    private boolean initFlag = false;

    /**
     * description DefaultSubjectAuToken  +  DefaultSubject
     *
     * @param auToken 1
     * @return com.usthe.sureness.subject.Subject
     */
    @Override
    @SuppressWarnings("unchecked")
    public Subject createSubject(SubjectAuToken auToken) {
        String principal = (String)auToken.getPrincipal();
        List<String> roles = (List<String>)auToken.getOwnRoles();
        String targetUri = (String)auToken.getTargetResource();
        Subject subject = new DefaultSubject().setPrincipal(principal)
                .setRoles(roles).setTargetResource(targetUri);
        // 将subject 绑定到localThread变量中
        ThreadContext.bind(subject);
        // 如果是网关认证中心, 之后可以考虑把subject绑定到request请求中,供子系统使用
        return subject;
    }

    /**
     * 由于sureness-core想在设计的时候不加其他特定的依赖, 这里就不实现
     * 在httpRequest中获取内容在填充subject了
     * 填充内容源设计成任何内容对象,不绑定框架
     * 基于web的实现在 sureness-web中实现它
     *
     * @param var1 1
     * @return com.usthe.sureness.subject.SubjectAuToken
     */
    @Override
    public SubjectAuToken createSubjectAuToken(Object var1) {
        initFlag = false;
        return null;
    }

    @Override
    public void checkComponentInit() throws SurenessNoInitException {
        if (!initFlag) {
            throw new SurenessNoInitException("the subjectFactory not complete ye");
        }
    }
}
