package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.SubjectCreate;
import com.usthe.sureness.util.JsonWebTokenUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;


/**
 * @author tomsun28
 * @date 21:04 2020-03-08
 */
public class JwtSubjectCreatorTest {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";

    private SubjectCreate creator;

    @Before
    public void setUp() {
        creator = new JwtSubjectCreator();
    }


    @Test
    public void canSupportSubject() {
        String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), "tom",
                "token-server", 36000L, Arrays.asList("role2", "rol3"),
                null, Boolean.FALSE, SignatureAlgorithm.HS512);
        HttpServletRequest request = createNiceMock(HttpServletRequest.class);
        expect(request.getHeader(AUTHORIZATION)).andReturn(BEARER + " " + jwt);
        replay(request);
        assertTrue(creator.canSupportSubject(request));
        verify(request);
    }

    @Test
    public void createSubject() {
        String jwt = JsonWebTokenUtil.issueJwt(UUID.randomUUID().toString(), "tom",
                "token-server", 36000L, Arrays.asList("role2", "rol3"),
                null, Boolean.FALSE, SignatureAlgorithm.HS512);
        HttpServletRequest request = createNiceMock(HttpServletRequest.class);
        expect(request.getHeader(AUTHORIZATION)).andReturn(BEARER + " " + jwt);
        expect(request.getRequestURI()).andReturn("/api/v1/book");
        expect(request.getMethod()).andReturn("put");
        expect(request.getRemoteHost()).andReturn("192.167.2.1");
        replay(request);
        assertNotNull(creator.createSubject(request));
        verify(request);
    }
}