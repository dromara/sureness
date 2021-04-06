package com.usthe.sureness.sample.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * BOOT
 * @author tomsun28
 * @date 17:17 2019-05-12
 */
@SpringBootApplication
@ServletComponentScan
public class SessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionApplication.class, args);
    }
}
