package com.usthe.sureness.mgt;

import com.sun.tools.javac.util.Assert;
import com.usthe.sureness.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/* *
 * @Author tomsun28
 * @Description  hold the each thread subject and more ,  learn from shiro
 * @Date 16:25 2019-02-24
 */
public class SecurityContextHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityContextHolder.class);

    @SuppressWarnings("unchecked")
    private static final ThreadLocal<Subject> contextHolder = new InheritableThreadLocal();

    public void clearContext() {
        contextHolder.remove();
    }

    public void bindContext(Subject context) {
        Assert.checkNonNull(context, "Only non-null SubjectContext instances are permitted");
        contextHolder.set(context);
    }

    public Optional<Subject> getContext() {
        return Optional.ofNullable(contextHolder.get());
    }
}
