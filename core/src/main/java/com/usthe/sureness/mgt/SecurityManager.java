package com.usthe.sureness.mgt;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import com.usthe.sureness.subject.SubjectContext;

/**
 * @author tomsun28
 * @date 22:33 2019-01-23
 */
public interface SecurityManager {

    /**
     * description TODO
     */
    void checkIn();

    /**
     * description TODO
     *
     * @param var1 1
     * @param var2 2
     * @return com.usthe.sureness.subject.Subject
     */
    Subject login(Subject var1, SubjectAuToken var2);


    /**
     * description TODO
     *
     * @param var1 1
     * @return com.usthe.sureness.subject.Subject
     */
    Subject createSubject(SubjectContext var1);

}
