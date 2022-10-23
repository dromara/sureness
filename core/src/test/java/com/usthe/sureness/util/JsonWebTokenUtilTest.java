package com.usthe.sureness.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author tomsun28
 * @date 16:20 2020-03-08
 */
public class JsonWebTokenUtilTest {

    private static final String DEFAULT_SECRET_KEY =
        "MIIEowIBAl+f/dKhaX0csgOCTlCxq20yhmUea6H6JIpST3ST1SE2Rwp" +
            "LnfKefTjsIfJLBa2YkhEqE/GtcHDTNe4CU6+9y/S5z50Kik70LsP43r" +
            "RnLN7XNn4wARoQXizIv6MHUsIV+EFfiMw/x7R0ntu4aWr/CWuApcFaj" +
            "4mWEa6EwrPHTZmbT5Mt45AM2UYhzDHK+0F0rUq3MwH+oXsm+L3F/zjj" +
            "M6EByXIO+SV5+8tVt4bisXQ13rbN0oxhUZR73+LDj9mxa6rFhMW+lfx" +
            "CyaFv0bwq2Eik0jdrKUtsA6bx3sDJeFV643R+YYzGMRIqcBIp6AKA98" +
            "GM2RIqcBIp6-?::4390fsf4sdl6opf)4ZI:tdQMtcQQ14pkOAQdQ546";

    @BeforeEach
    public void before() {
        JsonWebTokenUtil.setDefaultSecretKey(DEFAULT_SECRET_KEY);
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