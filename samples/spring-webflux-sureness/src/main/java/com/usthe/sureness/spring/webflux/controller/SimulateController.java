package com.usthe.sureness.spring.webflux.controller;


import org.springframework.http.ResponseEntity;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 模拟资源controller,供测试调用
 * @author tomsun28
 * @date 17:35 2019-05-12
 */
@RestController
public class SimulateController {

    public static final String SUCCESS_ACCESS_RESOURCE = "access this resource: %s success";

    @GetMapping("/api/v1/source1")
    public ResponseEntity<Map<String, String>> api1Mock1(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PostMapping("/api/v1/source1")
    public ResponseEntity<Map<String, String>> api1Mock2(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PutMapping("/api/v1/source1")
    public ResponseEntity<Map<String, String>> api1Mock3(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @DeleteMapping("/api/v1/source1")
    public ResponseEntity<Map<String, String>> api1Mock4(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PatchMapping("/api/v1/source1")
    public ResponseEntity<Map<String, String>> api1Mock5(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @GetMapping("/api/v1/source2/{var1}/{var2}")
    public ResponseEntity<Map<String, String>> api1Mock6(ServerWebExchange exchange, @PathVariable String var1, @PathVariable Integer var2 ) {
        Map<String, String> resultMap = new HashMap<>(3);
        resultMap.putAll(getResponseMap(exchange));
        if (!StringUtils.isEmpty(var1)) {
            resultMap.put("var1", var1);
        }
        if (Objects.nonNull(var2)) {
            resultMap.put("var2", String.valueOf(var2));
        }
        return ResponseEntity.ok(resultMap);
    }

    @PostMapping("/api/v1/source2/{var1}")
    public ResponseEntity<Map<String, String>> api1Mock7(ServerWebExchange exchange, @PathVariable String var1) {
        Map<String, String> resultMap = new HashMap<>(2);
        resultMap.putAll(getResponseMap(exchange));
        if (!StringUtils.isEmpty(var1)) {
            resultMap.put("var1", var1);
        }
        return ResponseEntity.ok(resultMap);
    }

    @DeleteMapping("/api/v1/source2/{var2}")
    public ResponseEntity<Map<String, String>> api1Mock8(ServerWebExchange exchange, @PathVariable Integer var2 ) {
        Map<String, String> resultMap = new HashMap<>(2);
        resultMap.putAll(getResponseMap(exchange));
        if (Objects.nonNull(var2)) {
            resultMap.put("var2", String.valueOf(var2));
        }
        return ResponseEntity.ok(resultMap);
    }

    @PutMapping("/api/v1/source2")
    public ResponseEntity<Map<String, String>> api1Mock9(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PatchMapping("/api/v1/source2")
    public ResponseEntity<Map<String, String>> api1Mock10(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @GetMapping("/api/v1/source3")
    public ResponseEntity<Map<String, String>> api1Mock11(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PostMapping("/api/v1/source3")
    public ResponseEntity<Map<String, String>> api1Mock12(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PutMapping("/api/v1/source3")
    public ResponseEntity<Map<String, String>> api1Mock13(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @DeleteMapping("/api/v1/source3")
    public ResponseEntity<Map<String, String>> api1Mock14(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PatchMapping("/api/v1/source3")
    public ResponseEntity<Map<String, String>> api1Mock15(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @GetMapping("/api/v2/source4")
    public ResponseEntity<Map<String, String>> api2Mock16(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PostMapping("/api/v2/source4")
    public ResponseEntity<Map<String, String>> api2Mock17(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PutMapping("/api/v2/source4")
    public ResponseEntity<Map<String, String>> api2Mock18(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @DeleteMapping("/api/v2/source4")
    public ResponseEntity<Map<String, String>> api2Mock19(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PatchMapping("/api/v2/source4")
    public ResponseEntity<Map<String, String>> api2Mock20(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @GetMapping("/api/v2/source5")
    public ResponseEntity<Map<String, String>> api2Mock21(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PostMapping("/api/v2/source5")
    public ResponseEntity<Map<String, String>> api2Mock22(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @DeleteMapping("/api/v2/source5")
    public ResponseEntity<Map<String, String>> api2Mock23(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PutMapping("/api/v2/source5")
    public ResponseEntity<Map<String, String>> api2Mock24(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PatchMapping("/api/v2/source5")
    public ResponseEntity<Map<String, String>> api2Mock25(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @GetMapping("/api/v2/source6")
    public ResponseEntity<Map<String, String>> api2Mock26(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PostMapping("/api/v2/source6")
    public ResponseEntity<Map<String, String>> api2Mock27(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }
    @DeleteMapping("/api/v2/source6")
    public ResponseEntity<Map<String, String>> api2Mock28(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }
    @PutMapping("/api/v2/source6")
    public ResponseEntity<Map<String, String>> api2Mock29(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }
    @PatchMapping("/api/v2/source6")
    public ResponseEntity<Map<String, String>> api2Mock30(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    /**
     * 获取MOCK固定的返回数据MAP
     * @param exchange http 请求
     * @return 返回数据
     */
    private Map<String, String> getResponseMap(ServerWebExchange exchange) {
        ServerHttpRequest httpRequest = exchange.getRequest();
        StringBuilder builder = new StringBuilder();
        String requestUri = httpRequest.getPath().value();
        builder.append(requestUri);
        builder.append("--");
        String requestType = httpRequest.getMethodValue();
        builder.append(requestType);
        builder.append("--");
        return Collections.singletonMap("result", String.format(SUCCESS_ACCESS_RESOURCE, builder.toString()));
    }
}
