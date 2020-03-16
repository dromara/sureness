package com.usthe.sureness.sample.bootstrap.controller;

import com.usthe.sureness.provider.ducument.DocumentResourceDefaultProvider;
import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.Md5Util;
import io.jsonwebtoken.SignatureAlgorithm;
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
 * 用户登录认证controller
 * @author tomsun28
 * @date 13:11 2019-05-26
 */
@RestController()
public class AccountController {

    private static final String APP_ID = "appId";
    /**
     * 账户数据提供
     */
    private SurenessAccountProvider accountProvider = new DocumentResourceDefaultProvider();

    /**
     * 登录，此提供一个用户登录获取jwt接口.方便用jwt测试其他接口
     * @param requestBody 请求体
     * @return 响应信息
     *
     */
    @PostMapping("/api/v1/account/auth")
    public ResponseEntity<Object> login(@RequestBody Map<String,String> requestBody) {
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
        // issue jwt
        // 获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
        List<String> roles = account.getOwnRoles();
        long refreshPeriodTime = 36000L;
        String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), appId,
                "token-server", refreshPeriodTime >> 1, roles,
                null, Boolean.FALSE, SignatureAlgorithm.HS512);
        Map<String, String> body = Collections.singletonMap("token", jwt);
        return ResponseEntity.ok().body(body);
    }


}
