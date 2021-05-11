package zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author tomsun28
 */
@SpringBootApplication
@EnableZuulProxy
public class SurenessZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurenessZuulApplication.class, args);
    }

}
