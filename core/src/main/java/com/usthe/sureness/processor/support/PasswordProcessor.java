package com.usthe.sureness.processor.support;

import com.usthe.sureness.processor.BaseProcessor;
import com.usthe.sureness.processor.exception.IncorrectCredentialsException;
import com.usthe.sureness.processor.exception.SurenessAuthenticationException;
import com.usthe.sureness.processor.exception.SurenessAuthorizationException;
import com.usthe.sureness.processor.exception.UnknownAccountException;
import com.usthe.sureness.provider.Account;
import com.usthe.sureness.provider.AccountProvider;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import com.usthe.sureness.subject.support.PasswordSubjectToken;
import com.usthe.sureness.util.Md5Util;

/**
 * 支持 username password 类型token的处理器实例
 * @author tomsun28
 * @date 12:38 2019-03-13
 */
public class PasswordProcessor extends BaseProcessor {


    private AccountProvider accountProvider;

    @Override
    public boolean canSupportAuTokenClass(Class<?> var) {
        return var != null && var == PasswordSubjectToken.class;
    }

    @Override
    public Class<?> getSupportAuTokenClass() {
        // 这里只支持passwordToken
        // username/appId/email/phoneNum + password
        return PasswordSubjectToken.class;
    }

    @Override
    public boolean authenticated(SubjectAuToken var) throws SurenessAuthenticationException {
        String appId = (String) var.getPrincipal();
        Account account = accountProvider.loadAccount(appId);
        if (account == null) {
            throw new  UnknownAccountException("no the account: " + appId);
        }
        String password = Md5Util.md5((String) var.getCredentials() + account.getSalt());
        if (password == null || !password.equals(account.getPassword())) {
            throw new IncorrectCredentialsException("incorrect password");
        }
        return true;
    }

    @Override
    public boolean authorized(SubjectAuToken var) throws SurenessAuthorizationException {
        return true;
    }

    @Override
    public Subject createSubject(SubjectAuToken var) {
        return null;
    }

}
