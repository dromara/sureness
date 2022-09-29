package com.usthe.sureness.mgt.jwt;

import com.usthe.sureness.processor.exception.ExpiredCredentialsException;
import com.usthe.sureness.provider.ducument.DocumentResourceAccess;
import com.usthe.sureness.util.JsonWebTokenUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefaultJwtManagerTest {

    private DefaultJwtManager defaultJwtManager;

    @BeforeEach
    void init() {
        DocumentResourceAccess.setYamlName("sureness-sample.yml");
        defaultJwtManager = DefaultJwtManager.getInstance();
    }

    @AfterEach
    void destroy() {
        DefaultRedis defaultRedis = defaultJwtManager.getDefaultRedis();
        PassiveExpiringJwtMap passiveExpiringJwtMap = defaultJwtManager.getPassiveExpiringJwtMap();
        if (defaultRedis.isRedisServiceOpen()) {
            defaultRedis.clear();
        } else {
            passiveExpiringJwtMap.clear();
        }
    }

    @Test
    void getInstance() {
        assertDoesNotThrow(DefaultJwtManager::getInstance);
    }

    @Test
    void checkJwt() {
        String expiredJwt = "eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eJwtzEsKAjEQRdG91DgBU50PyTKcioOOVmG0tSUVRRD3bgzOLofHe8O5FUhgfLbuyKgtMmsbOetIwWibLTsMiO7gQYE8ch-39dq7iIy-0E0L1SfVH84NkvHebkzAOCmg1_0PbhpQZEtcSU6QeF6EFNR1oX61G4EwYIL95wtgySzn.kIG8wFgrxJMH8ZAJKEJkOIxYEKBnaOaFfQOqnld2LGC-OHKHrRhId61JcGoAtaX3UdCi-RncrJkCQ8ih_RRgCA";
        String jwt = JsonWebTokenUtil.issueJwt("century", 10L);
        assertThrows(ExpiredCredentialsException.class, () -> defaultJwtManager.checkJwt(expiredJwt));
        assertDoesNotThrow(() -> defaultJwtManager.checkJwt(jwt));
    }

    @Test
    public void cacheToken() {
        String jwt = JsonWebTokenUtil.issueJwt("century", 1L);
        assertDoesNotThrow(() -> defaultJwtManager.cacheToken(jwt, 1L));
    }

}
