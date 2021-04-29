package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.PasswordSubject;
import com.usthe.sureness.util.SurenessConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * the creator to create PasswordSubject(basic auth)
 * only support reactive server-side HTTP request
 * org.springframework.http.server.reactive.ServerHttpRequest
 * @author tomsun28
 * @date 23:53 2020-02-27
 */
public class BasicSubjectSpringReactiveCreator implements SubjectCreate {

    private static final Logger logger = LoggerFactory.getLogger(BasicSubjectSpringReactiveCreator.class);

    private static final int COUNT_2 = 2;

    @Override
    public boolean canSupportSubject(Object context) {
        // ("Authorization", "Basic YWRtaW46YWRtaW4=")        --- basic auth
        if (context instanceof ServerHttpRequest) {
            String authorization = ((ServerHttpRequest)context).getHeaders().getFirst(SurenessConstant.AUTHORIZATION);
            return authorization != null && authorization.startsWith(SurenessConstant.BASIC);
        } else {
            return false;
        }
    }

    @Override
    public Subject createSubject(Object context) {
        String authorization = ((ServerHttpRequest)context).getHeaders().getFirst(SurenessConstant.AUTHORIZATION);
        if (authorization == null) {
            return null;
        }
        //basic auth
        String basicAuth = authorization.replace(SurenessConstant.BASIC, "").trim();
        basicAuth = new String(Base64.getDecoder().decode(basicAuth), StandardCharsets.UTF_8);
        String[] auth = basicAuth.split(":");
        if (auth.length != COUNT_2) {
            if (logger.isInfoEnabled()) {
                logger.info("can not create basic auth PasswordSubject by this request message");
            }
            return null;
        }
        String username = auth[0];
        if (username == null || "".equals(username)) {
            if (logger.isInfoEnabled()) {
                logger.info("can not create basic auth PasswordSubject by this request message, appId can not null");
            }
            return null;
        }
        String password = auth[1];
        InetSocketAddress remoteAddress = ((ServerHttpRequest) context).getRemoteAddress();
        String remoteHost = remoteAddress == null ? "" : remoteAddress.getHostString();
        String requestUri = ((ServerHttpRequest) context).getPath().value();
        String requestType = ((ServerHttpRequest) context).getMethodValue();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();
        return PasswordSubject.builder(username, password)
                .setRemoteHost(remoteHost)
                .setTargetResource(targetUri)
                .build();
    }
}
