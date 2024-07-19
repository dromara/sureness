package org.dromara.sureness.sample.redis.session.sureness;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.dromara.sureness.mgt.SurenessSecurityManager;
import org.dromara.sureness.processor.exception.*;
import org.dromara.sureness.subject.SubjectSum;
import org.dromara.sureness.util.SurenessContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * sureness filter class example, filter all http request
 * @author tomsun28
 * @date 17:22 2019-05-12
 */
@Order(1)
@WebFilter(filterName = "SurenessFilterExample", urlPatterns = "/*", asyncSupported = true)
public class SurenessFilterExample implements Filter {

    /** logger **/
    private static final Logger logger = LoggerFactory.getLogger(SurenessFilterExample.class);

    private static final String UPGRADE = "Upgrade";

    private static final String WEBSOCKET = "websocket";

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("surenessFilter initialized");
    }

    @Override
    public void destroy() {
        logger.info("surenessFilter destroyed");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        try {
            SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(servletRequest);
            // You can consider using SurenessContextHolder to bind subject in threadLocal
            // if bind, please remove it when end
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject);
            }
        } catch (IncorrectCredentialsException | UnknownAccountException | ExpiredCredentialsException e1) {
            logger.debug("this request account info is illegal, {}", e1.getMessage());
            responseWrite(ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Username or password is incorrect or expired"), servletResponse);
            return;
        } catch (NeedDigestInfoException e5) {
            logger.debug("you should try once again with digest auth information");
            responseWrite(ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .header("WWW-Authenticate", e5.getAuthenticate()).build(), servletResponse);
            return;
        } catch (UnauthorizedException e6) {
            logger.debug("this account can not access this resource, {}", e6.getMessage());
            responseWrite(ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("This account has no permission to access this resource"), servletResponse);
            return;
        } catch (RuntimeException e) {
            logger.error("other exception happen: ", e);
            responseWrite(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(),
                    servletResponse);
            return;
        }
        try {
            // if ok, doFilter and add subject in request
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            int statusCode = ((HttpServletResponse) servletResponse).getStatus();
            String upgrade = ((HttpServletResponse) servletResponse).getHeader(UPGRADE);
            if (statusCode != HttpStatus.SWITCHING_PROTOCOLS.value() || !WEBSOCKET.equals(upgrade)) {
                SurenessContextHolder.clear();
            }
        }
    }

    /**
     * write response json data
     * @param content content
     * @param response response
     */
    private static void responseWrite(ResponseEntity content, ServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        ((HttpServletResponse)response).setStatus(content.getStatusCodeValue());
        content.getHeaders().forEach((key, value) ->
                ((HttpServletResponse) response).addHeader(key, value.get(0)));
        try (PrintWriter printWriter = response.getWriter()) {
            if (content.getBody() != null) {
                if (content.getBody() instanceof String) {
                    printWriter.write(content.getBody().toString());
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    printWriter.write(objectMapper.writeValueAsString(content.getBody()));
                }
            } else {
                printWriter.flush();
            }
        } catch (IOException e) {
            logger.error("responseWrite response error: ", e);
        }
    }
}
