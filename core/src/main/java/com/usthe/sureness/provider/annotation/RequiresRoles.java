package com.usthe.sureness.provider.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * sureness annotation
 * The modified resource with @RequiresRoles can be accessed when user role in [roles]
 * @author tomsun28
 * @date 23:22 2020-03-16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRoles {

    /**
     * support roles
     * @return roles
     */
    String[] roles() default {};

    /**
     * request uri
     * @return uri
     */
    String mapping();

    /**
     * request method
     * @return method - post,get,put,delete ...
     */
    String method();
}
