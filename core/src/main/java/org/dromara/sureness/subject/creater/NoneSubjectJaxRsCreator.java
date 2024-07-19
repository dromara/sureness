package org.dromara.sureness.subject.creater;

import javax.ws.rs.container.ContainerRequestContext;

import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.SubjectCreate;
import org.dromara.sureness.subject.support.NoneSubject;

/**
 * the subject creator support creating NoneSubject
 * all request content can create a NoneSubject
 * only support JAX-RS
 * @author tomsun28
 * @date 15:55 2020-02-28
 */
public class NoneSubjectJaxRsCreator implements SubjectCreate {
    @Override
    public boolean canSupportSubject(Object context) {
        return context instanceof ContainerRequestContext;
    }

    @Override
    public Subject createSubject(Object context) {
        String requestUri = ((ContainerRequestContext) context).getUriInfo().getPath();
        String requestType = ((ContainerRequestContext) context).getMethod();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();
        return NoneSubject.builder()
                .setTargetUri(targetUri)
                .build();
    }
}
