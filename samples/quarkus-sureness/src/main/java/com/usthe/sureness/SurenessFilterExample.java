package com.usthe.sureness;

import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.exception.DisabledAccountException;
import com.usthe.sureness.processor.exception.ExcessiveAttemptsException;
import com.usthe.sureness.processor.exception.ExpiredCredentialsException;
import com.usthe.sureness.processor.exception.IncorrectCredentialsException;
import com.usthe.sureness.processor.exception.ProcessorNotFoundException;
import com.usthe.sureness.processor.exception.UnauthorizedException;
import com.usthe.sureness.processor.exception.UnknownAccountException;
import com.usthe.sureness.processor.exception.UnsupportedSubjectException;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.util.SurenessContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * 程序的http request请求入口过滤类示例  所有request请求都需经过此类
 * @author tomsun28
 * @date 17:22 2019-05-12
 */
@Provider
@PreMatching
public class SurenessFilterExample implements ContainerRequestFilter, ContainerResponseFilter  {

    /** 日志操作 **/
    private static final Logger logger = LoggerFactory.getLogger(SurenessFilterExample.class);

    @Override
    public void filter(ContainerRequestContext requestContext) {
        try {
            SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(requestContext);
            // 可以考虑使用SurenessContextHolder放入threadLocal中绑定
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject);
            }
        } catch (ProcessorNotFoundException | UnknownAccountException | UnsupportedSubjectException e4) {
            logger.debug("this request is illegal");
            requestContext.abortWith(Response.status(401).entity(e4.getMessage()).build());

        } catch (DisabledAccountException | ExcessiveAttemptsException e2 ) {
            logger.debug("the account is disabled");
            requestContext.abortWith(Response.status(401).entity(e2.getMessage()).build());

        } catch (IncorrectCredentialsException | ExpiredCredentialsException e3) {
            logger.debug("this account credential is incorrect or expired");
            requestContext.abortWith(Response.status(401).entity(e3.getMessage()).build());

        } catch (UnauthorizedException e5) {
            logger.debug("this account can not access this resource");
            requestContext.abortWith(Response.status(403).entity(e5.getMessage()).build());

        } catch (RuntimeException e) {
            logger.error("other exception happen: ", e);
            requestContext.abortWith(Response.status(500).entity(e.getMessage()).build());
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        SurenessContextHolder.clear();
    }
}
