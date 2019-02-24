package com.usthe.sureness.mgt.support;

import com.usthe.sureness.mgt.SecurityManager;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import com.usthe.sureness.subject.SubjectContext;
import com.usthe.sureness.subject.SubjectFactory;

import javax.security.sasl.AuthenticationException;

/* *
 * @Author tomsun28
 * @Description
 * @Date 00:41 2019-01-24
 */
public class DefaultSecurityManager implements SecurityManager {

    private SubjectFactory subjectFactory;



    public Subject login(Subject var1, SubjectAuToken var2) {
        return null;
    }

    public Subject createSubject(SubjectContext var1) {
        return null;
    }
}
