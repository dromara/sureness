package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.JwtSubject;
import com.usthe.sureness.util.JsonWebTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;

/**
 * the subject creator support creating JwtSubject from websocket
 * only support JAX-RS
 * @author tomsun28
 * @date 2021/3/2 22:14
 */
public class JwtSubjectWsJaxRsCreator implements SubjectCreate {

    private static final String TOKEN = "token";

    @Override
    public boolean canSupportSubject(Object context) {
        // support token jwt
        // requestUri?token=jwt0-eyJhbGciOiJIUzUxMi --- jwt auth
        if (context instanceof ContainerRequestContext) {
            Object token = ((ContainerRequestContext)context).getProperty(TOKEN);
            return token instanceof String && !JsonWebTokenUtil.isNotJsonWebToken((String) token);
        }
        return false;
    }

    @Override
    public Subject createSubject(Object context) {
        // support token jwt
        // requestUri?token=jwt0-eyJhbGciOiJIUzUxMi --- jwt auth
        Object token = ((ContainerRequestContext)context).getProperty(TOKEN);
        if (token instanceof String) {
            String jwtToken = ((String)token).trim();
            String remoteHost = ((HttpServletRequest) context).getRemoteHost();
            String requestUri = ((HttpServletRequest) context).getRequestURI();
            String requestType = ((HttpServletRequest) context).getMethod();
            String targetUri = requestUri.concat("===").concat(requestType.toLowerCase());
            return JwtSubject.builder(jwtToken)
                    .setRemoteHost(remoteHost)
                    .setTargetResource(targetUri)
                    .build();
        }
        return null;
    }
}
