package com.usthe.sureness.provider;

import java.util.List;
import java.util.Map;

/**
 * 从yaml文件加载的资源实体 默认文件 sureness.yaml
 * @author tomsun28
 * @date 21:34 2019-05-24
 */
public class DocumentResourceEntity {

    private List<String> resourceRole;

    private List<Map<String, Object>> account;

    public List<String> getResourceRole() {
        return resourceRole;
    }

    public void setResourceRole(List<String> resourceRole) {
        this.resourceRole = resourceRole;
    }

    public List<Map<String, Object>> getAccount() {
        return account;
    }

    public void setAccount(List<Map<String, Object>> account) {
        this.account = account;
    }
}
