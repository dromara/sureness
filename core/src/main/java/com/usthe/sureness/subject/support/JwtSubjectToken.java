package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.SubjectAuToken;

import java.util.List;

/**
 * @author tomsun28
 * @date 12:28 2019-03-14
 */
public class JwtSubjectToken implements SubjectAuToken {


    private static final long serialVersionUID = 1L;

    /**
     * 用户的标识
     */
    private String appId;
    /**
     * 用户的IP
     */
    private String ipHost;
    /**
     * 设备信息
     */
    private String deviceInfo;
    /**
     * json web token值
     */
    private String jwt;
    /**
     * 所拥有的角色 在解析完jwt之后把用户角色放到这里
     */
    private List<String> roles;
    /**
     * 所访问资源地址
     */
    private String targetUri;
    /**
     * 所访问资源他支持的角色
     */
    private List<String> supportRoles;

    @Override
    public Object getPrincipal() {
        return this.appId;
    }

    @Override
    public Object getCredentials() {
        return this.jwt;
    }

    @Override
    public Object getOwnRoles() {
        return this.roles;
    }

    @Override
    public Object getTargetResource() {
        return this.targetUri;
    }

    @Override
    public Object getSupportRoles() {
        return this.supportRoles;
    }

    @Override
    public void setSupportRoles(Object var1) {
        this.supportRoles = (List<String>) var1;
    }
}
