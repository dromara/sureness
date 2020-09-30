package com.usthe.sureness.spring.webflux;


import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.exception.*;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.util.SurenessContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author tomsun28
 * @date 2020-09-29 22:02
 */
@Configuration
@Order(-1)
public class SurenessFilterExample implements WebFilter {

    private static final Logger logger = LoggerFactory.getLogger(SurenessFilterExample.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request =  exchange.getRequest();
        Integer statusCode = null;
        String errorMsg = null;
        try {
            SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(request);
            // 可以考虑使用SurenessContextHolder放入threadLocal中绑定,如果绑定 请在请求线程结束时remove
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject);
            }
        } catch (ProcessorNotFoundException | UnknownAccountException | UnsupportedSubjectException e4) {
            logger.debug("this request is illegal");
            statusCode = HttpStatus.BAD_REQUEST.value();
            errorMsg = e4.getMessage();
        } catch (DisabledAccountException | ExcessiveAttemptsException e2 ) {
            logger.debug("the account is disabled");
            statusCode = HttpStatus.FORBIDDEN.value();
            errorMsg = e2.getMessage();
        } catch (IncorrectCredentialsException | ExpiredCredentialsException e3) {
            logger.debug("this account credential is incorrect or expired");
            statusCode = HttpStatus.FORBIDDEN.value();
            errorMsg = e3.getMessage();
        } catch (UnauthorizedException e5) {
            logger.debug("this account can not access this resource");
            statusCode = HttpStatus.FORBIDDEN.value();
            errorMsg = e5.getMessage();
        } catch (RuntimeException e) {
            logger.error("other exception happen: ", e);
            statusCode = HttpStatus.FORBIDDEN.value();
            errorMsg = e.getMessage();
        }

        // auth error filter to error collect api
        if (statusCode != null && errorMsg != null) {
            String finalErrorMsg = errorMsg;
            Integer finalStatusCode = statusCode;
            request = request.mutate().headers(httpHeaders -> {
                httpHeaders.add("statusCode", String.valueOf(finalStatusCode));
                httpHeaders.add("errorMsg", finalErrorMsg);
            }).path("/auth/error").build();
            exchange =  exchange.mutate().request(request).build();
        }
        return chain.filter(exchange).doFinally(x -> SurenessContextHolder.unbindSubject());
    }
}
