package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.SubjectAuToken;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

/**
 * @author tomsun28
 * @date 23:35 2019-05-12
 */
public class WebSubjectFactory extends BaseSubjectFactory {

    private static final String JWT = "jwt";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic";

    @Override
    public SubjectAuToken createSubjectAuToken(Object request) {
        if (request instanceof ServletRequest) {
            String authorization = ((HttpServletRequest)request).getHeader(AUTHORIZATION);
            // 根据head里面的参数内容，判断其请求认证鉴权的方式，新建对应的token
            // 现在支持 jwt, Basic auth, ...
            // ("Authorization", "JWT eyJhbGciOiJIUzUxMi...")
            // ("Authorization", "Basic YWRtaW46YWRtaW4=")
            if (authorization.startsWith(JWT)) {
                // jwt
                String jwtValue = authorization.replace(JWT, "").trim();
                JwtSubjectToken jwtSubjectToken = new JwtSubjectToken();
                jwtSubjectToken.setJwt(jwtValue);
                return jwtSubjectToken;
            } else if (authorization.startsWith(BASIC)) {
                //basic auth
                String basicAuth = authorization.replace(BASIC, "").trim();
                basicAuth = Base64.getDecoder().decode(basicAuth).toString();
                String[] auth = basicAuth.split(":");
                String username = auth[0];
                String password = auth[1];
                PasswordSubjectToken passwordSubjectToken = new PasswordSubjectToken();
                passwordSubjectToken.setAppId(username);
                passwordSubjectToken.setPassword(password);
                return passwordSubjectToken;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
