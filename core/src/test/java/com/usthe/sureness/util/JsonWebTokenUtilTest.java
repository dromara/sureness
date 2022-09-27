package com.usthe.sureness.util;

import com.usthe.sureness.DefaultSurenessConfig;
import com.usthe.sureness.provider.ducument.DocumentResourceAccess;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author tomsun28
 * @date 16:20 2020-03-08
 */
public class JsonWebTokenUtilTest {

    DefaultSurenessConfig defaultSurenessConfig;
    @BeforeEach
    public void init() {
        DocumentResourceAccess.setYamlName("sureness-sample.yml");
        defaultSurenessConfig = new DefaultSurenessConfig();
    }

    @Test
    public void issueJwt() {
        String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), "tom",
                "token-server", 36000L, Arrays.asList("role2", "rol3"),
                null, Boolean.FALSE);
        assertNotNull(jwt);
    }

    @Test
    void issueJwtAll() {
        Map<String, Object> customClaimMap = new HashMap<>(4);
        customClaimMap.put("roles", Arrays.asList("role2", "rol3"));
        customClaimMap.put("perms", null);
        customClaimMap.put("isRefresh", true);
        String jwt = JsonWebTokenUtil.issueJwtAll(UUID.randomUUID().toString(), "tom",
                "token-server", 36000L, null, null, null, customClaimMap, null);
        assertNotNull(jwt);
    }

    @Test
    public void isNotJsonWebToken() {
        String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), "tom",
                "token-server", 36000L, Arrays.asList("role2", "rol3"),
                null, Boolean.FALSE);
        boolean flag = JsonWebTokenUtil.isNotJsonWebToken(jwt);
        assertFalse(flag);
        flag = JsonWebTokenUtil.isNotJsonWebToken("gsgdsghdbhegxhsgdjsdj");
        assertTrue(flag);
    }

    @Test
    public void parseJwt() {
        String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), "tom",
                "token-server", 36000L, Arrays.asList("role2", "rol3"),
                null, Boolean.FALSE);
        Claims claims = JsonWebTokenUtil.parseJwt(jwt);
        assertNotNull(claims);
        assertEquals("tom", claims.getSubject());
        assertEquals("token-server", claims.getIssuer());
        assertNotNull(claims.get("roles", List.class));
        assertNull(claims.get("perms", List.class));
        assertFalse(claims.get("isRefresh", Boolean.class));
        assertEquals(2, claims.get("roles", List.class).size());

        Map<String, Object> customClaimMap = new HashMap<>(4);
        customClaimMap.put("roles", Arrays.asList("role2", "rol3", "role4"));
        customClaimMap.put("perms", null);
        customClaimMap.put("isRefresh", true);
        String jwt2 = JsonWebTokenUtil.issueJwtAll(UUID.randomUUID().toString(), "tom",
                "token-server", 36000L, null, null, null, null, customClaimMap);
        Claims claims2 = JsonWebTokenUtil.parseJwt(jwt2);
        assertNotNull(claims2);
        assertEquals("tom", claims2.getSubject());
        assertEquals("token-server", claims2.getIssuer());
        assertNotNull(claims2.get("roles", List.class));
        assertNull(claims2.get("perms", List.class));
        assertTrue(claims2.get("isRefresh", Boolean.class));
        assertEquals(3, claims2.get("roles", List.class).size());
    }
}