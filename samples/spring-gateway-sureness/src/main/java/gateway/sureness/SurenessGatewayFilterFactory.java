package gateway.sureness;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * surenessFilter factory
 * @author tomsun28
 * @date 2021/4/28 23:40
 */
@Component
public class SurenessGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return new SurenessFilter();
    }

    @Override
    public String name() {
        return "SurenessAuth";
    }
}
