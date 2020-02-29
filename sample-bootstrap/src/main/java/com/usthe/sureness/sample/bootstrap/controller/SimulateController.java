package com.usthe.sureness.sample.bootstrap.controller;

import com.usthe.sureness.sample.bootstrap.util.CommonUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/api/v1/source1")
    public ResponseEntity<Map<String, String>> api1Mock1(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PostMapping("/api/v1/source1")
    public ResponseEntity<Map<String, String>> api1Mock2(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PutMapping("/api/v1/source1")
    public ResponseEntity<Map<String, String>> api1Mock3(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @DeleteMapping("/api/v1/source1")
    public ResponseEntity<Map<String, String>> api1Mock4(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PatchMapping("/api/v1/source1")
    public ResponseEntity<Map<String, String>> api1Mock5(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @GetMapping("/api/v1/source2/{var1}/{var2}")
    public ResponseEntity<Map<String, String>> api1Mock6(HttpServletRequest request, @PathVariable String var1, @PathVariable Integer var2 ) {
        Map<String, String> resultMap = new HashMap<>(3);
        resultMap.putAll(getResponseMap(request));
        if (!StringUtils.isEmpty(var1)) {
            resultMap.put("var1", var1);
        }
        if (Objects.nonNull(var2)) {
            resultMap.put("var2", String.valueOf(var2));
        }
        return ResponseEntity.ok(resultMap);
    }

    @PostMapping("/api/v1/source2/{var1}")
    public ResponseEntity<Map<String, String>> api1Mock7(HttpServletRequest request, @PathVariable String var1) {
        Map<String, String> resultMap = new HashMap<>(2);
        resultMap.putAll(getResponseMap(request));
        if (!StringUtils.isEmpty(var1)) {
            resultMap.put("var1", var1);
        }
        return ResponseEntity.ok(resultMap);
    }

    @DeleteMapping("/api/v1/source2/{var2}")
    public ResponseEntity<Map<String, String>> api1Mock8(HttpServletRequest request, @PathVariable Integer var2 ) {
        Map<String, String> resultMap = new HashMap<>(2);
        resultMap.putAll(getResponseMap(request));
        if (Objects.nonNull(var2)) {
            resultMap.put("var2", String.valueOf(var2));
        }
        return ResponseEntity.ok(resultMap);
    }

    @PutMapping("/api/v1/source2")
    public ResponseEntity<Map<String, String>> api1Mock9(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PatchMapping("/api/v1/source2")
    public ResponseEntity<Map<String, String>> api1Mock10(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @GetMapping("/api/v1/source3")
    public ResponseEntity<Map<String, String>> api1Mock11(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PostMapping("/api/v1/source3")
    public ResponseEntity<Map<String, String>> api1Mock12(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PutMapping("/api/v1/source3")
    public ResponseEntity<Map<String, String>> api1Mock13(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @DeleteMapping("/api/v1/source3")
    public ResponseEntity<Map<String, String>> api1Mock14(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PatchMapping("/api/v1/source3")
    public ResponseEntity<Map<String, String>> api1Mock15(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @GetMapping("/api/v2/source4")
    public ResponseEntity<Map<String, String>> api2Mock16(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PostMapping("/api/v2/source4")
    public ResponseEntity<Map<String, String>> api2Mock17(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PutMapping("/api/v2/source4")
    public ResponseEntity<Map<String, String>> api2Mock18(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @DeleteMapping("/api/v2/source4")
    public ResponseEntity<Map<String, String>> api2Mock19(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PatchMapping("/api/v2/source4")
    public ResponseEntity<Map<String, String>> api2Mock20(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @GetMapping("/api/v2/source5")
    public ResponseEntity<Map<String, String>> api2Mock21(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PostMapping("/api/v2/source5")
    public ResponseEntity<Map<String, String>> api2Mock22(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @DeleteMapping("/api/v2/source5")
    public ResponseEntity<Map<String, String>> api2Mock23(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PutMapping("/api/v2/source5")
    public ResponseEntity<Map<String, String>> api2Mock24(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PatchMapping("/api/v2/source5")
    public ResponseEntity<Map<String, String>> api2Mock25(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @GetMapping("/api/v2/source6")
    public ResponseEntity<Map<String, String>> api2Mock26(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    @PostMapping("/api/v2/source6")
    public ResponseEntity<Map<String, String>> api2Mock27(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }
    @DeleteMapping("/api/v2/source6")
    public ResponseEntity<Map<String, String>> api2Mock28(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }
    @PutMapping("/api/v2/source6")
    public ResponseEntity<Map<String, String>> api2Mock29(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }
    @PatchMapping("/api/v2/source6")
    public ResponseEntity<Map<String, String>> api2Mock30(HttpServletRequest request) {
        return ResponseEntity.ok(getResponseMap(request));
    }

    /**
     * 获取MOCK固定的返回数据MAP
     * @param request http 请求
     * @return 返回数据
     */
    private Map<String, String> getResponseMap(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        String requestUri = request.getRequestURI();
        builder.append(requestUri);
        builder.append("--");
        String requestType = request.getMethod();
        builder.append(requestType);
        builder.append("--");
        return Collections.singletonMap("result", String.format(CommonUtil.SUCCESS_ACCESS_RESOURCE, builder.toString()));
    }
}
