package org.dromara.sureness.handler;

import org.dromara.sureness.subject.SubjectSum;

/**
 * the handler process after auth success
 * @author tomsun28
 * @date 2021/4/5 17:37
 */
public interface SuccessHandler {

    /**
     * handle after success process
     * @param subjectSum subject
     * @param request request
     */
    void processHandler(SubjectSum subjectSum, Object request);

}
