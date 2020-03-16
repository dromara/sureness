package com.usthe.sureness.provider.annotation;

import java.util.LinkedList;
import java.util.List;

/**
 * @author tomsun28
 * @date 23:57 2020-03-16
 */
public class AnnotationLoader {

    /**
     * 注解的指定扫描包
     */
    private List<String> scanPackages;

    /**
     * 注解的指定扫描类
     */
    private List<String> scanClasses;


    /**
     * 从指定的扫描包里遍历扫描出类
     * @param packages 包路径
     * @return 包下的类
     */
    private List<String> scanPackages(List<String> packages) {
        if (packages == null) {
            return new LinkedList<>();
        }
        // todo
        return null;
    }



}
