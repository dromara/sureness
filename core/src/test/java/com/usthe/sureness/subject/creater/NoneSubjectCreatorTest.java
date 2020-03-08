package com.usthe.sureness.subject.creater;

import com.usthe.sureness.subject.SubjectCreate;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;


/**
 * @author tomsun28
 * @date 21:05 2020-03-08
 */
public class NoneSubjectCreatorTest {

    private SubjectCreate creator;

    @Before
    public void setUp() {
        creator = new NoneSubjectCreator();
    }


    @Test
    public void canSupportSubject() {
        HttpServletRequest request = createNiceMock(HttpServletRequest.class);
        replay(request);
        assertTrue(creator.canSupportSubject(request));
        verify(request);
    }

    @Test
    public void createSubject() {
        HttpServletRequest request = createNiceMock(HttpServletRequest.class);
        expect(request.getRequestURI()).andReturn("/api/v1/book");
        expect(request.getMethod()).andReturn("put");
        expect(request.getRemoteHost()).andReturn("192.167.2.1");
        replay(request);
        assertNotNull(creator.createSubject(request));
        verify(request);
    }
}