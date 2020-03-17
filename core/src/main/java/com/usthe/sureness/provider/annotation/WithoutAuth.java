package com.usthe.sureness.provider.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口认证修饰注解，被修饰的接口不需要认证保护即可访问
 * @author tomsun28
 * @date 23:38 2020-03-16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithoutAuth {

    /**
     * 请求路径
     * @return uri
     */
    String mapping();

    /**
     * 请求方式
     * @return method - post,get,put,delete ...
     */
    String method();
}
