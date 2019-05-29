package com.usthe.sureness.sample.bootstrap.controller;

import com.usthe.sureness.provider.DocumentResourceDefaultProvider;
import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.Md5Util;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author tomsun28
 * @date 13:11 2019-05-26
 */
@RestController()
public class AccountController {

    private SurenessAccountProvider accountProvider = new DocumentResourceDefaultProvider();

    @PostMapping("/api/v1/account/login")
    public ResponseEntity<Object> login(@RequestParam String appId, @RequestParam String password) {
        if (appId == null || "".equals(appId)) {
            return ResponseEntity.badRequest().build();
        }
        SurenessAccount account = accountProvider.loadAccount(appId);
        if (account == null || account.isDisabledAccount() || account.isExcessiveAttempts()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (account.getPassword() != null) {
            if (account.getSalt() != null) {
                password = Md5Util.md5(password + account.getSalt());
            }
            if (!account.getPassword().equals(password)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        // issue jwt
        // 获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
        List<String> roles = account.getOwnRoles();
        long refreshPeriodTime = 36000L;
        String jwt = JsonWebTokenUtil.issueJWT(UUID.randomUUID().toString(), appId,
                "token-server", refreshPeriodTime >> 1, roles,
                null, Boolean.FALSE, SignatureAlgorithm.HS512);
        Map<String, String> body = new HashMap<>(1);
        body.put("token", jwt);
        return ResponseEntity.ok().body(body);
    }


}
