package com.usthe.sureness.subject.creater;

import com.usthe.sureness.mgt.jwt.DefaultJwtManager;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.JwtSubject;
import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.SurenessConstant;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * the subject creator support creating JwtSubject from websocket
 * only support JAX-RS
 * @author tomsun28
 * @date 2021/3/2 22:14
 */
public class JwtSubjectWsJaxRsCreator implements SubjectCreate {

    @Override
    public boolean canSupportSubject(Object context) {
        // support token jwt
        // requestUri?token=jwt0-eyJhbGciOiJIUzUxMi --- jwt auth
        if (context instanceof ContainerRequestContext) {
            Object token = ((ContainerRequestContext)context).getProperty(SurenessConstant.TOKEN);
            return token instanceof String && !JsonWebTokenUtil.isNotJsonWebToken((String) token);
        }
        return false;
    }

    @Override
    public Subject createSubject(Object context) {
        // support token jwt
        // requestUri?token=jwt0-eyJhbGciOiJIUzUxMi --- jwt auth
        Object token = ((ContainerRequestContext)context).getProperty(SurenessConstant.TOKEN);
        if (token instanceof String) {
            String jwtToken = ((String)token).trim();
            // add jwtManager to ensure that jwt does not expire
            DefaultJwtManager defaultJwtManager = DefaultJwtManager.getInstance();
            defaultJwtManager.checkJwt(jwtToken);
            String requestUri = ((ContainerRequestContext) context).getUriInfo().getPath();
            String requestType = ((ContainerRequestContext) context).getMethod();
            String targetUri = requestUri.concat("===").concat(requestType.toLowerCase());
            return JwtSubject.builder(jwtToken)
                    .setTargetResource(targetUri)
                    .build();
        }
        return null;
    }
}
