package sureness.controller;


import com.usthe.sureness.util.JsonWebTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sureness.pojo.dto.Account;
import sureness.pojo.dto.Message;
import sureness.service.AccountService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author tomsun28
 * @date 00:24 2019-08-01
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    private static final String TOKEN_SPLIT = "--";

    @PostMapping("/token")
    public ResponseEntity<Message> issueJwtToken(@RequestBody @Validated Account account) {
        boolean authenticatedFlag = accountService.authenticateAccount(account);
        if (!authenticatedFlag) {
            Message message = Message.builder()
                    .errorMsg("username or password not incorrect").build();
            if (log.isDebugEnabled()) {
                log.debug("account: {} authenticated fail", account);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }
        List<String> ownRole = accountService.loadAccountRoles(account.getUsername());
        String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), account.getUsername(),
                "tom-auth-server", 3600L, ownRole);
        Map<String, String> responseData = Collections.singletonMap("token", jwt);
        Message message = Message.builder().data(responseData).build();
        if (log.isDebugEnabled()) {
            log.debug("issue token success, account: {} -- token: {}", account, jwt);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PostMapping("/custom/token")
    public ResponseEntity<Message> issueCustomToken(@RequestBody @Validated Account account) {
        boolean authenticatedFlag = accountService.authenticateAccount(account);
        if (!authenticatedFlag) {
            Message message = Message.builder()
                    .errorMsg("username or password not incorrect").build();
            if (log.isDebugEnabled()) {
                log.debug("account: {} authenticated fail", account);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }
        long refreshPeriodTime = 36000L;
        String token = account.getUsername() + TOKEN_SPLIT + System.currentTimeMillis()
                + TOKEN_SPLIT + refreshPeriodTime
                + TOKEN_SPLIT + UUID.randomUUID().toString().replace("-", "");
        TokenStorage.addToken(account.getUsername(), token);
        Map<String, String> responseData = Collections.singletonMap("customToken", token);
        Message message = Message.builder().data(responseData).build();
        if (log.isDebugEnabled()) {
            log.debug("issue token success, account: {} -- token: {}", account, token);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PostMapping("/register")
    public ResponseEntity<Message> accountRegister(@RequestBody @Validated Account account) {
        if (accountService.registerAccount(account)) {
            Map<String, String> responseData = Collections.singletonMap("success", "sign up success, login after");
            Message message = Message.builder().data(responseData).build();
            if (log.isDebugEnabled()) {
                log.debug("account: {}, sign up success", account);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else {
            Message message = Message.builder()
                    .errorMsg("username already exist").build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
    }
}
