package com.usthe.sureness.processor.support;

import com.usthe.sureness.processor.BaseProcessor;
import com.usthe.sureness.processor.exception.DisabledAccountException;
import com.usthe.sureness.processor.exception.ExcessiveAttemptsException;
import com.usthe.sureness.processor.exception.IncorrectCredentialsException;
import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.processor.exception.UnknownAccountException;
import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.support.PasswordSubject;
import com.usthe.sureness.util.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * the processor support username password - PasswordSubject
 * @author tomsun28
 * @date 12:38 2019-03-13
 */
public class PasswordProcessor extends BaseProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PasswordProcessor.class);

    private SurenessAccountProvider accountProvider;

    @Override
    public boolean canSupportSubjectClass(Class<?> var) {
        return var == PasswordSubject.class;
    }

    @Override
    public Class<?> getSupportSubjectClass() {
        // only support passwordToken  -- username/appId/email/phoneNum + password
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
            throw new UnknownAccountException("do not exist the account: " + appId);
        }
        if (var.getCredential() != null && account.getPassword() != null) {
            String password = String.valueOf(var.getCredential());
            if (account.getSalt() != null && !"".equals(account.getSalt())) {
                // Not case-sensitive for salt password
                password = Md5Util.md5( password + account.getSalt());
                if (password != null && password.equalsIgnoreCase(account.getPassword())) {
                    password = account.getPassword();
                }
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
        // attention: need to set subject own roles from account
        var.setOwnRoles(account.getOwnRoles());
        return var;
    }

    public void setAccountProvider(SurenessAccountProvider provider) {
        this.accountProvider = provider;
    }

}
