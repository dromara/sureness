package com.usthe.sureness.provider;


import java.util.List;

/**
 * account for sureness
 * @author tomsun28
 * @date 23:18 2019-04-02
 */
public interface SurenessAccount {

     /**
      * get appId, identifier
      * @return appId
      */
     String getAppId();

     /**
      * get user password
      * @return password
      */
     String getPassword();

     /**
      * get salt
      * @return salt
      */
     String getSalt();

     /**
      * get the roles owned by this account
      * @return roles
      */
     List<String> getOwnRoles();

     /**
      * if is a disable account
      * @return disable return true, else false
      */
     boolean isDisabledAccount();

     /**
      * Whether there are too many attempts after routine authentication failure,
      * exceeding the number set by the system
      * @return yes return true, else false
      */
     boolean isExcessiveAttempts();

}

