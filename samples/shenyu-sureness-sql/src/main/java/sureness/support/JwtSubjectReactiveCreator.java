package sureness.support;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.JwtSubject;
import com.usthe.sureness.util.JsonWebTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetSocketAddress;


/**
 * JwtSubject creator
 * only support ServerHttpRequest
 * @author tomsun28
 * @date 23:58 2020-09-29
 */
public class JwtSubjectReactiveCreator implements SubjectCreate {

    private static final Logger logger = LoggerFactory.getLogger(JwtSubjectReactiveCreator.class);

    private static final String BEARER = "Bearer";
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public boolean canSupportSubject(Object context) {
        // support bearer jwt
        // ("Authorization", "Bearer eyJhbGciOiJIUzUxMi...")  --- jwt auth
        if (context instanceof ServerHttpRequest) {
            String authorization = ((ServerHttpRequest)context).getHeaders().getFirst(AUTHORIZATION);
            if (authorization != null && authorization.startsWith(BEARER)) {
                String jwtValue = authorization.replace(BEARER, "").trim();
                return !JsonWebTokenUtil.isNotJsonWebToken(jwtValue);
            }
        }
        return false;
    }

    @Override
    public Subject createSubject(Object context) {
        String authorization = ((ServerHttpRequest)context).getHeaders().getFirst(AUTHORIZATION);
        if (authorization != null && authorization.startsWith(BEARER)) {
            // jwt token
            String jwtValue = authorization.replace(BEARER, "").trim();
            if (JsonWebTokenUtil.isNotJsonWebToken(jwtValue)) {
                if (logger.isInfoEnabled()) {
                    logger.info("can not create JwtSubject by this request message, is not jwt");
                }
                return null;
            }
            InetSocketAddress remoteAddress = ((ServerHttpRequest) context).getRemoteAddress();
            String remoteHost = remoteAddress == null ? "" : remoteAddress.getHostString();
            String requestUri = ((ServerHttpRequest) context).getPath().value();
            String requestType = ((ServerHttpRequest) context).getMethodValue();
            String targetUri = requestUri.concat("===").concat(requestType.toLowerCase());
            return JwtSubject.builder(jwtValue)
                    .setRemoteHost(remoteHost)
                    .setTargetResource(targetUri)
                    .build();
        }
        return null;
    }
}
