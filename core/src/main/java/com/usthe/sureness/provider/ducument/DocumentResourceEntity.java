package com.usthe.sureness.provider.ducument;

import java.util.List;
import java.util.Map;

/**
 * sureness config information entity
 * @author tomsun28
 * @date 21:34 2019-05-24
 */
public class DocumentResourceEntity {

    private List<String> resourceRole;

    private List<String> excludedResource;

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

    public List<String> getExcludedResource() {
        return excludedResource;
    }

    public void setExcludedResource(List<String> excludedResource) {
        this.excludedResource = excludedResource;
    }
}
