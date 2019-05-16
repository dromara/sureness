package com.usthe.sureness.sample.support;

import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author tomsun28
 * @date 17:22 2019-05-12
 */
@Order(1)
@WebFilter(filterName = "SurenessFilter", urlPatterns = "/*", asyncSupported = true)
public class SurenessFilter implements Filter {
    /**
     * 日志操作
     */
    private static final Logger logger = LoggerFactory.getLogger(SurenessFilter.class);

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
            // TODO 考虑将生成的subject信息塞入request
            servletRequest.setAttribute("subject", subject);
        } catch (RuntimeException e) {
            logger.info("exception happen: ", e);
            // TODO 拦截并返回定制信息
            servletResponse.getWriter().flush();
            return;
        }
        // if ok doFilter and add subject in request
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
