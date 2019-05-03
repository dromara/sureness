package com.usthe.sureness.matcher;

import java.util.Set;

/**
 * @author tomsun28
 * @date 22:30 2019-02-25
 */
public interface PathTreeProvider {

    /**
     *  加载URL资源的接口，可从数据库加载数据，文本加载数据等
     * @return java.util.Set<java.lang.String>
     * @throws SurenessLoadDataException when加载数据异常
     */
    Set<String> providePathData() throws SurenessLoadDataException;
}
