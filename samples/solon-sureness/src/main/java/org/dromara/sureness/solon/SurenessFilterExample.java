package org.dromara.sureness.solon;


import org.dromara.sureness.mgt.SurenessSecurityManager;
import org.dromara.sureness.processor.exception.*;
import org.dromara.sureness.subject.SubjectSum;
import org.dromara.sureness.util.SurenessContextHolder;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

/**
 * sureness filter class example, filter all http request
 * @author tomsun28
 * @date 2020-09-29 22:02
 */
@Component
public class SurenessFilterExample implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SurenessFilterExample.class);

    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {

        try {
            SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(ctx);
            // You can consider using SurenessContextHolder to bind subject in threadLocal
            // if bind, please remove it when end
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject);
            }
        } catch (IncorrectCredentialsException | UnknownAccountException | ExpiredCredentialsException e1) {
            logger.debug("this request account info is illegal");
            responseWrite(ctx, 401, e1.getMessage(), null);
            return;
        } catch (NeedDigestInfoException e5) {
            logger.debug("you should try once again with digest auth information");
            responseWrite(ctx, 401,
                    "try once again with digest auth information",
                    Collections.singletonMap("WWW-Authenticate", e5.getAuthenticate()));
            return;
        } catch (UnauthorizedException e5) {
            logger.debug("this account can not access this resource");
            responseWrite(ctx, 403, e5.getMessage(), null);
            return;
        } catch (RuntimeException e) {
            logger.error("other exception happen: ", e);
            responseWrite(ctx, 409, e.getMessage(), null);
            return;
        }
        chain.doFilter(ctx);
    }

    /**
     * write response data
     *
     * @param ctx        ctx
     * @param statusCode statusCode
     * @param message    message
     */
    private void responseWrite(Context ctx, int statusCode,
                               String message, Map<String, String> headers) throws Throwable {
        ctx.statusSet(statusCode);
        if (headers != null) {
            headers.forEach(ctx::headerAdd);
        }

        ctx.render(Result.failure(message));
    }
}
