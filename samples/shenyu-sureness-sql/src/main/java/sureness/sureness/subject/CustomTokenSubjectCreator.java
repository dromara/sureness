package sureness.sureness.subject;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Objects;


/**
 * custom token creator, get token from http request header - {"Token" : "tokenValue"}
 * tokenValue is : admin--issueTime--refreshPeriodTime--uuid
 * @author tomsun28
 * @date 2020-12-03 22:08
 */
public class CustomTokenSubjectCreator implements SubjectCreate {

    private static final String TOKEN_SPLIT = "--";

    private static final int TOKEN_SPLIT_SIZE = 4;

    @Override
    public boolean canSupportSubject(Object context) {
        // support token
        // {"Token" : "tokenValue"}
        if (context instanceof ServerHttpRequest) {
            String authorization = ((ServerHttpRequest)context).getHeaders().getFirst("Token");
            return authorization != null && authorization.split(TOKEN_SPLIT).length == TOKEN_SPLIT_SIZE;
        }
        return false;
    }

    @Override
    public Subject createSubject(Object context) {
        String authorization = ((ServerHttpRequest)context).getHeaders().getFirst("Token");
        String remoteHost = Objects.requireNonNull(((ServerHttpRequest) context).getRemoteAddress()).getHostString();
        String requestUri = ((ServerHttpRequest) context).getURI().getPath();
        String requestType = ((ServerHttpRequest) context).getMethodValue();
        String targetUri = requestUri.concat("===").concat(requestType.toLowerCase());
        return CustomTokenSubject.builder(authorization)
                .setRemoteHost(remoteHost)
                .setTargetResource(targetUri)
                .build();
    }
}
