package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.SubjectDeclare;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectFactory;
import com.usthe.sureness.util.ThreadContext;
import java.util.List;

/**
 * @author tomsun28
 * @date 00:40 2019-01-24
 */
public abstract class BaseSubjectFactory implements SubjectFactory {

    /**
     * description DefaultSubjectAuToken  +  SurenessSubject
     *
     * @param auToken 1
     * @return com.usthe.sureness.subject.Subject
     */
    @Override
    @SuppressWarnings("unchecked")
    public SubjectDeclare createSubject(Subject auToken) {
        String principal = (String)auToken.getPrincipal();
        List<String> roles = (List<String>)auToken.getOwnRoles();
        String targetUri = (String)auToken.getTargetResource();
        SubjectDeclare subject =  SurenessSubjectDeclare.builder()
                .setTargetResource(targetUri)
                .setRoles(roles)
                .setPrincipal(principal)
                .build();
        // 将subject 绑定到localThread变量中
        ThreadContext.bind(subject);
        // 如果是网关认证中心, 之后可以考虑把subject绑定到request请求中,供子系统使用
        return subject;
    }

}
