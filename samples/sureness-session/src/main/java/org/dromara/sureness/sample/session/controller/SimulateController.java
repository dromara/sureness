package org.dromara.sureness.sample.session.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

/**
 * simulate api controller, for testing
 * @author tomsun28
 * @date 17:35 2019-05-12
 */
@RestController
public class SimulateController {

    /** access success message **/
    public static final String SUCCESS_ACCESS_RESOURCE = "access this resource: %s success";

    @GetMapping("/api/v1/bar/{id}")
    public ResponseEntity<Map<String, String>> api1Mock1(HttpServletRequest request, @PathVariable String id) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PostMapping("/api/v1/bar")
    public ResponseEntity<Map<String, String>> api1Mock2(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PutMapping("/api/v2/bar")
    public ResponseEntity<Map<String, String>> api1Mock3(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @GetMapping("/api/v2/foo")
    public ResponseEntity<Map<String, String>> api1Mock4(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @DeleteMapping("/api/v2/foo")
    public ResponseEntity<Map<String, String>> api1Mock5(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @GetMapping("/api/v3/foo")
    public ResponseEntity<Map<String, String>> api1Mock6(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    /**
     * get the response map data from request
     * @param request http request
     * @return map data
     */
    private Map<String, String> getResponseMap(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        String requestUri = request.getRequestURI();
        builder.append(requestUri);
        builder.append("--");
        String requestType = request.getMethod();
        builder.append(requestType);
        builder.append("--");
        return Collections.singletonMap("result", String.format(SUCCESS_ACCESS_RESOURCE, builder.toString()));
    }
}
