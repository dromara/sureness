package com.usthe.sureness.matcher;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * path tree resource data provider
 * @author tomsun28
 * @date 22:30 2019-02-25
 */
public interface PathTreeProvider {

    /**
     * web server context path set, default is null
     * Please sync with your server context path settings here
     * eg: springboot context path is: server.servlet.context-path = v2
     *     tomcat context path is: <context path="v2">
     */
    AtomicReference<String> CONTEXT_PATH_REF = new AtomicReference<>();
    /**
     * set context path
     * Please sync with your server context path settings here
     * @param contextPath context path
     */
    default void setContextPath(String contextPath) {
        CONTEXT_PATH_REF.set(contextPath);
    }

    /**
     * get context path
     * Please sync with your server context path settings use setContextPath
     * @return  context path
     */
    default String getContextPath() {
        return CONTEXT_PATH_REF.get();
    }

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
