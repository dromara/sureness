package com.usthe.sureness.provider;

/**
 * load account data interface
 * @author tomsun28
 * @date 23:02 2019-04-02
 */
public interface SurenessAccountProvider {

    /**
     * load account information from database, file or other persistence layer
     * @param appId account appId
     * @return account information
     */
    SurenessAccount loadAccount(String appId);

}
