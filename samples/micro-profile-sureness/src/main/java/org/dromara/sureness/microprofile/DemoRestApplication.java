package org.dromara.sureness.microprofile;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.dromara.sureness.SurenessDefaultConfig;

/**
 * @author Lenovo
 */
@ApplicationPath("/")
public class DemoRestApplication extends Application {

    public DemoRestApplication(){
        super();
        new SurenessDefaultConfig(SurenessDefaultConfig.SUPPORT_JAX_RS);
    }
}
