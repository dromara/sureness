package org.dromara.sureness.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.dromara.sureness.subject.SubjectSum;
import org.dromara.sureness.util.SurenessConstant;

import static java.util.Objects.isNull;

/**
 * attach auth success subject info with servlet request session
 * @author tomsun28
 * @date 2021/4/5 17:54
 */
public class AttachSessionServletHandler implements SuccessHandler{

    @Override
    public void processHandler(SubjectSum subjectSum, Object request) {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            HttpSession httpSession = servletRequest.getSession();
            if (isHttpSessionValid(httpSession)) {
                httpSession.setAttribute(SurenessConstant.PRINCIPAL, subjectSum.getPrincipal());
                httpSession.setAttribute(SurenessConstant.PRINCIPALS, subjectSum.getPrincipalMap());
                httpSession.setAttribute(SurenessConstant.ROLES, subjectSum.getRoles());
            }
        }

    }

    private boolean isHttpSessionValid(HttpSession httpSession) {
        return httpSession.isNew() || isNull(httpSession.getAttribute(SurenessConstant.PRINCIPAL));
    }
}
