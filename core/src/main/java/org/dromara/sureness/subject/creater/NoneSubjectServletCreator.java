package org.dromara.sureness.subject.creater;

import jakarta.servlet.http.HttpServletRequest;

import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.SubjectCreate;
import org.dromara.sureness.subject.support.NoneSubject;
import org.dromara.sureness.util.ServletUtil;

/**
 * the subject creator support creating NoneSubject
 * all request content can create a NoneSubject
 * only support HttpServletRequest
 * @author tomsun28
 * @date 15:55 2020-02-28
 */
public class NoneSubjectServletCreator implements SubjectCreate {
    @Override
    public boolean canSupportSubject(Object context) {
        return context instanceof HttpServletRequest;
    }

    @Override
    public Subject createSubject(Object context) {
        String remoteHost = ((HttpServletRequest) context).getRemoteHost();
        String requestUri = ServletUtil.getRequestUri((HttpServletRequest) context);
        String requestType = ((HttpServletRequest) context).getMethod();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();
        return NoneSubject.builder().setRemoteHost(remoteHost)
                .setTargetUri(targetUri)
                .build();
    }
}
