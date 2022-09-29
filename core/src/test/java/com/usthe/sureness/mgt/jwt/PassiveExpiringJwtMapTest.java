package com.usthe.sureness.mgt.jwt;

import com.usthe.sureness.provider.ducument.DocumentResourceAccess;
import com.usthe.sureness.util.JsonWebTokenUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PassiveExpiringJwtMapTest {

    // Note: Before testing, please manually set "enabled" to "false" or do not configure redis in the configuration file
    private PassiveExpiringJwtMap jwtMap;

    private String testJwt;

    private String fakeJwt;

    @BeforeEach
    public void init() {
        DocumentResourceAccess.setYamlName("sureness-sample.yml");
        jwtMap =  DefaultJwtManager.getInstance().getPassiveExpiringJwtMap();
        testJwt = JsonWebTokenUtil.issueJwt("century", 100L);
        fakeJwt = "this is an invalid jwt";
    }

    @AfterEach
    public void destroy() {
        jwtMap.clear();
    }

    // Note: when you test this method, please set ttl in your configuration file
    @Test
    public void isTTLSetUp() {
        // clean up jwtMap to remove the effect of constructor
        jwtMap.clear();
        boolean flag1 = jwtMap.isTTLSetup();
        assertFalse(flag1);
        jwtMap.loadTimeToLive(1);
        boolean flag2 = jwtMap.isTTLSetup();
        assertTrue(flag2);
    }

    // Note: when you test this method, please set ttl in your configuration file
    @Test
    public void loadTimeToLive() {
        assertDoesNotThrow(() -> jwtMap.loadTimeToLive(1));
        long ttl = jwtMap.loadTimeToLive(1);
        assertEquals(1, ttl);
    }

    @Test
    public void put() {
        Long flag1 = jwtMap.put(testJwt, 60L);
        // The reason flag1 is not empty is that JWT was already added to map when it was issued
        assertNotNull(flag1);
        Long flag2 = jwtMap.put(fakeJwt, 60L);
        assertNull(flag2);
        jwtMap.loadTimeToLive(3);
    }

    @Test
    public void get() {
        jwtMap.put(testJwt, 60L);
        Long flag1 = jwtMap.get(testJwt);
        assertNotNull(flag1);
        Long flag2 = jwtMap.get(fakeJwt);
        assertNull(flag2);
        String jwt = "eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eJwtzEsKAjEQRdG91DgBU50PyTKcioOOVmG0tSUVRRD3bgzOLofHe8O5FUhgfLbuyKgtMmsbOetIwWibLTsMiO7gQYE8ch-39dq7iIy-0E0L1SfVH84NkvHebkzAOCmg1_0PbhpQZEtcSU6QeF6EFNR1oX61G4EwYIL95wtgySzn.kIG8wFgrxJMH8ZAJKEJkOIxYEKBnaOaFfQOqnld2LGC-OHKHrRhId61JcGoAtaX3UdCi-RncrJkCQ8ih_RRgCA";
        jwtMap.loadTimeToLive(3);
        jwtMap.put(jwt);
        Long flag3 = jwtMap.get(jwt);
        assertNotNull(flag3);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Long flag4 = jwtMap.get(jwt);
        assertNull(flag4);
    }

    @Test
    public void containsKey() {
        jwtMap.put(testJwt, 3L);
        boolean flag1 = jwtMap.containsKey(testJwt);
        assertTrue(flag1);
        boolean flag2 = jwtMap.containsKey(fakeJwt);
        assertFalse(flag2);
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // after 3 seconds, valid jwt expired
        boolean flag3 = jwtMap.containsKey(testJwt);
        assertFalse(flag3);
    }

    @Test
    public void size() {
        // jwtMap already set jwt when initiate
        assertEquals(1, jwtMap.size());
        JsonWebTokenUtil.issueJwt("test", 1L);
        assertEquals(2, jwtMap.size());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, jwtMap.size());
    }

    @Test
    public void clear() {
        jwtMap.loadTimeToLive(1);
        assertTrue(jwtMap.isTTLSetup());
        assertEquals(1, jwtMap.size());
        assertNotEquals(0, jwtMap.getTimeToLive());
        jwtMap.clear();
        assertFalse(jwtMap.isTTLSetup());
        assertEquals(0, jwtMap.size());
        assertEquals(0, jwtMap.getTimeToLive());
    }

}
