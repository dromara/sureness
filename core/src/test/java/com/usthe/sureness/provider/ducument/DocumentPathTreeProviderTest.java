package com.usthe.sureness.provider.ducument;

import com.usthe.sureness.matcher.PathTreeProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomsun28
 * @date 2021/1/19 14:16
 */
class DocumentPathTreeProviderTest {

    @BeforeAll
    static void stepUp() {
        DocumentResourceAccess.setYamlName("sureness-sample.yml");
    }

    @Test
    void providePathData() {
        PathTreeProvider pathTreeProvider = new DocumentPathTreeProvider();
        Set<String> paths = pathTreeProvider.providePathData();
        assertNotNull(paths);
        assertEquals(13, paths.size());
    }

    @Test
    void provideExcludedResource() {
        PathTreeProvider pathTreeProvider = new DocumentPathTreeProvider();
        Set<String> paths = pathTreeProvider.provideExcludedResource();
        assertNotNull(paths);
        assertEquals(8, paths.size());
    }
}