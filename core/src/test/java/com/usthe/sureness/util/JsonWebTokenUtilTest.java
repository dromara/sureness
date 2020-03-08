package com.usthe.sureness.util;

import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.UUID;


/**
 * @author tomsun28
 * @date 16:20 2020-03-08
 */
public class JsonWebTokenUtilTest {

    @Test
    public void issueJwt() {
        String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), "tom",
                "token-server", 36000L, Arrays.asList("role2", "rol3"),
                null, Boolean.FALSE, SignatureAlgorithm.HS512);
        assertNotNull(jwt);
    }

    @Test
    public void isNotJsonWebToken() {
        String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), "tom",
                "token-server", 36000L, Arrays.asList("role2", "rol3"),
                null, Boolean.FALSE, SignatureAlgorithm.HS512);
        boolean flag = JsonWebTokenUtil.isNotJsonWebToken(jwt);
        assertFalse(flag);
        flag = JsonWebTokenUtil.isNotJsonWebToken("gsgdsghdbhegxhsgdjsdj");
        assertTrue(flag);
    }

    @Test
    public void parseJwt() {
        String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), "tom",
                "token-server", 36000L, Arrays.asList("role2", "rol3"),
                null, Boolean.FALSE, SignatureAlgorithm.HS512);
        assertNotNull(JsonWebTokenUtil.parseJwt(jwt));
    }
}