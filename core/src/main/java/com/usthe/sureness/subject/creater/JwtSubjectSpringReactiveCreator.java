package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.JwtSubject;
import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.SurenessConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetSocketAddress;


/**
 * JwtSubject creator
 * only support reactive server-side HTTP request
 * @author tomsun28
 * @date 23:58 2020-09-29
 */
public class JwtSubjectSpringReactiveCreator implements SubjectCreate {

    private static final Logger logger = LoggerFactory.getLogger(JwtSubjectSpringReactiveCreator.class);

    @Override
    public boolean canSupportSubject(Object context) {
        // support bearer jwt
        // ("Authorization", "Bearer eyJhbGciOiJIUzUxMi...")  --- jwt auth
        if (context instanceof ServerHttpRequest) {
            String authorization = ((ServerHttpRequest)context).getHeaders().getFirst(SurenessConstant.AUTHORIZATION);
            if (authorization != null && authorization.startsWith(SurenessConstant.BEARER)) {
                String jwtValue = authorization.replace(SurenessConstant.BEARER, "").trim();
                return !JsonWebTokenUtil.isNotJsonWebToken(jwtValue);
            }
        }
        return false;
    }

    @Override
    public Subject createSubject(Object context) {
        String authorization = ((ServerHttpRequest)context).getHeaders().getFirst(SurenessConstant.AUTHORIZATION);
        if (authorization != null && authorization.startsWith(SurenessConstant.BEARER)) {
            // jwt token
            String jwtValue = authorization.replace(SurenessConstant.BEARER, "").trim();
            if (JsonWebTokenUtil.isNotJsonWebToken(jwtValue)) {
                if (logger.isInfoEnabled()) {
                    logger.info("can not create JwtSubject by this request message, is not jwt");
                }
                return null;
            }
            InetSocketAddress remoteAddress = ((ServerHttpRequest) context).getRemoteAddress();
            String remoteHost = remoteAddress == null ? "" : remoteAddress.getHostString();
            String requestUri = ((ServerHttpRequest) context).getPath().value();
            String requestType = ((ServerHttpRequest) context).getMethodValue();
            String targetUri = requestUri.concat("===").concat(requestType.toLowerCase());
            return JwtSubject.builder(jwtValue)
                    .setRemoteHost(remoteHost)
                    .setTargetResource(targetUri)
                    .build();
        }
        return null;
    }
}
