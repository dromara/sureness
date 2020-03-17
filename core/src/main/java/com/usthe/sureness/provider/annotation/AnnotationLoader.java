package com.usthe.sureness.provider.annotation;

import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.util.ClassScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author tomsun28
 * @date 23:57 2020-03-16
 */
public class AnnotationLoader implements PathTreeProvider {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationLoader.class);

    /**
     * 注解的指定扫描包
     */
    private List<String> scanPackages;

    /**
     * 扫描出来的class
     */
    private List<Class<?>> scanClasses;

    /**
     * 是否初始化
     */
    private volatile boolean isInit = false;

    private synchronized void init() {
        if (scanPackages == null) {
            scanPackages = Collections.singletonList(" ");
        }
        scanClasses = ClassScanner.scanPackages(scanPackages);
        isInit = true;
        logger.info("sureness: annotationLoader success init, load {} classes total.", scanClasses.size());
    }

    @Override
    public Set<String> providePathData() {
        if (!isInit) {
            init();
        }
        // get provide path data from load class
        Set<String> resource = new HashSet<>();
        for (Class<?> clazz : scanClasses) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(WithoutAuth.class)) {
                    continue;
                }
                if (method.isAnnotationPresent(RequiresRoles.class)) {
                    RequiresRoles requiresRoles = method.getDeclaredAnnotation(RequiresRoles.class);

                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < requiresRoles.roles().length; i++) {
                        if (i < requiresRoles.roles().length) {
                            builder.append(requiresRoles.roles()[i]).append(",");
                        } else {
                            builder.append(requiresRoles.roles()[i]);
                        }
                        String path = requiresRoles.mapping().toLowerCase()
                                + "===" + requiresRoles.method().toLowerCase()
                                + "===[" + builder.toString() + "]";
                        resource.add(path);
                        if (logger.isDebugEnabled()) {
                            logger.debug("sureness: annotationLoader load path {}.", path);
                        }
                    }
                }
            }
        }
        return resource;
    }

    @Override
    public Set<String> provideExcludedResource() {
        if (!isInit) {
            init();
        }
        // get provide exclude path data from load class
        Set<String> resource = new HashSet<>();
        for (Class<?> clazz : scanClasses) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(WithoutAuth.class)) {
                    WithoutAuth withoutAuth = method.getDeclaredAnnotation(WithoutAuth.class);
                    String path = withoutAuth.mapping().toLowerCase()
                            + "===" + withoutAuth.method().toLowerCase();
                    resource.add(path);
                    if (logger.isDebugEnabled()) {
                        logger.debug("sureness: annotationLoader load exclude path {}.", path);
                    }
                }
            }
        }
        return resource;
    }

    public List<String> getScanPackages() {
        return scanPackages;
    }

    public void setScanPackages(List<String> scanPackages) {
        this.scanPackages = scanPackages;
    }

}
