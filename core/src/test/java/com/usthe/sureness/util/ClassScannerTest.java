package com.usthe.sureness.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomsun28
 * @date 2021/1/17 22:09
 */
class ClassScannerTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "com.usthe.sureness.subject",
            "com.usthe.sureness.mgt",
            "com.usthe.sureness.util"})
    void scanPackages(String packageName) {
        List<Class<?>> scanClasses = ClassScanner.scanPackages(packageName);
        assertNotNull(scanClasses);
    }
}