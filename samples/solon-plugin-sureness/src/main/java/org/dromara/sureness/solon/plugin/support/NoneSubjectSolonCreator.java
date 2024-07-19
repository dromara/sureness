package org.dromara.sureness.solon.plugin.support;

import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.SubjectCreate;
import org.dromara.sureness.subject.support.NoneSubject;
import org.noear.solon.core.handle.Context;

/**
 * the creator to create NoneSubject - no auth info
 * all request can be created a NoneSubject by NoneSubjectReactiveCreator
 * only support solon Context
 * @author tomsun28
 * @date 2021/5/7 20:51
 */
public class NoneSubjectSolonCreator implements SubjectCreate {
    @Override
    public boolean canSupportSubject(Object context) {
        return context instanceof Context;
    }

    @Override
    public Subject createSubject(Object context) {
        String remoteHost = ((Context) context).realIp();
        String requestUri = ((Context) context).path();
        String requestType = ((Context) context).method();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();

        return NoneSubject.builder().setRemoteHost(remoteHost)
                .setTargetUri(targetUri).build();
    }
}