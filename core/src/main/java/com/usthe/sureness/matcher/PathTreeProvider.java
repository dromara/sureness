package com.usthe.sureness.matcher;

import java.util.Set;

/**
 * 资源的数据源
 * @author tomsun28
 * @date 22:30 2019-02-25
 */
public interface PathTreeProvider {

    /**
     *  加载URL资源的接口，可从数据库加载数据，文本加载数据等 eg: /api/v2/host===post===[role2,role3,role4]
     * @return uri资源set
     * @throws SurenessLoadDataException when加载数据异常
     */
    Set<String> providePathData();
}
