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
    List<String> loadAccountRoles(String username);

    /**
     * 注册账户
     * @param account 简单的账户密码注册
     * @return 注册成功返回true 失败false
     */
    boolean registerAccount(Account account);

    /**
     * 判断账户是否已经存在
     * @param account 账户信息
     * @return 存在true 不存在false
     */
    boolean isAccountExist(Account account);

    /**
     * 通过username加载对于的account信息
     * @param username 账户名
     * @return account
     */
    SurenessAccount loadAccount(String username);
}
