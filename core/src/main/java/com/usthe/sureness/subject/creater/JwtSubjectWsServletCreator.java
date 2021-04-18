package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.JwtSubject;
import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.SurenessConstant;

import javax.servlet.http.HttpServletRequest;

/**
 * the subject creator support creating JwtSubject from websocket
 * only support HttpServletRequest
 * @author tomsun28
 * @date 2021/3/2 22:14
 */
public class JwtSubjectWsServletCreator implements SubjectCreate {

    @Override
    public boolean canSupportSubject(Object context) {
        // support token jwt
        // requestUri?token=jwt0-eyJhbGciOiJIUzUxMi --- jwt auth
        if (context instanceof HttpServletRequest) {
            String token = ((HttpServletRequest)context).getParameter(SurenessConstant.TOKEN);
            return !JsonWebTokenUtil.isNotJsonWebToken(token);
        }
        return false;
    }

    @Override
    public Subject createSubject(Object context) {
        // support token jwt
        // requestUri?token=jwt0-eyJhbGciOiJIUzUxMi --- jwt auth
        String jwtToken = ((HttpServletRequest)context).getParameter(SurenessConstant.TOKEN);
        if (jwtToken != null) {
            jwtToken = jwtToken.trim();
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
