package org.dromara.sureness.subject.creater;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.dromara.sureness.subject.PrincipalMap;
import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.SubjectCreate;
import org.dromara.sureness.subject.support.SessionSubject;
import org.dromara.sureness.util.SurenessConstant;

import java.util.List;

/**
 * the subject creator support creating SessionSubject
 * @author tomsun28
 * @date 2021/4/6 20:04
 */
public class SessionSubjectServletCreator implements SubjectCreate {

    @Override
    public boolean canSupportSubject(Object context) {
        if (context instanceof HttpServletRequest) {
            HttpSession httpSession = ((HttpServletRequest) context).getSession(false);
            return httpSession != null && httpSession.getAttribute(SurenessConstant.PRINCIPAL) != null;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Subject createSubject(Object context) {
        HttpServletRequest servletRequest = (HttpServletRequest) context;
        HttpSession httpSession = servletRequest.getSession(false);
        String principal = (String) httpSession.getAttribute(SurenessConstant.PRINCIPAL);
        if (principal == null || "".equals(principal.trim())) {
            return null;
        }
        Object principalMapTmp = httpSession.getAttribute(SurenessConstant.PRINCIPALS);
        PrincipalMap principalMap = principalMapTmp == null ? null : (PrincipalMap) principalMapTmp;
        Object rolesTmp = httpSession.getAttribute(SurenessConstant.ROLES);
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
