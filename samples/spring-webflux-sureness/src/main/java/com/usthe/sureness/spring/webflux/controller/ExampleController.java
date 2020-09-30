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
        return ResponseEntity.status(Integer.parseInt(statusCode)).body(errorMsg);
    }
}
