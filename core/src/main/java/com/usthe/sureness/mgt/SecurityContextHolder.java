package com.usthe.sureness.mgt;


import com.usthe.sureness.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * hold the each thread subject and more ,  learn from spring security
 * @author tomsun28
 * @date 16:25 2019-02-24
 */
@Deprecated
public class SecurityContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(SecurityContextHolder.class);

    @SuppressWarnings("unchecked")
    private static final ThreadLocal<Subject> CONTEXT_HOLDER = new InheritableThreadLocal();

    public void clearContext() {
        CONTEXT_HOLDER.remove();
    }

    public void bindContext(Subject context) {
        CONTEXT_HOLDER.set(context);
    }

    public Optional<Subject> getContext() {
        return Optional.ofNullable(CONTEXT_HOLDER.get());
    }
}
