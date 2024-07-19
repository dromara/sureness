package org.dromara.sureness.microprofile;

import org.dromara.sureness.mgt.SurenessSecurityManager;
import org.dromara.sureness.processor.exception.*;
import org.dromara.sureness.subject.SubjectSum;
import org.dromara.sureness.util.SurenessContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * @author Lenovo
 */
@Provider
@PreMatching
public class SurenessFilter implements ContainerRequestFilter, ContainerResponseFilter {

    /** logger **/
    private static final Logger logger = LoggerFactory.getLogger(SurenessFilter.class);

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
