package org.dromara.sureness.sample.tom.sureness.provider;

import org.dromara.sureness.matcher.PathTreeProvider;
import org.dromara.sureness.sample.tom.service.ResourceService;
import org.dromara.sureness.util.SurenessCommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * ths provider provides path resources
 * load sureness config resource form database
 * @author tomsun28
 * @date 16:00 2019-08-04
 */
@Component
public class DatabasePathTreeProvider implements PathTreeProvider {

    @Autowired
    private ResourceService resourceService;

    @Override
    public Set<String> providePathData() {
        return SurenessCommonUtil.attachContextPath(getContextPath(), resourceService.getAllEnableResourcePath());

    }

    @Override
    public Set<String> provideExcludedResource() {
        return SurenessCommonUtil.attachContextPath(getContextPath(), resourceService.getAllDisableResourcePath());
    }

}
