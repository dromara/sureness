package com.usthe.sureness.sample.tom.service.impl;

import com.usthe.sureness.sample.tom.pojo.dto.Account;
import com.usthe.sureness.sample.tom.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tomsun28
 * @date 10:58 2019-08-04
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Override
    public boolean authenticateAccount(Account account) {
        return false;
    }

    @Override
    public List<String> loadAccountRoles(String username) {
        return null;
    }

    @Override
    public boolean registerAccount(Account account) {
        return false;
    }

    @Override
    public boolean isAccountExist(Account account) {
        return false;
    }
}
