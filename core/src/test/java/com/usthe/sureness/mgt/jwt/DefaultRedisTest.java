package com.usthe.sureness.mgt.jwt;

import com.usthe.sureness.provider.ducument.DocumentResourceAccess;
import com.usthe.sureness.provider.ducument.DocumentResourceEntity;
import com.usthe.sureness.util.JsonWebTokenUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultRedisTest {

    // Note: Before testing, please manually set "enabled" to "true" in the configuration file
    private DefaultRedis defaultRedis;

    private String testJwt;

    private String fakeJwt;

    private DocumentResourceEntity documentResourceEntity;

    @BeforeEach
    public void init() throws IOException {
        DocumentResourceAccess.setYamlName("sureness-sample.yml");
        documentResourceEntity = DocumentResourceAccess.loadConfig();
        DefaultJwtManager defaultJwtManager = DefaultJwtManager.getInstance();
        defaultRedis = defaultJwtManager.getDefaultRedis();
        testJwt = JsonWebTokenUtil.issueJwt("century", 10L);
        fakeJwt = "this is an invalid jwt";
    }

    @AfterEach
    public void destroy() {
        if (defaultRedis.isRedisServiceOpen()) {
            defaultRedis.clear();
        }
    }

    @Test
    public void isConnected() {
        if (defaultRedis.isRedisServiceOpen()) {
            assertTrue(defaultRedis.isConnected());
        }
    }

    @Test
    public void loadRedis() {
        if (defaultRedis.isRedisServiceOpen()) {
            Assertions.assertDoesNotThrow(() -> defaultRedis.loadRedis(documentResourceEntity));
        }
    }

    @Test
    public void setJwt() {
        if (defaultRedis.isRedisServiceOpen()) {
            String setRes1 = defaultRedis.setJwt(testJwt);
            assertEquals("OK", setRes1);
            String setRes2 = defaultRedis.setJwt(fakeJwt);
            assertNull(setRes2);
        }
    }

    @Test
    public void getJwtExpiration() {
        if (defaultRedis.isRedisServiceOpen()) {
            String jwtExpiration = defaultRedis.getJwtExpiration(testJwt);
            Assertions.assertNotNull(jwtExpiration);
            Assertions.assertNull(defaultRedis.getJwtExpiration(fakeJwt));
        }
    }

    @Test
    public void deleteJwt() {
        if (defaultRedis.isRedisServiceOpen()) {
            defaultRedis.setJwt(testJwt);
            Assertions.assertEquals(1, defaultRedis.deleteJwt(testJwt));
            Assertions.assertEquals(0, defaultRedis.deleteJwt(fakeJwt));
        }
    }

    @Test
    public void jwtExists() {
        if (defaultRedis.isRedisServiceOpen()) {
            defaultRedis.setJwt(testJwt);
            Assertions.assertEquals(1, defaultRedis.jwtExists(testJwt));
            Assertions.assertEquals(0, defaultRedis.jwtExists(fakeJwt));
        }
    }

    @Test
    public void setJwtExpiration() {
        if (defaultRedis.isRedisServiceOpen()) {
            defaultRedis.setJwt(testJwt);
            Boolean flag1 = defaultRedis.setJwtExpiration(testJwt, 5L);
            assertTrue(flag1);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Boolean flag2 = defaultRedis.setJwtExpiration(fakeJwt, 5L);
            assertFalse(flag2);
        }
    }

    @Test
    public void clear() {
        if (defaultRedis.isRedisServiceOpen()) {
            assertEquals("OK", defaultRedis.clear());
        }
    }

}
