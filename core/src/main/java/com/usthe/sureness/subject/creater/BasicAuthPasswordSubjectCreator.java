package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.PasswordSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 支持通过basic auth 创建PasswordSubject 的创建者
 * @author tomsun28
 * @date 23:53 2020-02-27
 */
public class BasicAuthPasswordSubjectCreator implements SubjectCreate {

    private static final Logger logger = LoggerFactory.getLogger(BasicAuthPasswordSubjectCreator.class);

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic";
    private static final int COUNT_2 = 2;

    @Override
    public boolean canSupportSubject(Object context) {
        // basic auth判断
        // ("Authorization", "Basic YWRtaW46YWRtaW4=")        --- basic auth
        if (context instanceof HttpServletRequest) {
            String authorization = ((HttpServletRequest)context).getHeader(AUTHORIZATION);
            return authorization != null && authorization.startsWith(BASIC);
        } else {
            return false;
        }
    }

    @Override
    public Subject createSubject(Object context) {
        String authorization = ((HttpServletRequest)context).getHeader(AUTHORIZATION);
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
        String remoteHost = ((HttpServletRequest) context).getRemoteHost();
        String requestUri = ((HttpServletRequest) context).getRequestURI();
        String requestType = ((HttpServletRequest) context).getMethod();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();
        return PasswordSubject.builder(username, password)
                .setRemoteHost(remoteHost)
                .setTargetResource(targetUri)
                .build();
    }
}
