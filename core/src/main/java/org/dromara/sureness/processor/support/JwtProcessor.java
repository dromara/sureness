package org.dromara.sureness.processor.support;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.dromara.sureness.processor.BaseProcessor;
import org.dromara.sureness.processor.exception.ExpiredCredentialsException;
import org.dromara.sureness.processor.exception.IncorrectCredentialsException;
import org.dromara.sureness.processor.exception.SurenessAuthenticationException;
import org.dromara.sureness.subject.PrincipalMap;
import org.dromara.sureness.subject.Subject;
import org.dromara.sureness.subject.support.JwtSubject;
import org.dromara.sureness.subject.support.SinglePrincipalMap;
import org.dromara.sureness.util.JsonWebTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import  static java.util.Objects.nonNull;

/**
 * the processor support jwt - JwtSubject
 * @author tomsun28
 * @date 12:36 2019-03-13
 */
public class JwtProcessor extends BaseProcessor {

    private static final Logger logger = LoggerFactory.getLogger(JwtProcessor.class);

    @Override
    public boolean canSupportSubjectClass(Class<?> var) {
        return var == JwtSubject.class;
    }

    @Override
    public Class<?> getSupportSubjectClass() {
        return JwtSubject.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Subject authenticated(Subject var) throws SurenessAuthenticationException {
        String jwt = (String) var.getCredential();
        if (JsonWebTokenUtil.isNotJsonWebToken(jwt)) {
            throw new  IncorrectCredentialsException("this jwt credential is illegal");
        }
        Claims claims;
        try {
            claims = JsonWebTokenUtil.parseJwt(jwt);
        } catch (SignatureException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            // JWT error
            if (logger.isDebugEnabled()) {
                logger.debug("jwtProcessor authenticated fail, user: {}, jwt: {}",
                        var.getPrincipal(), jwt);
            }
            throw new IncorrectCredentialsException("this jwt error:" + e.getMessage());
        } catch (ExpiredJwtException e) {
            // JWT expired
            if (logger.isDebugEnabled()) {
                logger.debug("jwtProcessor authenticated expired, user: {}, jwt: {}",
                        var.getPrincipal(), jwt);
            }
            throw new ExpiredCredentialsException("this jwt has expired");
        }
        // attention: need to set subject own roles from account
        var.setPrincipal(claims.getSubject());
        List<String> ownRoles = claims.get("roles", List.class);
        if (nonNull(ownRoles)) {
            var.setOwnRoles(ownRoles);
        }
        PrincipalMap principalMap = new SinglePrincipalMap();
        for (Map.Entry<String, Object> claimEntry : claims.entrySet()) {
            principalMap.setPrincipal(claimEntry.getKey(), claimEntry.getValue());
        }
        var.setPrincipalMap(principalMap);
        return var;
    }

}
