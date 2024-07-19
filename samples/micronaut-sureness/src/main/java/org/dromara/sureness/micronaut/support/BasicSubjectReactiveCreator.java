package org.dromara.sureness.micronaut.support;

import io.micronaut.http.HttpRequest;

import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.SubjectCreate;
import org.dromara.sureness.subject.support.PasswordSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author tom
 */
public class BasicSubjectReactiveCreator implements SubjectCreate {

    private static final Logger logger = LoggerFactory.getLogger(BasicSubjectReactiveCreator.class);

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic";
    private static final int COUNT_2 = 2;

    @Override
    public boolean canSupportSubject(Object context) {
        // ("Authorization", "Basic YWRtaW46YWRtaW4=")        --- basic auth
        if (context instanceof HttpRequest) {
            String authorization = ((HttpRequest)context)
                    .getHeaders().getFirst(AUTHORIZATION).orElse(null);
            return authorization != null && authorization.startsWith(BASIC);
        } else {
            return false;
        }
    }

    @Override
    public Subject createSubject(Object context) {
        String authorization = ((HttpRequest)context).
                getHeaders().getFirst(AUTHORIZATION).orElse(null);
        if (authorization == null) {
            return null;
        }
        //basic auth
        String basicAuth = authorization.replace(BASIC, "").trim();
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
        InetSocketAddress remoteAddress = ((HttpRequest) context).getRemoteAddress();
        String remoteHost = remoteAddress.getHostString();
        String requestUri = ((HttpRequest) context).getPath();
        String requestType = ((HttpRequest) context).getMethodName();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();
        return PasswordSubject.builder(username, password)
                .setRemoteHost(remoteHost)
                .setTargetResource(targetUri)
                .build();
    }
}
