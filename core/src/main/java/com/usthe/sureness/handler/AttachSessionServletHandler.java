package com.usthe.sureness.handler;

import com.usthe.sureness.subject.SubjectSum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * attach auth success subject info with servlet request session
 * @author tomsun28
 * @date 2021/4/5 17:54
 */
public class AttachSessionServletHandler implements SuccessHandler{

    private static final String PRINCIPAL = "principal";
    private static final String PRINCIPALS = "principals";
    private static final String ROLES = "roles";

    @Override
    public void processHandler(SubjectSum subjectSum, Object request) {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            HttpSession httpSession = servletRequest.getSession();
            if (httpSession.isNew() || httpSession.getAttribute(PRINCIPAL) == null) {
                httpSession.setAttribute(PRINCIPAL, subjectSum.getPrincipal());
                httpSession.setAttribute(PRINCIPALS, subjectSum.getPrincipalMap());
                httpSession.setAttribute(ROLES, subjectSum.getRoles());
            }
        }

    }
}
