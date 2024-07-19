package org.dromara.sureness.micronaut.support;

import io.micronaut.http.HttpRequest;

import java.net.InetSocketAddress;

import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.SubjectCreate;
import org.dromara.sureness.subject.support.NoneSubject;

/**
 * @author tom
 */
public class NoneSubjectReactiveCreator implements SubjectCreate {
    @Override
    public boolean canSupportSubject(Object context) {
        return context instanceof HttpRequest;
    }

    @Override
    public Subject createSubject(Object context) {
        InetSocketAddress remoteAddress = ((HttpRequest) context).getRemoteAddress();
        String remoteHost = remoteAddress.getHostString();
        String requestUri = ((HttpRequest) context).getPath();
        String requestType = ((HttpRequest) context).getMethodName();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();
        return NoneSubject.builder().setRemoteHost(remoteHost)
                .setTargetUri(targetUri).build();
    }
}
