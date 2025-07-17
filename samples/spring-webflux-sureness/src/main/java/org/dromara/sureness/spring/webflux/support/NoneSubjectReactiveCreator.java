package org.dromara.sureness.spring.webflux.support;

import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.SubjectCreate;
import org.dromara.sureness.subject.support.NoneSubject;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetSocketAddress;

/**
 * the creator to create NoneSubject - no auth info
 * all request can be created a NoneSubject by NoneSubjectReactiveCreator
 * only support ServerHttpRequest
 * @author tomsun28
 * @date 22:55 2020-09-29
 */
public class NoneSubjectReactiveCreator implements SubjectCreate {
    @Override
    public boolean canSupportSubject(Object context) {
        return context instanceof ServerHttpRequest;
    }

    @Override
    public Subject createSubject(Object context) {
        InetSocketAddress remoteAddress = ((ServerHttpRequest) context).getRemoteAddress();
        String remoteHost = remoteAddress == null ? "" : remoteAddress.getHostString();
        String requestUri = ((ServerHttpRequest) context).getPath().value();
        String requestType = ((ServerHttpRequest) context).getMethod().name();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();
        return NoneSubject.builder().setRemoteHost(remoteHost)
                .setTargetUri(targetUri).build();
    }
}
