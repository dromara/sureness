package org.dromara.sureness.mgt;

import org.dromara.sureness.SurenessDefaultConfig;
import org.dromara.sureness.mgt.SecurityManager;
import org.dromara.sureness.mgt.SurenessSecurityManager;
import org.dromara.sureness.processor.exception.IncorrectCredentialsException;
import org.dromara.sureness.provider.ducument.DocumentResourceAccess;
import org.dromara.sureness.subject.SubjectSum;
import org.dromara.sureness.util.JsonWebTokenUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomsun28
 * @date 2021/1/19 21:15
 */
class SurenessSecurityManagerTest {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic";
    private static final String BEARER = "Bearer";
    private static final String DEFAULT_SECRET_KEY =
        "MIIEowIBAl+f/dKhaX0csgOCTlCxq20yhmUea6H6JIpST3ST1SE2Rwp" +
            "LnfKefTjsIfJLBa2YkhEqE/GtcHDTNe4CU6+9y/S5z50Kik70LsP43r" +
            "RnLN7XNn4wARoQXizIv6MHUsIV+EFfiMw/x7R0ntu4aWr/CWuApcFaj" +
            "4mWEa6EwrPHTZmbT5Mt45AM2UYhzDHK+0F0rUq3MwH+oXsm+L3F/zjj" +
            "M6EByXIO+SV5+8tVt4bisXQ13rbN0oxhUZR73+LDj9mxa6rFhMW+lfx" +
            "CyaFv0bwq2Eik0jdrKUtsA6bx3sDJeFV643R+YYzGMRIqcBIp6AKA98" +
            "GM2RIqcBIp6-?::4390fsf4sdl6opf)4ZI:tdQMtcQQ14pkOAQdQ546";

    private static SecurityManager securityManager;

    @BeforeAll
    static void setUp() {
        DocumentResourceAccess.setYamlName("sureness-sample.yml");
        new SurenessDefaultConfig(SurenessDefaultConfig.SUPPORT_JAKARTA_SERVLET);
        assertDoesNotThrow(SurenessSecurityManager::getInstance);
        securityManager = SurenessSecurityManager.getInstance();
        assertNotNull(securityManager);
        JsonWebTokenUtil.setDefaultSecretKey(DEFAULT_SECRET_KEY);
    }

    @Test
    void checkInBasicAuth() {

        HttpServletRequest request = createNiceMock(HttpServletRequest.class);

        expect(request.getHeader(AUTHORIZATION)).andStubReturn(BASIC + " "
                + new String(Base64.getEncoder().encode("admin:admin".getBytes(StandardCharsets.UTF_8))));
        expect(request.getServletPath()).andStubReturn("/api/v2/host");
        expect(request.getMethod()).andStubReturn("put");
        expect(request.getRemoteHost()).andStubReturn("192.167.2.1");
        replay(request);
        AtomicReference<SubjectSum> subjectSum = new AtomicReference<>();
        assertDoesNotThrow(() -> subjectSum.set(securityManager.checkIn(request)));
        assertNotNull(subjectSum.get());
        assertEquals("admin", subjectSum.get().getPrincipal());
        assertTrue(subjectSum.get().hasAllRoles(Arrays.asList("role1","role2")));
        assertEquals("/api/v2/host===put", subjectSum.get().getTargetResource());
        verify(request);

        reset(request);

        expect(request.getHeader(AUTHORIZATION)).andStubReturn(BASIC + " "
                + new String(Base64.getEncoder().encode("admin:1234".getBytes(StandardCharsets.UTF_8))));
        expect(request.getServletPath()).andStubReturn("/api/v1/book");
        expect(request.getMethod()).andStubReturn("put");
        expect(request.getRemoteHost()).andStubReturn("192.167.2.1");
        replay(request);
        assertThrows(IncorrectCredentialsException.class, () -> securityManager.checkIn(request));
    }

    @Test
    void checkInJwtAuth() {
        String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), "tom",
                "token-server", 36000L, Arrays.asList("role2", "role3"),
                null, Boolean.FALSE);
        HttpServletRequest request = createNiceMock(HttpServletRequest.class);
        expect(request.getHeader(AUTHORIZATION)).andStubReturn(BEARER + " " + jwt);
        expect(request.getServletPath()).andStubReturn("/api/v1/source1");
        expect(request.getMethod()).andStubReturn("get");
        expect(request.getRemoteHost()).andStubReturn("192.167.2.1");
        replay(request);
        AtomicReference<SubjectSum> subjectSum = new AtomicReference<>();
        assertDoesNotThrow(() -> subjectSum.set(securityManager.checkIn(request)));
        assertNotNull(subjectSum.get());
        assertEquals("tom", subjectSum.get().getPrincipal());
        assertTrue(subjectSum.get().hasAllRoles(Arrays.asList("role2","role3")));
        assertEquals("/api/v1/source1===get", subjectSum.get().getTargetResource());
        verify(request);
    }
}