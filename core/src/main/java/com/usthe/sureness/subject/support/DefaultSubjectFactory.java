package com.usthe.sureness.subject.support;

import com.usthe.sureness.mgt.DefaultSecurityManager;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import com.usthe.sureness.subject.SubjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tomsun28
 * @date 00:40 2019-01-24
 */
public class DefaultSubjectFactory implements SubjectFactory {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultSecurityManager.class);

    @Override
    public Subject createSubject(SubjectAuToken auToken) {


        return null;
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
        return null;
    }
}
