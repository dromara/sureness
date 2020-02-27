package com.usthe.sureness.sample.tom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author tomsun28
 * @date 23:13 2019-05-29
 */
@SpringBootApplication
@ServletComponentScan
public class TomApplication {

    public static void main(String[] args) {
        SpringApplication.run(TomApplication.class, args);
    }
}