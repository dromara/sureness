package org.dromara.sureness;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.dromara.sureness.SurenessDefaultConfig;
import org.dromara.sureness.provider.ducument.DocumentResourceAccess;

/**
 * @author tomsun28
 * @date 2021/1/17 11:02
 */
public class SurenessDefaultConfigTest {

    @Test
    public void init() {
        DocumentResourceAccess.setYamlName("sureness-sample.yml");
        assertDoesNotThrow(() -> new SurenessDefaultConfig(SurenessDefaultConfig.SUPPORT_JAKARTA_SERVLET));
        assertDoesNotThrow(() -> new SurenessDefaultConfig(SurenessDefaultConfig.SUPPORT_JAX_RS));
    }
}