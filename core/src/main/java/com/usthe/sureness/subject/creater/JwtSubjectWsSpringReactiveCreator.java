package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.JwtSubject;
import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.SurenessConstant;
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
