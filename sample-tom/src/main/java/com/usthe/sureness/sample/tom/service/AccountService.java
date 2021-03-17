package com.usthe.sureness.sample.tom.service;

import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.sample.tom.pojo.dto.Account;

import java.util.List;

/**
 * @author tomsun28
 * @date 00:12 2019-08-01
 */
public interface AccountService {
    /**
     * Verify account validity, username and password
     * @param account account info
     * @return success-true failed-false
     */
    boolean authenticateAccount(Account account);

    /**
     * Get all roles owned by this username account, combine them into string list
     * @param username account username
     * @return role-string eg role1,role3,role2
     */
    List<String> loadAccountRoles(String username);

    /**
     * register account
     * @param account account info
     * @return success-true failed-false
     */
    boolean registerAccount(Account account);

    /**
     * Determine whether the account already exists
     * @param account account info
     * @return exist-true no-false
     */
    boolean isAccountExist(Account account);

    /**
     * Load the account information by username
     * @param username account username
     * @return account
     */
    SurenessAccount loadAccount(String username);

    /**
     * authority User Role by username and roleId
     * @param appId account username
     * @param roleId roleId
     * @return success-true failed-false
     */
    boolean authorityUserRole(String appId, Long roleId);

    /**
     * delete authority User Role by username and roleId
     * @param appId account username
     * @param roleId roleId
     * @return success-true failed-false
     */
    boolean deleteAuthorityUserRole(String appId, Long roleId);
}
