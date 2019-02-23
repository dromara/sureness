package com.usthe.sureness.mgt;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import com.usthe.sureness.subject.SubjectContext;
import javax.security.sasl.AuthenticationException;

/* *
 * @Author tomsun28
 * @Description
 * @Date 22:33 2019-01-23
 */
public interface SecurityManager {

    Subject login(Subject var1, SubjectAuToken var2) throws AuthenticationException;


    Subject createSubject(SubjectContext var1);

}
