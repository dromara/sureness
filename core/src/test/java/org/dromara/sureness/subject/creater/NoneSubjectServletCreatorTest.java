package org.dromara.sureness.subject.creater;

import org.dromara.sureness.subject.SubjectCreate;
import org.dromara.sureness.subject.creater.NoneSubjectServletCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 * @author tomsun28
 * @date 21:05 2020-03-08
 */
public class NoneSubjectServletCreatorTest {

    private SubjectCreate creator;

    @BeforeEach
    public void setUp() {
        creator = new NoneSubjectServletCreator();
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
        expect(request.getServletPath()).andReturn("/api/v1/book");
        expect(request.getMethod()).andReturn("put");
        expect(request.getRemoteHost()).andReturn("192.167.2.1");
        replay(request);
        assertNotNull(creator.createSubject(request));
        verify(request);
    }
}