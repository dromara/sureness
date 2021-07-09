package com.usthe.sureness.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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
     * 可配置 支持 websocket, servlet, jax-rs或者其它容器协议
     */
    private String supportType;

    /**
     *   支持的认证方式 Jwt, basic auth, digest auth等其它认证方式
     */
    private Set<String> authTypes;

    /**
     *     jwt的解析加密密钥
     */
    private String token;

    private List<String> scanPackages;

    public List<String> getScanPackages() {
        return scanPackages;
    }

    public void setScanPackages(List<String> scanPackages) {
        this.scanPackages = scanPackages;
    }

    public String getSupportType() {
        return supportType;
    }

    public void setSupportType(String supportType) {
        this.supportType = supportType;
    }


    public Set<String> getAuthTypes() {
        return authTypes;
    }

    public void setAuthTypes(Set<String> authTypes) {
        this.authTypes = authTypes;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "SurenessProperties{" +
                "supportType='" + supportType + '\'' +
                ", authType='" + Arrays.toString(authTypes.toArray(new String[]{})) + '\'' +
                ", token='" + token + '\'' +
                ", scanPackages=" + Arrays.toString(scanPackages.toArray(new String[]{})) +
                '}';
    }
}
