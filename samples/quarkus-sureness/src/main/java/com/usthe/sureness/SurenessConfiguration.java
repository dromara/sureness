package com.usthe.sureness;


import io.quarkus.runtime.Startup;

import javax.enterprise.context.ApplicationScoped;

/**
 * sureness 配置,使用默认的DefaultSurenessConfig
 * @author tomsun28
 * @date 23:38 2019-05-12
 */
@Startup
@ApplicationScoped
public class SurenessConfiguration {

    /**
     * 初始化sureness默认配置
     */
    public SurenessConfiguration() {
        new DefaultSurenessConfig(DefaultSurenessConfig.SUPPORT_JAX_RS);
    }
}
