package org.dromara.sureness.spring.webflux;


import org.dromara.sureness.mgt.SurenessSecurityManager;
import org.dromara.sureness.processor.exception.*;
import org.dromara.sureness.subject.SubjectSum;
import org.dromara.sureness.util.SurenessContextHolder;
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
 * sureness filter class example, filter all http request
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
            // You can consider using SurenessContextHolder to bind subject in threadLocal
            // if bind, please remove it when end
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject);
            }
        } catch (IncorrectCredentialsException | UnknownAccountException | ExpiredCredentialsException e1) {
            logger.debug("this request account info is illegal");
            statusCode = HttpStatus.UNAUTHORIZED.value();
            errorMsg = e1.getMessage();
        } catch (UnauthorizedException e5) {
            logger.debug("this account can not access this resource");
            statusCode = HttpStatus.FORBIDDEN.value();
            errorMsg = e5.getMessage();
        } catch (RuntimeException e) {
            logger.error("other exception happen: ", e);
            statusCode = HttpStatus.CONFLICT.value();
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
