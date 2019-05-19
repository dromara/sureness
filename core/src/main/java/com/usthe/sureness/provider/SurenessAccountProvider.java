package com.usthe.sureness.provider;

/**
 * 账户加载接口类
 * @author tomsun28
 * @date 23:02 2019-04-02
 */
public interface SurenessAccountProvider {

    /**
     * 从数据库或者其他持久层加载对应用户的账户信息
     * @param appId 账户标识
     * @return 账户信息
     */
    SurenessAccount loadAccount(String appId);

}
