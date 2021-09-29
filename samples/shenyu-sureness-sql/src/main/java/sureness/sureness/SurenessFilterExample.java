package sureness.sureness;

import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.exception.ExpiredCredentialsException;
import com.usthe.sureness.processor.exception.IncorrectCredentialsException;
import com.usthe.sureness.processor.exception.UnauthorizedException;
import com.usthe.sureness.processor.exception.UnknownAccountException;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.util.SurenessContextHolder;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;


/**
 * @title: SurenessConfiguration
 * @Author hqgordon
 * @Date: 2021/7/31 5:13 下午
 * @Description: sureness过滤器
 * @Version 1.0
 */
@Configuration
@Order(-1)
public class SurenessFilterExample implements WebFilter {

    private static final Logger logger = LoggerFactory.getLogger(SurenessFilterExample.class);

    /*
     * @Author hqgordon
     * @Description 过滤方法
     * @Date 2021/8/9 8:29 下午
     * @Param [exchange, chain]
     * @return reactor.core.publisher.Mono<java.lang.Void>
     **/
    @Override
    @Nonnull
    public Mono<Void> filter(ServerWebExchange exchange,@Nonnull WebFilterChain chain) {
        ServerHttpRequest request =  exchange.getRequest();
        HttpStatus statusCode = null;
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
            statusCode = HttpStatus.UNAUTHORIZED;
            errorMsg = e1.getMessage();
        } catch (UnauthorizedException e5) {
            logger.debug("this account can not access this resource");
            statusCode = HttpStatus.FORBIDDEN;
            errorMsg = e5.getMessage();
        } catch (RuntimeException e) {
            logger.error("other exception happen: ", e);
            statusCode = HttpStatus.CONFLICT;
            errorMsg = e.getMessage();
        }

        // auth error filter to error collect api
        if (statusCode != null && errorMsg != null) {
            String finalErrorMsg = errorMsg;
            Integer finalStatusCode = statusCode.value();
            request = request.mutate().headers(httpHeaders -> {
                httpHeaders.add("statusCode", String.valueOf(finalStatusCode));
                httpHeaders.add("errorMsg", finalErrorMsg);
            }).path("/auth/error").build();
            exchange =  exchange.mutate().request(request).build();
            return  responseWrite(exchange,statusCode,errorMsg);
        }
        return chain.filter(exchange).doFinally(x -> SurenessContextHolder.unbindSubject());
    }
    private Mono<Void> responseWrite(ServerWebExchange exchange, HttpStatus statusCode,
                                     String message) {

        exchange.getResponse().setStatusCode(statusCode);
        if (message != null) {
            return exchange.getResponse().writeWith(Flux.create(sink -> {
                NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
                DataBuffer dataBuffer= nettyDataBufferFactory.wrap(message.getBytes(StandardCharsets.UTF_8));
                sink.next(dataBuffer);
                sink.complete();
            }));
        } else {
            return exchange.getResponse().setComplete();
        }
    }

}
