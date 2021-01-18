package com.usthe.sureness;


import com.usthe.sureness.provider.ducument.DocumentResourceAccess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomsun28
 * @date 2021/1/17 11:02
 */
public class DefaultSurenessConfigTest {

    @Test
    public void init() {
        DocumentResourceAccess.setYamlName("sureness-sample.yml");
        assertDoesNotThrow(() -> new DefaultSurenessConfig(DefaultSurenessConfig.SUPPORT_SERVLET));
        assertDoesNotThrow(() -> new DefaultSurenessConfig(DefaultSurenessConfig.SUPPORT_JAX_RS));
    }
}