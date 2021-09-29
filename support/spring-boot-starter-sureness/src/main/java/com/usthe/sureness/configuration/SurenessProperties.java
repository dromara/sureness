package com.usthe.sureness.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author wangtao
 * @date 2021/7/3
 */
@Component
@ConfigurationProperties(prefix = "sureness")
public class SurenessProperties {

    /**
     * sureness enabled, default true
     */
    private boolean enabled = true;

    /**
     * 可配置 支持 websocket, servlet, jax-rs或者其它容器协议
     */
    private Set<SupportType> supportTypes;

    /**
     * 支持的认证方式 Jwt, basic auth, digest auth等其它认证方式
     */
    private Set<AuthType> authTypes;

    /**
     * 当 authType 为 jwt 时设置的属性
     */
    private JwtProperties jwt;

    /**
     * 是否开启 session
     */
    private boolean sessionEnabled = false;

    /**
     * config for Annotation provider
     */
    private AnnotationProperties annotation;

    public Set<SupportType> getSupportTypes() {
        return supportTypes;
    }

    public void setSupportTypes(Set<SupportType> supportTypes) {
        this.supportTypes = supportTypes;
    }

    public Set<AuthType> getAuthTypes() {
        return authTypes;
    }

    public void setAuthTypes(Set<AuthType> authTypes) {
        this.authTypes = authTypes;
    }

    public JwtProperties getJwt() {
        return jwt;
    }

    public void setJwt(JwtProperties jwt) {
        this.jwt = jwt;
    }

    public boolean isSessionEnabled() {
        return sessionEnabled;
    }

    public void setSessionEnabled(boolean sessionEnabled) {
        this.sessionEnabled = sessionEnabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public AnnotationProperties getAnnotation() {
        return annotation;
    }

    public void setAnnotation(AnnotationProperties annotation) {
        this.annotation = annotation;
    }

    public static enum AuthType {
        /** json web token auth **/
        JWT,
        /** basic auth **/
        BASIC,
        /** digest auth **/
        DIGEST
    }

    public static enum SupportType {
        /** http servlet **/
        Servlet,
        /** jax-rs **/
        JAX_RS,
        /** spring reactor stream **/
        Spring_Reactor,
        /** websocket **/
        WebSocket
    }

    public static class AnnotationProperties {

        private boolean enable = false;

        private List<String> scanPackages;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public List<String> getScanPackages() {
            return scanPackages;
        }

        public void setScanPackages(List<String> scanPackages) {
            this.scanPackages = scanPackages;
        }
    }

    public static class JwtProperties {

        private String secretKey;

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }
    }
}
