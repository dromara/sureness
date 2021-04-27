package com.sureness.micronaut.support;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.NoneSubject;
import io.micronaut.http.HttpRequest;

import java.net.InetSocketAddress;


public class NoneSubjectReactiveCreator implements SubjectCreate {
    @Override
    public boolean canSupportSubject(Object context) {
        return context instanceof HttpRequest;
    }

    @Override
    public Subject createSubject(Object context) {
        InetSocketAddress remoteAddress = ((HttpRequest) context).getRemoteAddress();
        String remoteHost = remoteAddress == null ? "" : remoteAddress.getHostString();
        String requestUri = ((HttpRequest) context).getPath();
        String requestType = ((HttpRequest) context).getMethodName();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();
        return NoneSubject.builder().setRemoteHost(remoteHost)
                .setTargetUri(targetUri).build();
    }
}
