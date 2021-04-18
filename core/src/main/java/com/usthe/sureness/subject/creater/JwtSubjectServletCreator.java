package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.JwtSubject;
import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.SurenessConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * the subject creator support creating JwtSubject
 * only support HttpServletRequest
 * @author tomsun28
 * @date 23:58 2020-02-27
 */
public class JwtSubjectServletCreator implements SubjectCreate {

    private static final Logger logger = LoggerFactory.getLogger(JwtSubjectServletCreator.class);

    @Override
    public boolean canSupportSubject(Object context) {
        // support bearer jwt
        // ("Authorization", "Bearer eyJhbGciOiJIUzUxMi...")  --- jwt auth
        if (context instanceof HttpServletRequest) {
            String authorization = ((HttpServletRequest)context).getHeader(SurenessConstant.AUTHORIZATION);
            if (authorization != null && authorization.startsWith(SurenessConstant.BEARER)) {
                String jwtValue = authorization.replace(SurenessConstant.BEARER, "").trim();
                return !JsonWebTokenUtil.isNotJsonWebToken(jwtValue);
            }
        }
        return false;
    }

    @Override
    public Subject createSubject(Object context) {
        String authorization = ((HttpServletRequest)context).getHeader(SurenessConstant.AUTHORIZATION);
        if (authorization != null && authorization.startsWith(SurenessConstant.BEARER)) {
            // jwt token
            String jwtValue = authorization.replace(SurenessConstant.BEARER, "").trim();
            if (JsonWebTokenUtil.isNotJsonWebToken(jwtValue)) {
                if (logger.isInfoEnabled()) {
                    logger.info("can not create JwtSubject by this request message, is not jwt");
                }
                return null;
            }
            String remoteHost = ((HttpServletRequest) context).getRemoteHost();
            String requestUri = ((HttpServletRequest) context).getRequestURI();
            String requestType = ((HttpServletRequest) context).getMethod();
            String targetUri = requestUri.concat("===").concat(requestType.toLowerCase());
            return JwtSubject.builder(jwtValue)
                    .setRemoteHost(remoteHost)
                    .setTargetResource(targetUri)
                    .build();
        }
        return null;
    }
}
