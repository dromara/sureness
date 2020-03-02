package com.usthe.sureness.sample.tom.sureness.provider;

import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.sample.tom.dao.AuthResourceDao;
import com.usthe.sureness.sample.tom.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 从数据库加载资源配置信息和账户信息提供者
 * @author tomsun28
 * @date 16:00 2019-08-04
 */
@Component
public class ResourceProvider implements PathTreeProvider {

    @Autowired
    private ResourceService resourceService;

    @Override
    public Set<String> providePathData() {
        return resourceService.getAllEnableResourcePath();

    }

    @Override
    public Set<String> provideExcludedResource() {
        return resourceService.getAllDisableResourcePath();
    }

}
