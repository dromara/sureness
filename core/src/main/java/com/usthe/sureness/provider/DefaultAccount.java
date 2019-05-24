package com.usthe.sureness.provider;

import java.util.List;

/**
 * 账户实现
 * @author tomsun28
 * @date 16:20 2019-05-19
 */
public class DefaultAccount implements SurenessAccount {

    /** 账户标识 **/
    private String appId;

    /** 密码 **/
    private String password;

    /** 盐值 **/
    private String salt;

    /** 所拥有的角色 **/
    private List<String> ownRoles;

    /** 是否禁用账户 **/
    private boolean disabledAccount;

    /** 是否一定时间内认证次数过多暂时禁用账户认证 **/
    private boolean excessiveAttempts;

    private DefaultAccount(Builder builder) {
        this.appId = builder.appId;
        this.password = builder.password;
        this.salt = builder.salt;
        this.ownRoles = builder.ownRoles;
        this.disabledAccount = builder.disabledAccount;
        this.excessiveAttempts = builder.excessiveAttempts;
    }


    @Override
    public String getAppId() {
        return this.appId;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getSalt() {
        return this.salt;
    }

    @Override
    public List<String> getOwnRoles() {
        return this.ownRoles;
    }

    @Override
    public boolean isDisabledAccount() {
        return this.disabledAccount;
    }

    @Override
    public boolean isExcessiveAttempts() {
        return this.excessiveAttempts;
    }

    public static Builder builder(String appId) {
        return new Builder(appId);
    }

    public static class Builder {

        private String appId;
        private String password;
        private String salt;
        private List<String> ownRoles;
        private boolean disabledAccount;
        private boolean excessiveAttempts;

        public Builder(String appId) {
            this.appId = appId;
        }

        public Builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setSalt(String salt) {
            this.salt = salt;
            return this;
        }

        public Builder setOwnRoles(List<String> ownRoles) {
            this.ownRoles = ownRoles;
            return this;
        }

        public Builder setDisabledAccount(boolean disabledAccount) {
            this.disabledAccount = disabledAccount;
            return this;
        }

        public Builder setExcessiveAttempts(boolean excessiveAttempts) {
            this.excessiveAttempts = excessiveAttempts;
            return this;
        }

        public DefaultAccount build() {
            return new DefaultAccount(this);
        }
    }
}
