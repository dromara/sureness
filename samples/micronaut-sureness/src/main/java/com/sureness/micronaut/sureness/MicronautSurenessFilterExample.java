package com.sureness.micronaut.sureness;


import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.exception.DisabledAccountException;
import com.usthe.sureness.processor.exception.ExcessiveAttemptsException;
import com.usthe.sureness.processor.exception.ExpiredCredentialsException;
import com.usthe.sureness.processor.exception.IncorrectCredentialsException;
import com.usthe.sureness.processor.exception.ProcessorNotFoundException;
import com.usthe.sureness.processor.exception.UnauthorizedException;
import com.usthe.sureness.processor.exception.UnknownAccountException;
import com.usthe.sureness.processor.exception.UnsupportedSubjectException;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.util.SurenessContextHolder;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.OncePerRequestHttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.http.filter.ServerFilterPhase;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;


@Filter("/*")
public class MicronautSurenessFilterExample extends OncePerRequestHttpServerFilter {

    private static final Logger logger = LoggerFactory.getLogger(MicronautSurenessFilterExample.class);


//    private final TenantResolver tenantResolver;
//
//    public MicronautSurenessFilterExample(TenantResolver tenantResolver) {
//        this.tenantResolver = tenantResolver;
//    }


    @Override
    protected Publisher<MutableHttpResponse<?>> doFilterOnce(HttpRequest<?> request,
                                                             ServerFilterChain chain) {
        Integer statusCode = null;
        String errorMsg = null;
        try {
            SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(request);
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
            request = request.mutate().headers(httpHeaders -> {
                httpHeaders.add("statusCode", String.valueOf(finalStatusCode));
                httpHeaders.add("errorMsg", finalErrorMsg);
            });
            logger.info(statusCode+"--->"+errorMsg);
            try {
                URI location = new URI("/auth/error");
                HttpResponse.redirect(location).
                        header("statusCode", String.valueOf(finalStatusCode))
                        .header("errorMsg", finalErrorMsg);
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
