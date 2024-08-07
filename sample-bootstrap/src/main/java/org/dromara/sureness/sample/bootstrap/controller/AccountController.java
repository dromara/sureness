package org.dromara.sureness.sample.bootstrap.controller;

import org.dromara.sureness.provider.SurenessAccount;
import org.dromara.sureness.provider.SurenessAccountProvider;
import org.dromara.sureness.provider.ducument.DocumentAccountProvider;
import org.dromara.sureness.util.JsonWebTokenUtil;
import org.dromara.sureness.util.Md5Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * user auth controller
 * @author tomsun28
 * @date 13:11 2019-05-26
 */
@RestController()
public class AccountController {

    private static final String APP_ID = "appId";
    /**
     * account data provider
     */
    private SurenessAccountProvider accountProvider = new DocumentAccountProvider();

    /**
     * login, this provider a get jwt api, convenient to test other api with jwt
     * @param requestBody request
     * @return response
     *
     */
    @PostMapping("/api/v1/account/auth")
    public ResponseEntity<Object> authGetToken(@RequestBody Map<String,String> requestBody) {
        if (requestBody == null || !requestBody.containsKey(APP_ID)
                || !requestBody.containsKey("password")) {
            return ResponseEntity.badRequest().build();
        }
        String appId = requestBody.get("appId");
        String password = requestBody.get("password");
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
        // Get the roles the user has - rbac
        List<String> roles = account.getOwnRoles();
        long periodTime = 3600L;
        // issue jwt
        String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), appId,
                "token-server", periodTime, roles);
        Map<String, String> body = Collections.singletonMap("token", jwt);
        return ResponseEntity.ok().body(body);
    }


}
