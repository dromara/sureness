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
    private boolean enable = true;

    /**
     * support container type: Servlet, JAX_RS, Spring_Reactor
     * 可配置 支持 Servlet, JAX_RS, Spring_Reactor 容器协议
     */
    private ContainerType container = ContainerType.Servlet;

    /**
     * support auth type: Jwt, basic auth, digest auth
     * 支持的认证方式 Jwt, basic auth, digest auth等其它认证方式
     */
    private AuthType[] auths = new AuthType[] {AuthType.BASIC, AuthType.JWT, AuthType.DIGEST};

    /**
     * JWT properties
     * 当 authType 为 JWT 时设置的属性
     */
    private JwtProperties jwt;

    private SessionProperties session;

    private WebSocketProperties websocket;

    /**
     * config for Annotation provider
     */
    private AnnotationProperties annotation;

    public AuthType[] getAuths() {
        return auths;
    }

    public void setAuths(AuthType[] auths) {
        this.auths = auths;
    }

    public JwtProperties getJwt() {
        return jwt;
    }

    public void setJwt(JwtProperties jwt) {
        this.jwt = jwt;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
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

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public WebSocketProperties getWebsocket() {
        return websocket;
    }

    public void setWebsocket(WebSocketProperties websocket) {
        this.websocket = websocket;
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

        private String secret;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    public static class SessionProperties {
        /**
         * whether to enable session
         * 是否开启 session
         */
        private boolean enable = false;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }

    public static class WebSocketProperties {
        /**
         * whether to enable websocket protect
         * 是否开启 websocket 的认证鉴权
         */
        private boolean enable = false;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }
}
