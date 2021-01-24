package com.usthe.sureness.subject;

import com.usthe.sureness.subject.creater.BasicSubjectServletCreator;
import com.usthe.sureness.subject.creater.DigestSubjectServletCreator;
import com.usthe.sureness.subject.creater.JwtSubjectServletCreator;
import com.usthe.sureness.subject.creater.NoneSubjectServletCreator;
import com.usthe.sureness.subject.support.NoneSubject;
import com.usthe.sureness.subject.support.PasswordSubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomsun28
 * @date 2021/1/17 22:51
 */
class SurenessSubjectFactoryTest {

    private SurenessSubjectFactory subjectFactory;

    private List<SubjectCreate> subjectCreates;

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic";

    @BeforeEach
    public void setUp() {
        subjectFactory = new SurenessSubjectFactory();
        subjectCreates = Arrays.asList(
                new NoneSubjectServletCreator(),
                new DigestSubjectServletCreator(),
                new BasicSubjectServletCreator(),
                new JwtSubjectServletCreator());
    }

    @Test
    void createSubjects() {
        registerSubjectCreator();
        HttpServletRequest request = createNiceMock(HttpServletRequest.class);
        expect(request.getHeader(AUTHORIZATION)).andStubReturn(BASIC + " "
                + new String(Base64.getEncoder().encode("admin:admin".getBytes(StandardCharsets.UTF_8))));
        expect(request.getRequestURI()).andStubReturn("/api/v1/book");
        expect(request.getMethod()).andStubReturn("put");
        expect(request.getRemoteHost()).andStubReturn("192.167.2.1");
        replay(request);
        List<Subject> subjects = subjectFactory.createSubjects(request);
        assertNotNull(subjects);
        assertTrue(subjects.get(0) instanceof NoneSubject);
        assertTrue(subjects.get(1) instanceof PasswordSubject);
        assertEquals(2, subjects.size());
        verify(request);
    }

    @Test
    void registerSubjectCreator() {
        subjectFactory.registerSubjectCreator(subjectCreates);
        assertNotNull(subjectFactory.loadSubjectCreators());
        assertEquals(4, subjectFactory.loadSubjectCreators().size());
    }

    @Test
    void loadSubjectCreators() {
        registerSubjectCreator();
        assertNotNull(subjectFactory.loadSubjectCreators());
        assertEquals(4, subjectFactory.loadSubjectCreators().size());
        assertEquals(subjectCreates, subjectFactory.loadSubjectCreators());
    }
}