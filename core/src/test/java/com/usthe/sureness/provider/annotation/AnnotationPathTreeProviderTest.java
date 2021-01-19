package com.usthe.sureness.provider.annotation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomsun28
 * @date 2021/1/19 14:25
 */
class AnnotationPathTreeProviderTest {

    AnnotationPathTreeProvider pathTreeProvider;

    @BeforeEach
    void stepUp() {
        pathTreeProvider = new AnnotationPathTreeProvider();
    }

    @Test
    void providePathData() {
        assertDoesNotThrow(() -> pathTreeProvider.providePathData());
    }

    @Test
    void provideExcludedResource() {
        assertDoesNotThrow(() -> pathTreeProvider.provideExcludedResource());
    }

    @Test
    void setScanPackages() {
        pathTreeProvider.setScanPackages(Arrays.asList("com.usthe.sureness.provider.annotation", "com.usthe.tom"));
    }
}