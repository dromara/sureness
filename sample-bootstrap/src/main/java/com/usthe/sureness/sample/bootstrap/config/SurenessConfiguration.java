package com.usthe.sureness.sample.bootstrap.config;

import com.usthe.sureness.DefaultSurenessConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * sureness 配置
 * @author tomsun28
 * @date 23:38 2019-05-12
 */
@Configuration
public class SurenessConfiguration {

    @Bean
    public DefaultSurenessConfig surenessConfig() {
        return DefaultSurenessConfig.getInstance();
    }

}
