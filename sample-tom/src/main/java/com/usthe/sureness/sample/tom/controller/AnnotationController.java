package com.usthe.sureness.sample.tom.controller;

import com.usthe.sureness.provider.annotation.RequiresRoles;
import com.usthe.sureness.provider.annotation.WithoutAuth;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author tomsun28
 * @date 2021/1/19 14:53
 */
@RestController
public class AnnotationController {

    @GetMapping("/api/annotation/source1")
    @RequiresRoles(roles = {"role1", "role2", "role3"}, mapping = "/api/annotation/source1", method = "get")
    public ResponseEntity<String> api1Mock1() {
        return ResponseEntity.ok("success");
    }

    @GetMapping("/api/annotation/source2")
    @WithoutAuth(mapping = "/api/annotation/source2", method = "get")
    public ResponseEntity<String> api1Mock2() {
        return ResponseEntity.ok("success");
    }

}
