package org.dromara.sureness.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.dromara.sureness.subject.SubjectSum;
import org.dromara.sureness.subject.support.SurenessSubjectSum;
import org.dromara.sureness.util.SurenessContextHolder;

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