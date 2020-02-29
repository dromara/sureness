package com.usthe.sureness.sample.bootstrap.config;

import com.usthe.sureness.DefaultSurenessConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * sureness 配置,使用默认的DefaultSurenessConfig
 * @author tomsun28
 * @date 23:38 2019-05-12
 */
@Configuration
public class SurenessConfiguration {

    /**
     * 新建初始化sureness默认配置加入bean池
     * @return default config bean
     */
    @Bean
    public DefaultSurenessConfig surenessConfig() {
        return new DefaultSurenessConfig();
    }

}
