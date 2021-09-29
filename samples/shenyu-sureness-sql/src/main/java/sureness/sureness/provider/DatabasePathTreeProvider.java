package sureness.sureness.provider;

import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.util.SurenessCommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sureness.service.ResourceService;

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
