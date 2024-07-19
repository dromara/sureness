package org.dromara.sureness.subject.creater;

import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.SubjectCreate;
import org.dromara.sureness.subject.support.JwtSubject;
import org.dromara.sureness.util.JsonWebTokenUtil;
import org.dromara.sureness.util.SurenessConstant;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetSocketAddress;


/**
 * the subject creator support creating JwtSubject from websocket
 * only support reactive server-side HTTP request websocket
 * @author tomsun28
 * @date 2021/3/2 22:14
 */
public class JwtSubjectWsSpringReactiveCreator implements SubjectCreate {

    @Override
    public boolean canSupportSubject(Object context) {
        // support token jwt
        // requestUri?token=jwt0-eyJhbGciOiJIUzUxMi --- jwt auth
        if (context instanceof ServerHttpRequest) {
            String token = ((ServerHttpRequest)context).getQueryParams().getFirst(SurenessConstant.TOKEN);
            return !JsonWebTokenUtil.isNotJsonWebToken(token);
        }
        return false;
    }

    @Override
    public Subject createSubject(Object context) {
        // support token jwt
        // requestUri?token=jwt0-eyJhbGciOiJIUzUxMi --- jwt auth
        String token = ((ServerHttpRequest)context).getQueryParams().getFirst(SurenessConstant.TOKEN);
        if (token != null) {
            String jwtToken = token.trim();
            InetSocketAddress remoteAddress = ((ServerHttpRequest) context).getRemoteAddress();
            String remoteHost = remoteAddress == null ? "" : remoteAddress.getHostString();
            String requestUri = ((ServerHttpRequest) context).getPath().value();
            String requestType = ((ServerHttpRequest) context).getMethodValue();
            String targetUri = requestUri.concat("===").concat(requestType.toLowerCase());
            return JwtSubject.builder(jwtToken)
                    .setRemoteHost(remoteHost)
                    .setTargetResource(targetUri)
                    .build();
        }
        return null;
    }
}
