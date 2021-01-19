package com.usthe.sureness.mgt;

import com.usthe.sureness.DefaultSurenessConfig;
import com.usthe.sureness.processor.exception.IncorrectCredentialsException;
import com.usthe.sureness.provider.ducument.DocumentResourceAccess;
import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.util.JsonWebTokenUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
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

    private static SecurityManager securityManager;

    @BeforeAll
    static void setUp() {
        DocumentResourceAccess.setYamlName("sureness-sample.yml");
        new DefaultSurenessConfig(DefaultSurenessConfig.SUPPORT_SERVLET);
        assertDoesNotThrow(SurenessSecurityManager::getInstance);
        securityManager = SurenessSecurityManager.getInstance();
        assertNotNull(securityManager);
    }

    @Test
    void checkInBasicAuth() {

        HttpServletRequest request = createNiceMock(HttpServletRequest.class);

        expect(request.getHeader(AUTHORIZATION)).andStubReturn(BASIC + " "
                + new String(Base64.getEncoder().encode("admin:admin".getBytes(StandardCharsets.UTF_8))));
        expect(request.getRequestURI()).andStubReturn("/api/v1/book");
        expect(request.getMethod()).andStubReturn("put");
        expect(request.getRemoteHost()).andStubReturn("192.167.2.1");
        replay(request);
        AtomicReference<SubjectSum> subjectSum = new AtomicReference<>();
        assertDoesNotThrow(() -> subjectSum.set(securityManager.checkIn(request)));
        assertNotNull(subjectSum.get());
        assertEquals("admin", subjectSum.get().getPrincipal());
        assertTrue(subjectSum.get().hasAllRoles(Arrays.asList("role1","role2")));
        assertEquals("/api/v1/book===put", subjectSum.get().getTargetResource());
        verify(request);

        reset(request);

        expect(request.getHeader(AUTHORIZATION)).andStubReturn(BASIC + " "
                + new String(Base64.getEncoder().encode("admin:1234".getBytes(StandardCharsets.UTF_8))));
        expect(request.getRequestURI()).andStubReturn("/api/v1/book");
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
        expect(request.getRequestURI()).andStubReturn("/api/v2/book");
        expect(request.getMethod()).andStubReturn("get");
        expect(request.getRemoteHost()).andStubReturn("192.167.2.1");
        replay(request);
        AtomicReference<SubjectSum> subjectSum = new AtomicReference<>();
        assertDoesNotThrow(() -> subjectSum.set(securityManager.checkIn(request)));
        assertNotNull(subjectSum.get());
        assertEquals("tom", subjectSum.get().getPrincipal());
        assertTrue(subjectSum.get().hasAllRoles(Arrays.asList("role2","role3")));
        assertEquals("/api/v2/book===get", subjectSum.get().getTargetResource());
        verify(request);
    }
}