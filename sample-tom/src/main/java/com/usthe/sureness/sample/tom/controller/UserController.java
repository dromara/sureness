package com.usthe.sureness.sample.tom.controller;

import com.usthe.sureness.sample.tom.pojo.dto.Message;
import com.usthe.sureness.sample.tom.service.AccountService;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.util.SurenessContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author tomsun28
 * @date 21:05 2018/3/17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AccountService accountService;


    @GetMapping("/role")
    public ResponseEntity<Message> getUserRoles() {
        SubjectSum subject = SurenessContextHolder.getBindSubject();
        if (subject == null || subject.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String appId = (String) subject.getPrincipal();
        List<String> roles = accountService.loadAccountRoles(appId);
        return ResponseEntity.ok(Message.builder().data(roles).build());
    }

    @PostMapping("/authority/role/{appId}/{roleId}")
    public ResponseEntity<Message> authorityUserRole(@PathVariable String appId, @PathVariable Long roleId) {
        SubjectSum subject = SurenessContextHolder.getBindSubject();
        if (subject == null || subject.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String principal = (String) subject.getPrincipal();
        if (!principal.equals(appId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        boolean flag = accountService.authorityUserRole(appId, roleId);
        return flag ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/authority/role/{appId}/{roleId}")
    public ResponseEntity<Message> deleteAuthorityUserRole(@PathVariable String appId, @PathVariable Long roleId) {
        SubjectSum subject = SurenessContextHolder.getBindSubject();
        if (subject == null || subject.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String principal = (String) subject.getPrincipal();
        if (!principal.equals(appId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return accountService.deleteAuthorityUserRole(appId, roleId) ?
                ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
