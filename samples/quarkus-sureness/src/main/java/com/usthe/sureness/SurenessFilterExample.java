package com.usthe.sureness;

import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.exception.*;
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
 * An example of the program's http request request entry filtering class.
 * All request requests must go through this class
 * @author tomsun28
 * @date 17:22 2019-05-12
 */
@Provider
@PreMatching
public class SurenessFilterExample implements ContainerRequestFilter, ContainerResponseFilter  {

    /** logger **/
    private static final Logger logger = LoggerFactory.getLogger(SurenessFilterExample.class);

    @Override
    public void filter(ContainerRequestContext requestContext) {
        try {
            SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(requestContext);
            // You can consider using SurenessContextHolder to bind subject in threadLocal
            // if bind, please remove it when end
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject);
            }
        } catch (IncorrectCredentialsException | UnknownAccountException | ExpiredCredentialsException e1) {
            logger.debug("this request account info is illegal");
            requestContext.abortWith(Response.status(401).entity(e1.getMessage()).build());

        } catch (DisabledAccountException | ExcessiveAttemptsException e2 ) {
            logger.debug("the account is disabled");
            requestContext.abortWith(Response.status(401).entity(e2.getMessage()).build());

        } catch (NeedDigestInfoException e4) {
            logger.debug("you should try once again with digest auth information");
            requestContext.abortWith(Response.status(401).header("WWW-Authenticate", e4.getAuthenticate()).build());

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
