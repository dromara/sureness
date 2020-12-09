package com.usthe.sureness.matcher;

import java.util.Set;

/**
 * path tree resource data provider
 * @author tomsun28
 * @date 22:30 2019-02-25
 */
public interface PathTreeProvider {

    /**
     * Interface for loading URL resources.
     * Can load data from database, text load data, etc.
     * @return uri set, uri:eg: /api/v2/host===post===[role2,role3,role4]
     * @throws SurenessLoadDataException when加载数据异常
     */
    Set<String> providePathData();

    /**
     * Interface for loading URL exclude resources.
     * Can load data from database, text load data, etc.
     * @return uri set, uri: URL===METHOD, eg: /api/v4/host===post
     */
    Set<String> provideExcludedResource();
}
