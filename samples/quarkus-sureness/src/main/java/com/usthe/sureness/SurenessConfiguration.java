package com.usthe.sureness;


import io.quarkus.runtime.Startup;

import javax.enterprise.context.ApplicationScoped;

/**
 * sureness config,Use DefaultSurenessConfig
 * @author tomsun28
 * @date 23:38 2019-05-12
 */
@Startup
@ApplicationScoped
public class SurenessConfiguration {

    /**
     * init sureness default config
     */
    public SurenessConfiguration() {
        new DefaultSurenessConfig(DefaultSurenessConfig.SUPPORT_JAX_RS);
    }
}
