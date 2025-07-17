package org.dromara.sureness.spring.webflux.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;


import java.util.Collections;
import java.util.Map;

/**
 * simulate resource controller, for testing
 * @author tomsun28
 * @date 17:35 2019-05-12
 */
@RestController
public class SimulateController {

    public static final String SUCCESS_ACCESS_RESOURCE = "access this resource: %s success";

    @GetMapping("/api/v1/bar/{id}")
    public ResponseEntity<Map<String, String>> api1Mock1(ServerWebExchange exchange, @PathVariable("id") String id) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PostMapping("/api/v1/bar")
    public ResponseEntity<Map<String, String>> api1Mock2(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @PutMapping("/api/v2/bar")
    public ResponseEntity<Map<String, String>> api1Mock3(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @GetMapping("/api/v2/foo")
    public ResponseEntity<Map<String, String>> api1Mock4(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @DeleteMapping("/api/v2/foo")
    public ResponseEntity<Map<String, String>> api1Mock5(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    @GetMapping("/api/v3/foo")
    public ResponseEntity<Map<String, String>> api1Mock6(ServerWebExchange exchange) {
        return ResponseEntity.ok(getResponseMap(exchange));
    }

    /**
     * get map data from exchange request
     * @param exchange http request
     * @return map data
     */
    private Map<String, String> getResponseMap(ServerWebExchange exchange) {
        ServerHttpRequest httpRequest = exchange.getRequest();
        StringBuilder builder = new StringBuilder();
        String requestUri = httpRequest.getPath().value();
        builder.append(requestUri);
        builder.append("--");
        String requestType = httpRequest.getMethod().name();
        builder.append(requestType);
        builder.append("--");
        return Collections.singletonMap("result", String.format(SUCCESS_ACCESS_RESOURCE, builder.toString()));
    }
}
