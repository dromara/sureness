package com.usthe.sureness.provider.ducument;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomsun28
 * @date 2021/1/19 13:58
 */
class DocumentResourceAccessTest {

    @Test
    void loadConfig() {
        AtomicReference<DocumentResourceEntity> resourceEntity = new AtomicReference<>();
        assertDoesNotThrow(() -> resourceEntity.set(DocumentResourceAccess.loadConfig("sureness-sample.yml")));
        assertNotNull(resourceEntity.get());
        assertNotNull(resourceEntity.get().getAccount());
        assertNotNull(resourceEntity.get().getResourceRole());
        assertNotNull(resourceEntity.get().getExcludedResource());
    }

    @Test
    void setYamlName() {
        DocumentResourceAccess.setYamlName("sureness.yml");
        assertThrows(FileNotFoundException.class, DocumentResourceAccess::loadConfig);
        DocumentResourceAccess.setYamlName("sureness-sample.yml");
        assertDoesNotThrow((ThrowingSupplier<DocumentResourceEntity>) DocumentResourceAccess::loadConfig);
    }
}