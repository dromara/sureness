package com.usthe.sureness.mgt;

import com.usthe.sureness.processor.exception.UnsupportedSubjectException;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.processor.exception.BaseSurenessException;

import java.util.List;

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
