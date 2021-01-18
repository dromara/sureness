package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.SubjectCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomsun28
 * @date 2021/1/18 16:17
 */
class DigestSubjectServletCreatorTest {

    private SubjectCreate creator;
    private static final String AUTHORIZATION = "Authorization";

    @BeforeEach
    void setUp() {
        creator = new DigestSubjectServletCreator();
    }

    @Test
    void canSupportSubject() {
        HttpServletRequest request = createNiceMock(HttpServletRequest.class);
        replay(request);
        assertTrue(creator.canSupportSubject(request));
        verify(request);
    }

    @Test
    void createSubject() {
        HttpServletRequest request = createNiceMock(HttpServletRequest.class);
        replay(request);
        assertNotNull(creator.createSubject(request));
        verify(request);

        reset(request);

        expect(request.getHeader(AUTHORIZATION)).andReturn("Digest username=\"tom\", realm=\"sureness_realm\", " +
                "nonce=\"c3403e810156a6131c4333eaa27f0797\", uri=\"/api/v1/source1\", response=\"86c3684d94ebc9786e6e7b6cbb288cfe\", qop=auth, nc=00000002, cnonce=\"4ee455aebd085f01\"");
        expect(request.getRequestURI()).andReturn("/api/v1/book");
        expect(request.getMethod()).andReturn("put");
        expect(request.getRemoteHost()).andReturn("192.167.2.1");
        replay(request);
        assertNotNull(creator.createSubject(request));
        verify(request);
    }
}