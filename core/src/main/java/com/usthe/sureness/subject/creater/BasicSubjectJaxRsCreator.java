package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.PasswordSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * the subject creator support creating PasswordSubject
 * only support JAX-RS
 * @author tomsun28
 * @date 23:53 2020-09-20
 */
public class BasicSubjectJaxRsCreator implements SubjectCreate {

    private static final Logger logger = LoggerFactory.getLogger(BasicSubjectJaxRsCreator.class);

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic";
    private static final int COUNT_2 = 2;

    @Override
    public boolean canSupportSubject(Object context) {
        // ("Authorization", "Basic YWRtaW46YWRtaW4=")        --- basic auth
        if (context instanceof ContainerRequestContext) {
            String authorization = ((ContainerRequestContext)context).getHeaderString(AUTHORIZATION);
            return authorization != null && authorization.startsWith(BASIC);
        } else {
            return false;
        }
    }

    @Override
    public Subject createSubject(Object context) {
        String authorization = ((ContainerRequestContext)context).getHeaderString(AUTHORIZATION);
        //basic auth
        String basicAuth = authorization.replace(BASIC, "").trim();
        try {
            basicAuth = new String(Base64.getDecoder().decode(basicAuth), StandardCharsets.UTF_8);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("can not create basic auth PasswordSubject, due {}", e.getMessage());
            }
            return null;
        }
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
        username = username.trim();
        String password = auth[1] == null ? null : auth[1].trim();
        String requestUri = ((ContainerRequestContext) context).getUriInfo().getPath();
        String requestType = ((ContainerRequestContext) context).getMethod();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();
        return PasswordSubject.builder(username, password)
                .setTargetResource(targetUri)
                .build();
    }
}
