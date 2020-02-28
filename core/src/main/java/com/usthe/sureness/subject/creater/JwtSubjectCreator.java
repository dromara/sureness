package com.usthe.sureness.subject.creater;

import com.usthe.sureness.processor.exception.UnsupportedSubjectException;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.subject.support.JwtSubject;
import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.SurenessCommonUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * JwtSubject creator
 * @author tomsun28
 * @date 23:58 2020-02-27
 */
public class JwtSubjectCreator implements SubjectCreate {

    private static final String BEARER = "Bearer";
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public boolean canSupportSubject(Object context) {
        String authorization = ((HttpServletRequest)context).getHeader(AUTHORIZATION);
        return authorization != null && authorization.startsWith(BEARER);
    }

    @Override
    public Subject createSubject(Object context) {
        // todo 完善
        String authorization = ((HttpServletRequest)context).getHeader(AUTHORIZATION);
        // 根据head里面的参数内容，判断其请求认证鉴权的方式，新建对应的token
        // 现在支持 json web token, Basic auth, ...
        // ("Authorization", "Bearer eyJhbGciOiJIUzUxMi...")  --- jwt auth
        // ("Authorization", "Basic YWRtaW46YWRtaW4=")        --- basic auth
        if (authorization != null && authorization.startsWith(BEARER)) {
            // jwt token
            String jwtValue = authorization.replace(BEARER, "").trim();
            if (JsonWebTokenUtil.isNotJsonWebToken(jwtValue)) {
                throw new UnsupportedSubjectException("Bearer token now support jwt");
            }
            String remoteHost = ((HttpServletRequest) context).getRemoteHost();
            String requestUri = ((HttpServletRequest) context).getRequestURI();
            String requestType = ((HttpServletRequest) context).getMethod();
            String targetUri = requestUri.concat("===").concat(requestType.toUpperCase());
            String userAgent = SurenessCommonUtil.findUserAgent((HttpServletRequest) context);
            return JwtSubject.builder(jwtValue)
                    .setRemoteHost(remoteHost)
                    .setTargetResource(targetUri)
                    .setUserAgent(userAgent)
                    .build();
        }
        return null;
    }
}
