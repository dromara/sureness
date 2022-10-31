package gateway.shenyu;

import com.usthe.sureness.DefaultSurenessConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @title: SurenessConfiguration
 * @Author hqgordon
 * @Date: 2021/7/31 5:13 下午
 * @Description: 配置类
 * @Version 1.0
 */
@Configuration
public class SurenessConfiguration {
    /**
     * new sureness default config bean
     * @return default config bean
     */
    @Bean
    public DefaultSurenessConfig surenessConfig() {
        return new DefaultSurenessConfig(DefaultSurenessConfig.SUPPORT_SPRING_REACTIVE);
    }

}
