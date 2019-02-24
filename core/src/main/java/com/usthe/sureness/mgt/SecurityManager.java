package com.usthe.sureness.mgt;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import com.usthe.sureness.subject.SubjectContext;

/* *
 * @Author tomsun28
 * @Description
 * @Date 22:33 2019-01-23
 */
public interface SecurityManager {

    Subject login(Subject var1, SubjectAuToken var2);


    Subject createSubject(SubjectContext var1);

}
