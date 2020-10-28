package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.Subject;

import java.util.List;

/**
 * subject for digest auth
 * @author tomsun28
 * @date 2020-10-28 20:44
 */
public class DigestSubject implements Subject {

    private static final long serialVersionUID = 1L;

    /** 用户标识 **/
    private String appId;

    /** 安全域 **/
    private String realm;

    /** uri **/
    private String uri;

    /** 保护质量，包含auth（默认的）和 auth-int **/
    private String qop;

    /** 服务端向客户端发送质询时附带的一个随机数 **/
    private String nonce;

    /** nonce计数器，是一个16进制的数值 **/
    private String nc;

    /** 客户端随机数 **/
    private String cnonce;

    /** 加密后的口令 **/
    private String response;

    /** 请求的http method **/
    private String httpMethod;

    /** 访问用户的IP **/
    private String remoteHost;

    /** 所拥有的角色 在解析完jwt之后把用户角色放到这里 **/
    private List<String> ownRoles;

    /** 所访问资源地址 **/
    private String targetUri;

    /** 所访问资源他支持的角色 **/
    private List<String> supportRoles;

    public DigestSubject() {}

    private DigestSubject(Builder builder) {
        this.appId = builder.appId;
        this.response = builder.response;
        this.realm = builder.realm;
        this.uri = builder.uri;
        this.qop = builder.qop;
        this.nonce = builder.nonce;
        this.nc = builder.nc;
        this.cnonce = builder.cnonce;
        this.httpMethod = builder.httpMethod;
        this.remoteHost = builder.remoteHost;
        this.ownRoles = builder.ownRoles;
        this.targetUri = builder.targetUri;
        this.supportRoles = builder.supportRoles;
    }

    @Override
    public Object getPrincipal() {
        return appId;
    }

    @Override
    public Object getCredentials() {
        return response;
    }

    @Override
    public Object getOwnRoles() {
        return ownRoles;
    }

    @Override
    public Object getTargetResource() {
        return targetUri;
    }

    @Override
    public Object getSupportRoles() {
        return supportRoles;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setSupportRoles(Object var1) {
        this.supportRoles = (List<String>) var1;
    }

    public String getRealm() {
        return realm;
    }

    public String getUri() {
        return uri;
    }

    public String getQop() {
        return qop;
    }

    public String getNonce() {
        return nonce;
    }

    public String getNc() {
        return nc;
    }

    public String getCnonce() {
        return cnonce;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public static DigestSubject.Builder builder(String username, String response) {
        return new DigestSubject.Builder(username, response);
    }

    public static DigestSubject.Builder builder(Subject auToken) {
        return new DigestSubject.Builder(auToken);
    }

    public static class Builder {

        private String appId;
        private String response;
        private String realm;
        private String uri;
        private String qop;
        private String nonce;
        private String nc;
        private String cnonce;
        private String httpMethod;
        private String remoteHost;
        private List<String> ownRoles;
        private String targetUri;
        private List<String> supportRoles;

        public Builder(String username, String response) {
            this.appId = username;
            this.response = response;
        }

        @SuppressWarnings("unchecked")
        public Builder(Subject auToken) {
            this.appId = String.valueOf(auToken.getPrincipal());
            this.response = String.valueOf(auToken.getCredentials());
            this.ownRoles = (List<String>) auToken.getOwnRoles();
            this.targetUri = String.valueOf(auToken.getTargetResource());
            this.supportRoles = (List<String>) auToken.getSupportRoles();
        }

        public DigestSubject.Builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public DigestSubject.Builder setResponse(String response) {
            this.response = response;
            return this;
        }

        public DigestSubject.Builder setRealm(String realm) {
            this.realm = realm;
            return this;
        }

        public DigestSubject.Builder setUri(String uri) {
            this.uri = uri;
            return this;
        }

        public DigestSubject.Builder setQop(String qop) {
            this.qop = qop;
            return this;
        }

        public DigestSubject.Builder setNonce(String nonce) {
            this.nonce = nonce;
            return this;
        }

        public DigestSubject.Builder setNc(String nc) {
            this.nc = nc;
            return this;
        }

        public DigestSubject.Builder setCnonce(String cnonce) {
            this.cnonce = cnonce;
            return this;
        }

        public DigestSubject.Builder setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public DigestSubject.Builder setTargetUri(String targetUri) {
            this.targetUri = targetUri;
            return this;
        }

        public DigestSubject.Builder setRemoteHost(String remoteHost) {
            this.remoteHost = remoteHost;
            return this;
        }

        public DigestSubject.Builder setOwnRoles(List<String> ownRoles) {
            this.ownRoles = ownRoles;
            return this;
        }

        public DigestSubject.Builder setSupportRoles(List<String> supportRoles) {
            this.supportRoles = supportRoles;
            return this;
        }

        public DigestSubject build() {
            return new DigestSubject(this);
        }

    }
}
