package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.JwtSubject;
import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.SurenessCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;


/**
 * JwtSubject creator
 * only support JAX-RS
 * @author tomsun28
 * @date 23:58 2020-02-27
 */
public class JwtSubjectJaxRsCreator implements SubjectCreate {

    private static final Logger logger = LoggerFactory.getLogger(JwtSubjectJaxRsCreator.class);

    private static final String BEARER = "Bearer";
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public boolean canSupportSubject(Object context) {
        // support bearer jwt
        // ("Authorization", "Bearer eyJhbGciOiJIUzUxMi...")  --- jwt auth
        if (context instanceof ContainerRequestContext) {
            String authorization = ((ContainerRequestContext)context).getHeaderString(AUTHORIZATION);
            if (authorization != null && authorization.startsWith(BEARER)) {
                String jwtValue = authorization.replace(BEARER, "").trim();
                return !JsonWebTokenUtil.isNotJsonWebToken(jwtValue);
            }
        }
        return false;
    }

    @Override
    public Subject createSubject(Object context) {
        String authorization = ((ContainerRequestContext)context).getHeaderString(AUTHORIZATION);
        if (authorization != null && authorization.startsWith(BEARER)) {
            // jwt token
            String jwtValue = authorization.replace(BEARER, "").trim();
            if (JsonWebTokenUtil.isNotJsonWebToken(jwtValue)) {
                if (logger.isInfoEnabled()) {
                    logger.info("can not create JwtSubject by this request message, is not jwt");
                }
                return null;
            }
            String requestUri = ((ContainerRequestContext) context).getUriInfo().getPath();
            String requestType = ((ContainerRequestContext) context).getMethod();
            String targetUri = requestUri.concat("===").concat(requestType.toLowerCase());
            String userAgent = SurenessCommonUtil.findUserAgent((ContainerRequestContext) context);
            return JwtSubject.builder(jwtValue)
                    .setTargetResource(targetUri)
                    .setUserAgent(userAgent)
                    .build();
        }
        return null;
    }
}
