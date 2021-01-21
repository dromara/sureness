package com.usthe.sureness.spring.webflux.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tomsun28
 * @date 2020-09-29 21:57
 */
@RestController
public class ExampleController {


    @GetMapping("/hello")
    public String hello() {
        return "Hello, WebFlux !";
    }

    @GetMapping("/user")
    public String getUser() {
        return "hi i am tom!";
    }

    @GetMapping("/auth/error")
    public ResponseEntity<String> authError(@RequestHeader(value = "statusCode") String statusCode,
                                                               @RequestHeader(value = "errorMsg") String errorMsg) {
        return ResponseEntity.status(Integer.parseInt(statusCode)).body(cleanXss(errorMsg));
    }

    /**
     * xss clean
     * @param value value
     * @return clean value
     */
    private String cleanXss(String value) {
        //You'll need to remove the spaces from the html entities below
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }
}
