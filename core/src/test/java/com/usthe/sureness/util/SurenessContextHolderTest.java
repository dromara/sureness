package com.usthe.sureness.util;

import com.usthe.sureness.subject.SubjectSum;
import com.usthe.sureness.subject.support.SurenessSubjectSum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomsun28
 * @date 2021/1/17 22:37
 */
class SurenessContextHolderTest {

    @Test
    void clear() {
        bindSubject();
        assertDoesNotThrow(SurenessContextHolder::clear);
        assertNull(SurenessContextHolder.getBindSubject());
    }

    @Test
    void bindSubject() {
        SubjectSum subjectSum = SurenessSubjectSum.builder().build();
        assertDoesNotThrow(() -> SurenessContextHolder.bindSubject(subjectSum));
        assertEquals(subjectSum, SurenessContextHolder.getBindSubject());
    }

    @Test
    void unbindSubject() {
        bindSubject();
        assertDoesNotThrow(SurenessContextHolder::unbindSubject);
        assertNull(SurenessContextHolder.getBindSubject());
    }
}