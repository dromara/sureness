package com.usthe.sureness.sample.tom.sureness.provider;

import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.sample.tom.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tomsun28
 * @date 22:44 2020-03-02
 */
@Component
public class AccountProvider implements SurenessAccountProvider {

    @Autowired
    AccountService accountService;

    @Override
    public SurenessAccount loadAccount(String appId) {
        return accountService.loadAccount(appId);
    }
}
