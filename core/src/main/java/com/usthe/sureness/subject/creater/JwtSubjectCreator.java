package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.JwtSubject;
import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.SurenessCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * JwtSubject creator
 * @author tomsun28
 * @date 23:58 2020-02-27
 */
public class JwtSubjectCreator implements SubjectCreate {

    private static final Logger logger = LoggerFactory.getLogger(JwtSubjectCreator.class);

    private static final String BEARER = "Bearer";
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public boolean canSupportSubject(Object context) {
        // support bearer jwt
        // ("Authorization", "Bearer eyJhbGciOiJIUzUxMi...")  --- jwt auth
        if (context instanceof HttpServletRequest) {
            String authorization = ((HttpServletRequest)context).getHeader(AUTHORIZATION);
            if (authorization != null && authorization.startsWith(BEARER)) {
                String jwtValue = authorization.replace(BEARER, "").trim();
                return !JsonWebTokenUtil.isNotJsonWebToken(jwtValue);
            }
        }
        return false;
    }

    @Override
    public Subject createSubject(Object context) {
        String authorization = ((HttpServletRequest)context).getHeader(AUTHORIZATION);
        if (authorization != null && authorization.startsWith(BEARER)) {
            // jwt token
            String jwtValue = authorization.replace(BEARER, "").trim();
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
            String userAgent = SurenessCommonUtil.findUserAgent((HttpServletRequest) context);
            return JwtSubject.builder(jwtValue)
                    .setRemoteHost(remoteHost)
                    .setTargetResource(targetUri)
                    .setUserAgent(userAgent)
                    .build();
        }
        return null;
    }
}
