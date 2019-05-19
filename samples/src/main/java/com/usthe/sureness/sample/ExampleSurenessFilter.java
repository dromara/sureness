package com.usthe.sureness.sample;

import com.usthe.sureness.mgt.SurenessNoInitException;
import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.exception.DisabledAccountException;
import com.usthe.sureness.processor.exception.ExcessiveAttemptsException;
import com.usthe.sureness.processor.exception.ExpiredCredentialsException;
import com.usthe.sureness.processor.exception.IncorrectCredentialsException;
import com.usthe.sureness.processor.exception.ProcessorNotFoundException;
import com.usthe.sureness.processor.exception.UnauthorizedException;
import com.usthe.sureness.processor.exception.UnknownAccountException;
import com.usthe.sureness.processor.exception.UnsupportedTokenException;
import com.usthe.sureness.sample.util.CommonUtil;
import com.usthe.sureness.subject.Subject;
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
 * 过滤类   程序的入口过滤类  所有request请求都需经过此类
 * @author tomsun28
 * @date 17:22 2019-05-12
 */
@Order(1)
@WebFilter(filterName = "ExampleSurenessFilter", urlPatterns = "/*", asyncSupported = true)
public class ExampleSurenessFilter implements Filter {

    /** 日志操作 **/
    private static final Logger logger = LoggerFactory.getLogger(ExampleSurenessFilter.class);

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
            Subject subject = SurenessSecurityManager.getInstance().checkIn(servletRequest);
            // 考虑将生成的subject信息塞入request
            servletRequest.setAttribute("subject", subject);
        } catch (ProcessorNotFoundException | UnknownAccountException | UnsupportedTokenException e4) {
            logger.debug("this request is illegal: ", e4);
            CommonUtil.responseWrite(ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(e4.getMessage()), servletResponse);
            return;
        } catch (DisabledAccountException | ExcessiveAttemptsException e2 ) {
            logger.debug("the account is disabled: ", e2);
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
        } catch (SurenessNoInitException e1) {
            logger.error("SurenessSecurityManager not init, please check error :", e1);
            CommonUtil.responseWrite(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(),
                    servletResponse);
            return;
        }catch (RuntimeException e) {
            logger.error("other exception happen: ", e);
            CommonUtil.responseWrite(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(),
                    servletResponse);
            return;
        }
        // if ok, doFilter and add subject in request
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
