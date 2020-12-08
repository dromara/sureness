package com.usthe.sureness.provider.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * sureness annotation
 * The modified resource with @WithoutAuth can be accessed without authentication protection
 * @author tomsun28
 * @date 23:38 2020-03-16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithoutAuth {

    /**
     * request uri mapping
     * @return uri
     */
    String mapping();

    /**
     * request method
     * @return method - post,get,put,delete ...
     */
    String method();
}
