package com.usthe.sureness.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomsun28
 * @date 2021/4/2 21:38
 */
class XssSqlUtilTest {

    @Test
    void stripXss() {
        String xssTmp = "cc<script>is a book</script>BB";
        assertEquals("ccBB", XssSqlUtil.stripXss(xssTmp));
    }

    @Test
    void stripSqlInjection() {
        String jwtTmp = "good is girl--boy";
        assertEquals(jwtTmp, XssSqlUtil.stripSqlInjection(jwtTmp));
        String sqlInject = "select * from tableDb where d = 'sds' -- update";
        assertNotEquals(sqlInject, XssSqlUtil.stripSqlInjection(sqlInject));
        assertEquals("select * from tableDb where d =  update", XssSqlUtil.stripSqlInjection(sqlInject));
    }
}