package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.JwtSubject;
import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.SurenessConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;


/**
 * the subject creator support creating JwtSubject
 * only support JAX-RS
 * @author tomsun28
 * @date 23:58 2020-02-27
 */
public class JwtSubjectJaxRsCreator implements SubjectCreate {

    private static final Logger logger = LoggerFactory.getLogger(JwtSubjectJaxRsCreator.class);

    @Override
    public boolean canSupportSubject(Object context) {
        // support bearer jwt
        // ("Authorization", "Bearer eyJhbGciOiJIUzUxMi...")  --- jwt auth
        if (context instanceof ContainerRequestContext) {
            String authorization = ((ContainerRequestContext)context).getHeaderString(SurenessConstant.AUTHORIZATION);
            if (authorization != null && authorization.startsWith(SurenessConstant.BEARER)) {
                String jwtValue = authorization.replace(SurenessConstant.BEARER, "").trim();
                return !JsonWebTokenUtil.isNotJsonWebToken(jwtValue);
            }
        }
        return false;
    }

    @Override
    public Subject createSubject(Object context) {
        String authorization = ((ContainerRequestContext)context).getHeaderString(SurenessConstant.AUTHORIZATION);
        if (authorization != null && authorization.startsWith(SurenessConstant.BEARER)) {
            // jwt token
            String jwtValue = authorization.replace(SurenessConstant.BEARER, "").trim();
            if (JsonWebTokenUtil.isNotJsonWebToken(jwtValue)) {
                if (logger.isInfoEnabled()) {
                    logger.info("can not create JwtSubject by this request message, is not jwt");
                }
                return null;
            }
            String requestUri = ((ContainerRequestContext) context).getUriInfo().getPath();
            String requestType = ((ContainerRequestContext) context).getMethod();
            String targetUri = requestUri.concat("===").concat(requestType.toLowerCase());
            return JwtSubject.builder(jwtValue)
                    .setTargetResource(targetUri)
                    .build();
        }
        return null;
    }
}
