package org.dromara.sureness;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.dromara.sureness.DefaultSurenessConfig;
import org.dromara.sureness.provider.ducument.DocumentResourceAccess;

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