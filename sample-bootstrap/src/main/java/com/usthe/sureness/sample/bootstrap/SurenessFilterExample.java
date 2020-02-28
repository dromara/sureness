package com.usthe.sureness.sample.bootstrap;

import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.exception.DisabledAccountException;
import com.usthe.sureness.processor.exception.ExcessiveAttemptsException;
import com.usthe.sureness.processor.exception.ExpiredCredentialsException;
import com.usthe.sureness.processor.exception.IncorrectCredentialsException;
import com.usthe.sureness.processor.exception.ProcessorNotFoundException;
import com.usthe.sureness.processor.exception.UnauthorizedException;
import com.usthe.sureness.processor.exception.UnknownAccountException;
import com.usthe.sureness.processor.exception.UnsupportedSubjectException;
import com.usthe.sureness.sample.bootstrap.util.CommonUtil;
import com.usthe.sureness.subject.SubjectSum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 程序的http request请求入口过滤类示例  所有request请求都需经过此类
 * @author tomsun28
 * @date 17:22 2019-05-12
 */
@Order(1)
@WebFilter(filterName = "SurenessFilterExample", urlPatterns = "/*", asyncSupported = true)
public class SurenessFilterExample implements Filter {

    /** 日志操作 **/
    private static final Logger logger = LoggerFactory.getLogger(SurenessFilterExample.class);

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
            // 考虑将生成的subject信息塞入request
            if (subject != null) {
                servletRequest.setAttribute("subject", subject);
            }
        } catch (ProcessorNotFoundException | UnknownAccountException | UnsupportedSubjectException e4) {
            logger.debug("this request is illegal");
            CommonUtil.responseWrite(ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(e4.getMessage()), servletResponse);
            return;
        } catch (DisabledAccountException | ExcessiveAttemptsException e2 ) {
            logger.debug("the account is disabled");
            CommonUtil.responseWrite(ResponseEntity
                    .status(HttpStatus.FORBIDDEN).body(e2.getMessage()), servletResponse);
            return;
        } catch (IncorrectCredentialsException | ExpiredCredentialsException e3) {
            logger.debug("this account credential is incorrect or expired");
            CommonUtil.responseWrite(ResponseEntity
                    .status(HttpStatus.FORBIDDEN).body(e3.getMessage()), servletResponse);
            return;
        } catch (UnauthorizedException e5) {
            logger.debug("this account can not access this resource");
            CommonUtil.responseWrite(ResponseEntity
                    .status(HttpStatus.FORBIDDEN).body(e5.getMessage()), servletResponse);
            return;
        } catch (RuntimeException e) {
            logger.error("other exception happen: ", e);
            CommonUtil.responseWrite(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(),
                    servletResponse);
            return;
        }
        // if ok, doFilter and add subject in request
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
