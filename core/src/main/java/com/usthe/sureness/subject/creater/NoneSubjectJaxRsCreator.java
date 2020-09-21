package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.NoneSubject;
import com.usthe.sureness.util.SurenessCommonUtil;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * 无认证信息的subject creator
 * 所有请求都能创建出一个NoneSubject
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
        String userAgent = SurenessCommonUtil.findUserAgent((ContainerRequestContext) context);
        return NoneSubject.builder()
                .setTargetUri(targetUri)
                .setUserAgent(userAgent).build();
    }
}
