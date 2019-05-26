package com.usthe.sureness.sample.support;

import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.matcher.SurenessLoadDataException;

import java.util.Set;

/**
 * 从数据库中加载资源 -- 提供者
 * @author tomsun28
 * @date 23:52 2019-05-12
 */
@Deprecated
public class DatabaseResourceProvider implements PathTreeProvider {

    @Override
    public Set<String> providePathData() throws SurenessLoadDataException {
        return null;
    }
}
