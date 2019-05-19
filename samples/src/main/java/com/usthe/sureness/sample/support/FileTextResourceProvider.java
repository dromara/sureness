package com.usthe.sureness.sample.support;

import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.matcher.SurenessLoadDataException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**从文件中加载资源 -- 提供者
 * @author tomsun28
 * @date 23:53 2019-05-12
 */
@Component
public class FileTextResourceProvider implements PathTreeProvider {
    @Override
    public Set<String> providePathData() throws SurenessLoadDataException {
        Set<String> paths = new HashSet<>();
        // 暂时不从文件  构造一些伪数据测试
        paths.add("/===post===jwt[role2,role3,role4]");
        paths.add("/api/v2/host===post===jwt[role2,role3,role4]");
        paths.add("/api/v2/host===get===jwt[role2,role3,role4]");
        paths.add("/api/v2/host===delete===jwt[role2,role3,role4]");
        paths.add("/api/v2/host===put===jwt[role2,role3,role4]");
        paths.add("/api/v1/host===put===jwt[role2,role3,role4]");
        paths.add("/api/v3/host===put===jwt[role2,role3,role4]");
        paths.add("/api/v2/detail===put===jwt[role2,role3,role4]");
        paths.add("/api/v2/mom===put===jwt[role2,role3,role4]");
        paths.add("/api/*/mom/ha===put===jwt[role2,role3,role4]");
        paths.add("/api/mi/**===put===jwt[role2,role3,role4]");
        return paths;
    }
}
