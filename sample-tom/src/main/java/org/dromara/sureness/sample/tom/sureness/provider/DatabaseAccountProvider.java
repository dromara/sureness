package org.dromara.sureness.sample.tom.sureness.provider;

import org.dromara.sureness.provider.SurenessAccount;
import org.dromara.sureness.provider.SurenessAccountProvider;
import org.dromara.sureness.sample.tom.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * the provider provides account info
 * load account info from database
 * @author tomsun28
 * @date 22:44 2020-03-02
 */
@Component
public class DatabaseAccountProvider implements SurenessAccountProvider {

    @Autowired
    AccountService accountService;

    @Override
    public SurenessAccount loadAccount(String appId) {
        return accountService.loadAccount(appId);
    }
}
