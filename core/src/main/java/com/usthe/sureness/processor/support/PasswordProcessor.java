package com.usthe.sureness.processor.support;

import com.usthe.sureness.processor.BaseProcessor;
import com.usthe.sureness.processor.exception.DisabledAccountException;
import com.usthe.sureness.processor.exception.ExcessiveAttemptsException;
import com.usthe.sureness.processor.exception.IncorrectCredentialsException;
import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.processor.exception.SurenessAuthorizationException;
import com.usthe.sureness.processor.exception.UnauthorizedException;
import com.usthe.sureness.processor.exception.UnknownAccountException;
import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.support.PasswordSubject;
import com.usthe.sureness.util.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 支持 username password 类型token的处理器实例
 * @author tomsun28
 * @date 12:38 2019-03-13
 */
public class PasswordProcessor extends BaseProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PasswordProcessor.class);

    private SurenessAccountProvider accountProvider;

    @Override
    public boolean canSupportAuTokenClass(Class<?> var) {
        return var == PasswordSubject.class;
    }

    @Override
    public Class<?> getSupportAuTokenClass() {
        // 这里只支持passwordToken  -- username/appId/email/phoneNum + password
        return PasswordSubject.class;
    }

    @Override
    public Subject authenticated(Subject var) throws SurenessAuthenticationException {
        String appId = (String) var.getPrincipal();
        SurenessAccount account = accountProvider.loadAccount(appId);
        if (account == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("PasswordProcessor authenticated fail, no this user: {}",
                        var.getPrincipal());
            }
            throw new  UnknownAccountException("do not exist the account: " + appId);
        }
        if (var.getCredentials() != null && account.getPassword() != null) {
            String password = String.valueOf(var.getCredentials());
            if (account.getSalt() != null && !"".equals(account.getSalt())) {
                password = Md5Util.md5( password + account.getSalt());
            }
            if (password == null || !password.equals(account.getPassword())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("PasswordProcessor authenticated fail, user: {}",
                            var.getPrincipal());
                }
                throw new IncorrectCredentialsException("incorrect password");
            }
        }
        if (account.isDisabledAccount()) {
            throw new DisabledAccountException("account is disabled");
        }
        if (account.isExcessiveAttempts()) {
            throw new ExcessiveAttemptsException("account is disable due to many time authenticated, try later");
        }
        return PasswordSubject.builder(var)
                .setOwnRoles(account.getOwnRoles())
                .build();
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

    public void setAccountProvider(SurenessAccountProvider provider) {
        this.accountProvider = provider;
    }

}
