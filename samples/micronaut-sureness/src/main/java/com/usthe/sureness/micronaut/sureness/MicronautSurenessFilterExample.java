package com.usthe.sureness.micronaut.sureness;


import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.exception.*;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.util.SurenessContextHolder;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.http.filter.ServerFilterPhase;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author tom
 */
@Filter("/**")
public class MicronautSurenessFilterExample implements HttpServerFilter {

    private static final Logger logger = LoggerFactory.getLogger(MicronautSurenessFilterExample.class);

    @Inject
    private SurenessSecurityManager securityManager ;

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request,
                                                             ServerFilterChain chain) {
        Integer statusCode = null;
        String errorMsg = null;
        try {
            SubjectSum subject =securityManager.checkIn(request);
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject);
            }
        } catch (ProcessorNotFoundException | UnknownAccountException | UnsupportedSubjectException e4) {
            logger.debug("this request is illegal");
            statusCode = HttpStatus.BAD_REQUEST.getCode();
            errorMsg = e4.getMessage();
        } catch (DisabledAccountException | ExcessiveAttemptsException e2 ) {
            logger.debug("the account is disabled");
            statusCode = HttpStatus.FORBIDDEN.getCode();
            errorMsg = e2.getMessage();
        } catch (IncorrectCredentialsException | ExpiredCredentialsException e3) {
            logger.debug("this account credential is incorrect or expired");
            statusCode = HttpStatus.FORBIDDEN.getCode();
            errorMsg = e3.getMessage();
        } catch (UnauthorizedException e5) {
            logger.debug("this account can not access this resource");
            statusCode = HttpStatus.FORBIDDEN.getCode();
            errorMsg = e5.getMessage();
        } catch (RuntimeException e) {
            logger.error("other exception happen: ", e);
            statusCode = HttpStatus.FORBIDDEN.getCode();
            errorMsg = e.getMessage();
        }
        if (statusCode != null && errorMsg != null) {
            String finalErrorMsg = errorMsg;
            Integer finalStatusCode = statusCode;
            logger.info(statusCode+"--->"+errorMsg);
            try {
                URI location = new URI("/auth/error");
                request = request.mutate().headers(httpHeaders -> {
                    httpHeaders.add("statusCode", String.valueOf(finalStatusCode));
                    httpHeaders.add("errorMsg", finalErrorMsg);
                }).uri(location);
            }catch (URISyntaxException e){
                logger.error("uri error");
            }
        }
        return chain.proceed(request);
    }

    @Override
    public int getOrder() {
        return ServerFilterPhase.SECURITY.order();
    }


}
