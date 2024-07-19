package org.dromara.sureness.mgt;

import java.util.List;

import org.dromara.sureness.processor.exception.BaseSurenessException;
import org.dromara.sureness.processor.exception.UnsupportedSubjectException;
import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.SubjectSum;

/**
 * Authentication authorization entrance interface
 * @author tomsun28
 * @date 22:33 2019-01-23
 */
public interface SecurityManager {

    /**
     * auth entrance, put the request in authentication and authorization process
     * @param request request
     * @return com.usthe.sureness.subject.Subject
     * return null when the request uri===method is in excluded resource
     * @throws BaseSurenessException sureness exception
     */
    SubjectSum checkIn(Object request) throws BaseSurenessException;

    /**
     * Create the corresponding type of subject according to the request information
     * @param var1 request eg: httpRequest
     * @return com.usthe.sureness.subject.Subject
     * @throws UnsupportedSubjectException unSupport this request
     */
     List<Subject> createSubject(Object var1) throws UnsupportedSubjectException;

}
