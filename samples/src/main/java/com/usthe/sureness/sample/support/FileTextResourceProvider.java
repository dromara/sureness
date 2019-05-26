package com.usthe.sureness.sample.support;

import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.matcher.SurenessLoadDataException;

import java.util.HashSet;
import java.util.Set;

/**从文件中加载资源 -- 提供者
 * @author tomsun28
 * @date 23:53 2019-05-12
 */
@Deprecated
public class FileTextResourceProvider implements PathTreeProvider {

    private YamlFileResource fileResource;

    public FileTextResourceProvider(YamlFileResource fileResource) {
        this.fileResource = fileResource;
    }

    @Override
    public Set<String> providePathData() throws SurenessLoadDataException {
        return new HashSet<>(fileResource.getPathRole());
    }
}
