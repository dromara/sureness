package com.usthe.sureness.processor.support;

import com.usthe.sureness.processor.BaseProcessor;
import com.usthe.sureness.processor.exception.ExpiredCredentialsException;
import com.usthe.sureness.processor.exception.IncorrectCredentialsException;
import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.processor.exception.SurenessAuthorizationException;
import com.usthe.sureness.processor.exception.UnauthorizedException;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.support.JwtSubject;
import com.usthe.sureness.util.JsonWebTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 支持 appId + jwt 的token的处理器实例
 * @author tomsun28
 * @date 12:36 2019-03-13
 */
public class JwtProcessor extends BaseProcessor {

    private static final Logger logger = LoggerFactory.getLogger(JwtProcessor.class);

    @Override
    public boolean canSupportAuTokenClass(Class<?> var) {
        return var == JwtSubject.class;
    }

    @Override
    public Class<?> getSupportAuTokenClass() {
        return JwtSubject.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Subject authenticated(Subject var) throws SurenessAuthenticationException {
        String jwt = (String) var.getCredentials();
        if (JsonWebTokenUtil.isNotJsonWebToken(jwt)) {
            throw new  IncorrectCredentialsException("this jwt credential is illegal");
        }
        Claims claims;
        try {
            claims = JsonWebTokenUtil.parseJwt(jwt);
        } catch (SignatureException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            // JWT令牌错误
            if (logger.isDebugEnabled()) {
                logger.debug("jwtProcessor authenticated fail, user: {}, jwt: {}",
                        var.getPrincipal(), jwt);
            }
            throw new IncorrectCredentialsException("this jwt error:" + e.getMessage());
        } catch (ExpiredJwtException e) {
            // JWT 令牌过期
            if (logger.isDebugEnabled()) {
                logger.debug("jwtProcessor authenticated expired, user: {}, jwt: {}",
                        var.getPrincipal(), jwt);
            }
            throw new ExpiredCredentialsException("this jwt has expired");
        }
        JwtSubject.Builder builder = JwtSubject.builder(var)
                .setPrincipal(claims.getSubject());
        List<String> ownRoles = claims.get("roles", List.class);
        if (ownRoles != null) {
            builder.setOwnRoles(ownRoles);
        }
        return builder.build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void authorized(Subject var) throws SurenessAuthorizationException {
        List<String> ownRoles = (List<String>)var.getOwnRoles();
        List<String> supportRoles = (List<String>)var.getSupportRoles();
        if (supportRoles == null || supportRoles.isEmpty() || supportRoles.stream().anyMatch(ownRoles::contains)) {
            return;
        }
        throw new UnauthorizedException("do not have the role to access resource");
    }

}
