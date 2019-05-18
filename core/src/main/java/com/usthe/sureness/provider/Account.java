package com.usthe.sureness.provider;


import java.util.List;

/**
 * @author tomsun28
 * @date 23:18 2019-04-02
 */
public interface Account {

     /**
      * 获取用户的标识
      * @return 用户标识
      */
     String getAppId();

     /**
      * 获取用户的账户密码
      * @return 账户密码
      */
     String getPassword();

     /**
      * 获取盐
      * @return 加盐
      */
     String getSalt();

     /**
      * 获取用户所拥有的角色
      * @return 拥有的角色
      */
     List<String> getOwnRoles();

}

