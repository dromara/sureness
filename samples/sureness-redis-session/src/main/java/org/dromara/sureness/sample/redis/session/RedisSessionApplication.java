package org.dromara.sureness.sample.redis.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * BOOT
 * @author tomsun28
 * @date 17:17 2019-05-12
 */
@SpringBootApplication
@ServletComponentScan
@EnableRedisHttpSession
public class RedisSessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisSessionApplication.class, args);
    }
}
