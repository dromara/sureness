package com.usthe.sureness.subject.creater;

import com.usthe.sureness.processor.exception.UnsupportedSubjectException;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.PasswordSubject;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * PasswordSubject 的创建者
 * @author tomsun28
 * @date 23:53 2020-02-27
 */
public class PasswordSubjectCreator implements SubjectCreate {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic";
    private static final int COUNT_2 = 2;

    @Override
    public boolean canSupportSubject(Object context) {
        // todo 判断password 的请求特性
        String authorization = ((HttpServletRequest)context).getHeader(AUTHORIZATION);
        if (authorization != null && authorization.startsWith(BASIC)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Subject createSubject(Object context) {
        String authorization = ((HttpServletRequest)context).getHeader(AUTHORIZATION);
        //basic auth
        String basicAuth = authorization.replace(BASIC, "").trim();
        basicAuth = new String(Base64.getDecoder().decode(basicAuth), StandardCharsets.UTF_8);
        String[] auth = basicAuth.split(":");
        if (auth.length != COUNT_2) {
            throw new UnsupportedSubjectException("can not create token due the request message");
        }
        String username = auth[0];
        if (username == null || "".equals(username)) {
            throw new UnsupportedSubjectException("the appId can not null");
        }
        String password = auth[1];
        String remoteHost = ((HttpServletRequest) context).getRemoteHost();
        String requestUri = ((HttpServletRequest) context).getRequestURI();
        String requestType = ((HttpServletRequest) context).getMethod();
        String targetUri = requestUri.concat("===").concat(requestType.toUpperCase());
        return PasswordSubject.builder(username, password)
                .setRemoteHost(remoteHost)
                .setTargetResource(targetUri)
                .build();
    }
}
