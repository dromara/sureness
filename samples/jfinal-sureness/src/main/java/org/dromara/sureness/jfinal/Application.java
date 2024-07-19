package org.dromara.sureness.jfinal;

import org.dromara.sureness.DefaultSurenessConfig;

import com.jfinal.config.*;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;

/**
 * startup
 * @author tomsun28
 * @date 2021/4/30 18:52
 */
public class Application extends JFinalConfig {

    public static void main(String[] args) {
        UndertowServer.start(Application.class, 80, true);
    }

    @Override
    public void configConstant(Constants me) {
        // init sureness config
        new DefaultSurenessConfig();
        me.setDevMode(true);
    }

    @Override
    public void configRoute(Routes me) {
        me.scan("com.usthe.sureness.jfinal");
    }

    @Override
    public void configEngine(Engine me) {}
    @Override
    public void configPlugin(Plugins me) {}
    @Override
    public void configInterceptor(Interceptors me) {}
    @Override
    public void configHandler(Handlers me) {}
}
