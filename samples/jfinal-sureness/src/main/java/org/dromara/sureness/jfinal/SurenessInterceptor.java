package org.dromara.sureness.jfinal;


import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpServletRequest;

import org.dromara.sureness.mgt.SurenessSecurityManager;
import org.dromara.sureness.processor.exception.*;
import org.dromara.sureness.subject.SubjectSum;
import org.dromara.sureness.util.SurenessContextHolder;


/**
 * sureness interceptor to protect api
 * @author tomsun28
 * @date 2021/4/30 18:53
 */
public class SurenessInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        HttpServletRequest servletRequest = controller.getRequest();

        try {
            SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(servletRequest);
            // You can consider using SurenessContextHolder to bind subject in threadLocal
            // if bind, please remove it when end
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject);
            }
        } catch (IncorrectCredentialsException | UnknownAccountException | ExpiredCredentialsException e1) {
            System.out.println("this account credential is incorrect or expired");
            controller.getResponse().setStatus(401);
            controller.renderJson("message", e1.getMessage());
            return;
        } catch (DisabledAccountException | ExcessiveAttemptsException e2 ) {
            System.out.println("the account is disabled");
            controller.getResponse().setStatus(401);
            controller.renderJson("message", e2.getMessage());
            return;
        } catch (NeedDigestInfoException e3) {
            System.out.println("try once again with digest auth information");
            controller.getResponse().setStatus(401);
            controller.getResponse().addHeader("WWW-Authenticate", e3.getAuthenticate());
            controller.renderJson("message", "try once again with digest auth information");
            return;
        } catch (UnauthorizedException e4) {
            System.out.println("this account can not access this resource");
            controller.getResponse().setStatus(403);
            controller.renderJson("message", e4.getMessage());
            return;
        } catch (RuntimeException e) {
            System.out.println("other exception happen: " + e.getMessage());
            controller.getResponse().setStatus(500);
            controller.renderJson("message", e.getMessage());
            return;
        }
        inv.invoke();
    }
}
