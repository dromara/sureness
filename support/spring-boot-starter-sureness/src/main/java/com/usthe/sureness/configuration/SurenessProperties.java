package com.usthe.sureness.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

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
     * support container type: Servlet, JAX_RS, Spring_Reactor
     * 可配置 支持 Servlet, JAX_RS, Spring_Reactor 容器协议
     */
    private ContainerType container = ContainerType.Servlet;

    /**
     * support auth type: Jwt, basic auth, digest auth
     * 支持的认证方式 Jwt, basic auth, digest auth等其它认证方式
     */
    private AuthType[] authTypes = new AuthType[] {AuthType.BASIC, AuthType.JWT, AuthType.DIGEST};

    /**
     * JWT properties
     * 当 authType 为 JWT 时设置的属性
     */
    private JwtProperties jwt;

    /**
     * whether to enable websocket protect
     * 是否开启 websocket 的认证鉴权
     */
    private boolean websocketEnabled = true;

    /**
     * whether to enable session
     * 是否开启 session
     */
    private boolean sessionEnabled = false;

    /**
     * config for Annotation provider
     */
    private AnnotationProperties annotation;

    public AuthType[] getAuthTypes() {
        return authTypes;
    }

    public void setAuthTypes(AuthType[] authTypes) {
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

    public ContainerType getContainer() {
        return container;
    }

    public void setContainer(ContainerType container) {
        this.container = container;
    }

    public boolean isWebsocketEnabled() {
        return websocketEnabled;
    }

    public void setWebsocketEnabled(boolean websocketEnabled) {
        this.websocketEnabled = websocketEnabled;
    }


    public static enum AuthType {
        /** json web token auth **/
        JWT,
        /** basic auth **/
        BASIC,
        /** digest auth **/
        DIGEST
    }

    public static enum ContainerType {
        /** http servlet **/
        Servlet,
        /** jax-rs **/
        JAX_RS,
        /** spring reactor stream **/
        Spring_Reactor,
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
