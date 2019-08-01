package com.usthe.sureness.sample.tom.controller;

import com.usthe.sureness.sample.tom.pojo.dto.Account;
import com.usthe.sureness.sample.tom.pojo.dto.Message;
import com.usthe.sureness.sample.tom.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tomsun28
 * @date 00:24 2019-08-01
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/token")
    public ResponseEntity<Message> issueToken(@RequestBody @Validated Account account) {
        boolean authenticatedFlag = accountService.authenticateAccount(account);
        if (!authenticatedFlag) {
            Message message = Message.builder().errorType("authenticated fail")
                    .errorMsg("username or password not incorrect").build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }
        String ownRoleString = accountService.loadAccountRoles(account.getUsername());
        long refreshPeriodTime = 36000L;



        return ResponseEntity.ok().build();
    }


}
