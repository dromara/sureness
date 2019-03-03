package com.usthe.sureness.mgt.support;

import com.usthe.sureness.mgt.SecurityManager;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import com.usthe.sureness.subject.SubjectContext;
import com.usthe.sureness.subject.SubjectFactory;


/**
 *
 * @author tomsun28
 * @date 15:30 2019-03-03
 */
public class DefaultSecurityManager implements SecurityManager {


    /**
     *  subject 工厂
     */
    private SubjectFactory subjectFactory;


    /**
     * description TODO
     *
     */
    @Override
    public void checkIn() {

    }


    /**
     * description TODO
     *
     * @param var1 1
     * @param var2 2
     * @return com.usthe.sureness.subject.Subject
     */
    @Override
    public Subject login(Subject var1, SubjectAuToken var2) {
        return null;
    }

    /**
     * description TODO
     *
     * @param var1 1
     * @return com.usthe.sureness.subject.Subject
     */
    @Override
    public Subject createSubject(SubjectContext var1) {
        return null;
    }
}
