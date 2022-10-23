package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.SubjectCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.easymock.EasyMock.*;


/**
 * @author tomsun28
 * @date 21:04 2020-03-08
 */
public class BasicSubjectServletCreatorTest {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic";

    private SubjectCreate creator;

    @BeforeEach
    public void setUp() {
        creator = new BasicSubjectServletCreator();
    }

    @Test
    public void canSupportSubject() {
        HttpServletRequest request = createNiceMock(HttpServletRequest.class);
        expect(request.getHeader(AUTHORIZATION)).andReturn(BASIC + "12345");
        replay(request);
        assertTrue(creator.canSupportSubject(request));
        verify(request);

        reset(request);

        expect(request.getHeader(AUTHORIZATION)).andReturn("12345");
        replay(request);
        assertFalse(creator.canSupportSubject(request));
        verify(request);
    }

    @Test
    public void createSubject() {
        HttpServletRequest request = createNiceMock(HttpServletRequest.class);
        expect(request.getHeader(AUTHORIZATION)).andReturn(BASIC + " "
                + new String(Base64.getEncoder().encode("admin:admin".getBytes(StandardCharsets.UTF_8))));
        expect(request.getServletPath()).andReturn("/api/v1/book");
        expect(request.getMethod()).andReturn("put");
        expect(request.getRemoteHost()).andReturn("192.167.2.1");
        replay(request);
        assertNotNull(creator.createSubject(request));
        verify(request);
    }
}