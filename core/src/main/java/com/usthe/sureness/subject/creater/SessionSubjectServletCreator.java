package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.PrincipalMap;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.SessionSubject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * the subject creator support creating SessionSubject
 * @author tomsun28
 * @date 2021/4/6 20:04
 */
public class SessionSubjectServletCreator implements SubjectCreate {

    private static final String PRINCIPAL = "principal";
    private static final String PRINCIPALS = "principals";
    private static final String ROLES = "roles";

    @Override
    public boolean canSupportSubject(Object context) {
        if (context instanceof HttpServletRequest) {
            HttpSession httpSession = ((HttpServletRequest) context).getSession(false);
            return httpSession != null && httpSession.getAttribute(PRINCIPAL) != null;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Subject createSubject(Object context) {
        HttpServletRequest servletRequest = (HttpServletRequest) context;
        HttpSession httpSession = servletRequest.getSession(false);
        String principal = (String) httpSession.getAttribute(PRINCIPAL);
        if (principal == null || "".equals(principal.trim())) {
            return null;
        }
        Object principalMapTmp = httpSession.getAttribute(PRINCIPALS);
        PrincipalMap principalMap = principalMapTmp == null ? null : (PrincipalMap) principalMapTmp;
        Object rolesTmp = httpSession.getAttribute(ROLES);
        List<String> roles = rolesTmp == null ? null : (List<String>) rolesTmp;
        String remoteHost = ((HttpServletRequest) context).getRemoteHost();
        String requestUri = ((HttpServletRequest) context).getRequestURI();
        String requestType = ((HttpServletRequest) context).getMethod();
        String targetUri = requestUri.concat("===").concat(requestType).toLowerCase();
        return SessionSubject.builder(principal, roles)
                .setPrincipalMap(principalMap)
                .setRemoteHost(remoteHost)
                .setTargetResource(targetUri)
                .build();
    }
}
