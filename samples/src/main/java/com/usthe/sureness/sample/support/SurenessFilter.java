package com.usthe.sureness.sample.support;

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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        // TODO 认证及权限拦截
        // if ok filterChain.doFilter(servletRequest, servletResponse)
        // else 拦截
    }
}
