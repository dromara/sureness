package com.usthe.sureness.processor.support;

import com.usthe.sureness.processor.BaseProcessor;
import com.usthe.sureness.processor.exception.IncorrectCredentialsException;
import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.processor.exception.SurenessAuthorizationException;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import com.usthe.sureness.subject.support.JwtSubjectToken;
import com.usthe.sureness.util.JsonWebTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * 支持 appId + jwt 的token的处理器实例
 * @author tomsun28
 * @date 12:36 2019-03-13
 */
public class JwtProcessor extends BaseProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(JwtProcessor.class);

    @Override
    public boolean canSupportAuTokenClass(Class<?> var) {
        return var != null && var == JwtSubjectToken.class;
    }

    @Override
    public Class<?> getSupportAuTokenClass() {
        return JwtSubjectToken.class;
    }

    @Override
    public boolean authenticated(SubjectAuToken var) throws SurenessAuthenticationException {
        String jwt = (String) var.getCredentials();
        if (jwt == null || "".equals(jwt)) {
            throw new  IncorrectCredentialsException("this jwt credential is null");
        }
        Claims claims = null;
        try {
            claims = JsonWebTokenUtil.parseJwt(jwt, JsonWebTokenUtil.SECRET_KEY);
        } catch (SignatureException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            // JWT令牌错误
            if (LOG.isInfoEnabled()) {
                LOG.info("jwtProcessor authenticated fail, user: {}, jwt: {}",
                        var.getPrincipal(), jwt, e);
            }
            throw new IncorrectCredentialsException("this jwt error:" + e.getMessage());
        }  catch (ExpiredJwtException e) {
            // JWT 令牌过期
            if (LOG.isDebugEnabled()) {
                LOG.debug("jwtProcessor authenticated expired, user: {}, jwt: {}",
                        var.getPrincipal(), jwt, e);
            }
        }
        if (claims != null) {
            String roles = claims.get("roles", String.class);
            if (roles != null) {
                List<String> roleList = Arrays.asList(roles.split(","));
                var.setSupportRoles(roleList);
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean authorized(SubjectAuToken var) throws SurenessAuthorizationException {
        List<String> ownRoles = (List<String>)var.getOwnRoles();
        List<String> supportRoles = (List<String>)var.getSupportRoles();
        return supportRoles == null || supportRoles.isEmpty()
                || supportRoles.stream().anyMatch(role -> ownRoles.contains(role));
    }

    @Override
    public Subject createSubject(SubjectAuToken var) {
        return null;
    }

}
