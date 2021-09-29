package com.usthe.sureness.microprofile;

import com.usthe.sureness.DefaultSurenessConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Lenovo
 */
@ApplicationPath("/")
public class DemoRestApplication extends Application {

    public DemoRestApplication(){
        super();
        new DefaultSurenessConfig(DefaultSurenessConfig.SUPPORT_JAX_RS);
    }
}
