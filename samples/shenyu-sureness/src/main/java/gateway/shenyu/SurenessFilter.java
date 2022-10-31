package gateway.shenyu;

import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.exception.*;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.util.SurenessContextHolder;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

/**
 * @title: SurenessConfiguration
 * @Author hqgordon
 * @Date: 2021/7/31 5:13 下午
 * @Description: sureness过滤器
 * @Version 1.0
 */
@Component
@Order(-1)
public class SurenessFilter implements WebFilter {

    private static final Logger logger = LoggerFactory.getLogger(SurenessFilter.class);
    /*
     * @Author hqgordon
     * @Description //TODO
     * @Date 2021/8/4 8:38 上午
     * @Param [exchange, chain]
     * @return reactor.core.publisher.Mono<java.lang.Void>
     **/
    @Override
    @Nonnull
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        ServerHttpRequest request =  exchange.getRequest();
        try {
            SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(request);
            // You can consider using SurenessContextHolder to bind subject in threadLocal
            // if bind, please remove it when end
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject);
            }
        } catch (IncorrectCredentialsException | UnknownAccountException | ExpiredCredentialsException e1) {
            logger.debug("this request is illegal");
            return responseWrite(exchange, HttpStatus.UNAUTHORIZED, e1.getMessage(),null);
        } catch (NeedDigestInfoException e5) {
            logger.debug("you should try once again with digest auth information");
            return responseWrite(exchange, HttpStatus.UNAUTHORIZED,
                    "try once again with digest auth information",
                    Collections.singletonMap("WWW-Authenticate", e5.getAuthenticate()));
        } catch (UnauthorizedException e5) {
            logger.debug("this account can not access this resource");
            return responseWrite(exchange, HttpStatus.FORBIDDEN, e5.getMessage(),null);
        } catch (RuntimeException e) {
            logger.error("other exception happen: ", e);
            return responseWrite(exchange, HttpStatus.FORBIDDEN, e.getMessage(),null);
        }
        return chain.filter(exchange).doFinally(x -> SurenessContextHolder.unbindSubject());

    }
    /*
     * @Author hqgordon
     * @Description //TODO
     * @Date 2021/8/4 8:38 上午
     * @Param [exchange, statusCode, message, headers]
     * @return reactor.core.publisher.Mono<java.lang.Void>
     **/
    private Mono<Void> responseWrite(ServerWebExchange exchange, HttpStatus statusCode,
                                     String message, Map<String,String> headers) {
        exchange.getResponse().setStatusCode(statusCode);
        if (headers != null) {
            headers.forEach((key, value) -> exchange.getResponse().getHeaders().add(key, value));
        }
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
