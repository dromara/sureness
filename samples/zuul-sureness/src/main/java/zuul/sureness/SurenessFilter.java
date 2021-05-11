package zuul.sureness;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.exception.*;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.util.SurenessContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;

/**
 * sureness filter protest api
 * @author tomsun28
 * @date 2021/4/29 21:31
 */
@Component
public class SurenessFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(SurenessFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        try {
            SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(context.getRequest());
            // You can consider using SurenessContextHolder to bind subject in threadLocal
            // if bind, please remove it when end
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject);
            }
        } catch (IncorrectCredentialsException | UnknownAccountException | ExpiredCredentialsException e1) {
            logger.debug("request account auth error, maybe username, password or expired credential");
            responseWrite(context, HttpStatus.UNAUTHORIZED, e1.getMessage(), null);
            return null;
        } catch (DisabledAccountException | ExcessiveAttemptsException e2 ) {
            logger.debug("the account is disabled");
            responseWrite(context, HttpStatus.UNAUTHORIZED, e2.getMessage(), null);
            return null;
        } catch (NeedDigestInfoException e5) {
            logger.debug("you should try once again with digest auth information");
            responseWrite(context, HttpStatus.UNAUTHORIZED,
                    "try once again with digest auth information",
                    Collections.singletonMap("WWW-Authenticate", e5.getAuthenticate()));
            return null;
        } catch (UnauthorizedException e6) {
            logger.debug("this account can not access this resource : {}", e6.getMessage());
            responseWrite(context, HttpStatus.FORBIDDEN,
                    "forbidden: can not access this resource", null);
            return null;
        } catch (RuntimeException e) {
            logger.error("other exception happen: ", e);
            responseWrite(context, HttpStatus.FORBIDDEN, e.getMessage(), null);
            return null;
        }
        return null;
    }

    /**
     * write response json data
     * @param context content
     * @param statusCode statusCode
     * @param message message
     */
    private void responseWrite(RequestContext context, HttpStatus statusCode,
                                      String message, Map<String,String> headers) {
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(statusCode.value());
        context.getResponse().setCharacterEncoding("UTF-8");
        if (headers != null) {
            headers.forEach((key, value) -> {
                context.getResponse().addHeader(key, value);
            });
        }
        try (PrintWriter printWriter = context.getResponse().getWriter()) {
            if (message != null) {
                printWriter.write(message);
            } else {
                printWriter.flush();
            }
        } catch (IOException e) {
            logger.error("responseWrite response error: ", e);
        }
    }
}
