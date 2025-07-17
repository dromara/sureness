package org.dromara.sureness.subject.creater;

import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.SubjectCreate;
import org.dromara.sureness.subject.support.PasswordSubject;
import org.dromara.sureness.util.ServletUtil;
import org.dromara.sureness.util.SurenessConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * the subject creator support creating PasswordSubject
 * only support HttpServletRequest
 * @author tomsun28
 * @date 23:53 2020-02-27
 */
public class BasicSubjectServletCreator implements SubjectCreate {

    private static final Logger logger = LoggerFactory.getLogger(BasicSubjectServletCreator.class);

    private static final int COUNT_2 = 2;

    @Override
    public boolean canSupportSubject(Object context) {
        // ("Authorization", "Basic YWRtaW46YWRtaW4=")        --- basic auth
        if (context instanceof HttpServletRequest) {
            String authorization = ((HttpServletRequest)context).getHeader(SurenessConstant.AUTHORIZATION);
            return authorization != null && authorization.startsWith(SurenessConstant.BASIC);
        } else {
            return false;
        }
    }

    @Override
    public Subject createSubject(Object context) {
        String authorization = ((HttpServletRequest)context).getHeader(SurenessConstant.AUTHORIZATION);
        //basic auth
        String basicAuth = authorization.replace(SurenessConstant.BASIC, "").trim();
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
        String remoteHost = ((HttpServletRequest) context).getRemoteHost();
        String requestUri = ServletUtil.getRequestUri((HttpServletRequest) context);
        String requestType = ((HttpServletRequest) context).getMethod();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();
        return PasswordSubject.builder(username, password)
                .setRemoteHost(remoteHost)
                .setTargetResource(targetUri)
                .build();
    }
}
