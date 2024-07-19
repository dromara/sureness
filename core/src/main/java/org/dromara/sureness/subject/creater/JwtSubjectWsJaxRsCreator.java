package org.dromara.sureness.subject.creater;

import javax.ws.rs.container.ContainerRequestContext;

import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.SubjectCreate;
import org.dromara.sureness.subject.support.JwtSubject;
import org.dromara.sureness.util.JsonWebTokenUtil;
import org.dromara.sureness.util.SurenessConstant;

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
