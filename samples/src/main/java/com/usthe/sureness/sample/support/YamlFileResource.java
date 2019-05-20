package com.usthe.sureness.sample.support;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 文件内容映射
 * @author tomsun28
 * @date 23:00 2019-05-20
 */
@Component
@ConfigurationProperties(prefix = "sureness")
public class YamlFileResource {

    private List<String> pathRole;

    private List<Map<String, String>> user;

    private List<Map<String, List<String>>> userRole;

    public List<Map<String, String>> getUser() {
        return user;
    }

    public void setUser(List<Map<String, String>> user) {
        this.user = user;
    }

    public List<String> getPathRole() {
        return pathRole;
    }

    public void setPathRole(List<String> pathRole) {
        this.pathRole = pathRole;
    }

    public List<Map<String, List<String>>> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<Map<String, List<String>>> userRole) {
        this.userRole = userRole;
    }
}
