package com.usthe.sureness.sample.tom.service;

import com.usthe.sureness.sample.tom.pojo.dto.Account;

/**
 * @author tomsun28
 * @date 00:12 2019-08-01
 */
public interface AccountService {
    /**
     * 认证账户有效性  账户名称密码
     * @param account 账户内容
     * @return 成功true 失败false
     */
    boolean authenticateAccount(Account account);

    /**
     * 获取对应username账户所拥有的所有角色，组成字符串
     * @param username 账户名称
     * @return 角色拼接字符串 eg role1,role3,role2
     */
    String loadAccountRoles(String username);
}
