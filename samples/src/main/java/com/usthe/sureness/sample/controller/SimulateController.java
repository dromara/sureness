package com.usthe.sureness.sample.controller;

import com.usthe.sureness.sample.util.CommonUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 模拟资源
 * @author tomsun28
 * @date 17:35 2019-05-12
 */
@RestController
public class SimulateController {

    @GetMapping("/api/v1/getSource1")
    public ResponseEntity<String> getSource1(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String requestType = request.getMethod();
        return ResponseEntity.ok(String.format(CommonUtil.SUCCESS_ACCESS_RESOURCE, requestUri + requestType));
    }

    @GetMapping("/api/v2/getSource2/{var1}/{var2}")
    public ResponseEntity<String> getSource2(HttpServletRequest request, @PathVariable String var1, @PathVariable Integer var2 ) {
        StringBuilder builder = new StringBuilder();
        String requestUri = request.getRequestURI();
        builder.append(requestUri);
        builder.append("--");
        String requestType = request.getMethod();
        builder.append(requestType);
        builder.append("--");
        if (!StringUtils.isEmpty(var1)) {
            builder.append(var1);
            builder.append("--");
        }
        if (var2 != null && !StringUtils.isEmpty(var2)) {
            builder.append(var2);
            builder.append("--");
        }
        return ResponseEntity.ok(String.format(CommonUtil.SUCCESS_ACCESS_RESOURCE, builder.toString()));
    }

    // TODO 模拟更多资源
}
