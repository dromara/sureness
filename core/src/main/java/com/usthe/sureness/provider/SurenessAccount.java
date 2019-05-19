package com.usthe.sureness.provider;


import java.util.List;

/**
 * @author tomsun28
 * @date 23:18 2019-04-02
 */
public interface SurenessAccount {

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

     /**
      * 是否是禁用账户
      * @return 是禁用账户返回true 不是返回false
      */
     boolean isDisabledAccount();

     /**
      * 是否常规认证失败后尝试次数太多，超过系统设定的次数
      * @return 是常规认证次数达到阈值，账户暂时不能使用，返回true 否则返回false
      */
     boolean isExcessiveAttempts();

}

