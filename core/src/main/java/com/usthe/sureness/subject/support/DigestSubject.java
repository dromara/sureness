package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.PrincipalMap;
import com.usthe.sureness.subject.Subject;

import java.util.List;

/**
 * subject for digest auth
 * @author tomsun28
 * @date 2020-10-28 20:44
 */
public class DigestSubject implements Subject {

    private static final long serialVersionUID = 1L;

    /** appId, account identifier **/
    private String appId;

    /** account realm **/
    private String realm;

    /** uri **/
    private String uri;

    /** qop，include auth(default) and auth-int **/
    private String qop;

    /** A random number attached when the server sends a challenge to the client **/
    private String nonce;

    /** nonce counter, is a hexadecimal value **/
    private String nc;

    /** Client random number **/
    private String cnonce;

    /** Encrypted password **/
    private String response;

    /** http method **/
    private String httpMethod;

    /** remote IP **/
    private String remoteHost;

    /** the roles which this user owned **/
    private List<String> ownRoles;

    /** the uri resource which this user want access **/
    private String targetUri;

    /** the Roles which can access this resource above-targetUri **/
    private List<String> supportRoles;

    /** the map for principal, add your custom principal **/
    private PrincipalMap principalMap;

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
        this.principalMap = builder.principalMap;
    }

    @Override
    public Object getPrincipal() {
        return appId;
    }

    @Override
    public void setPrincipal(Object var1) {
        this.appId = (String) var1;
    }

    @Override
    public PrincipalMap getPrincipalMap() {
        return principalMap;
    }

    @Override
    public void setPrincipalMap(PrincipalMap var1) {
        this.principalMap = var1;
    }

    @Override
    public Object getCredential() {
        return response;
    }

    @Override
    public void setCredential(Object var1) {
        this.response = (String) var1;
    }

    @Override
    public Object getOwnRoles() {
        return ownRoles;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setOwnRoles(Object var1) {
        this.ownRoles = (List<String>) var1;
    }

    @Override
    public Object getTargetResource() {
        return targetUri;
    }

    @Override
    public void setTargetResource(Object var1) {
        this.targetUri = (String) var1;
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

    public static DigestSubject.Builder builder(Subject subject) {
        return new DigestSubject.Builder(subject);
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
        private PrincipalMap principalMap;

        public Builder(String username, String response) {
            this.appId = username;
            this.response = response;
        }

        @SuppressWarnings("unchecked")
        public Builder(Subject subject) {
            this.appId = String.valueOf(subject.getPrincipal());
            this.response = String.valueOf(subject.getCredential());
            this.ownRoles = (List<String>) subject.getOwnRoles();
            this.targetUri = String.valueOf(subject.getTargetResource());
            this.supportRoles = (List<String>) subject.getSupportRoles();
            this.principalMap = subject.getPrincipalMap();
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

        public DigestSubject.Builder setPrincipalMap(PrincipalMap principalMap) {
            this.principalMap = principalMap;
            return this;
        }

        public DigestSubject build() {
            return new DigestSubject(this);
        }

    }
}
