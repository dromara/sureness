package com.usthe.sureness.provider.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口权限修饰注解，被修饰的接口支持roles内角色访问
 * @author tomsun28
 * @date 23:22 2020-03-16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface authorizedRole {
    String[] roles();
}
