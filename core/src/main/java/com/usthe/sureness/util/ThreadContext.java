package com.usthe.sureness.util;

import com.usthe.sureness.subject.SubjectSum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *   learn from shiro ThreadContext
 * @author from shiro
 * @date 23:01 2019-01-09
 */
public class ThreadContext {

    private static final Logger logger = LoggerFactory.getLogger(ThreadContext.class);

    public static final String SUBJECT_KEY = ThreadContext.class.getName() + "_SUBJECT_KEY";
    @SuppressWarnings("unchecked")
    private static final ThreadLocal<Map<Object, Object>> RESOURCES = new ThreadContext.InheritableThreadLocalMap();

    public static void bind(SubjectSum subject) {
        if (subject != null) {
            put(SUBJECT_KEY, subject);
        }
    }

    public static SubjectSum unbindSubject() {
        return (SubjectSum)remove(SUBJECT_KEY);
    }

    public static SubjectSum getBindSubject() {
        return (SubjectSum)get(SUBJECT_KEY);
    }


    private static void put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        } else if (value == null) {
            remove(key);
        } else {
            ensureResourcesInitialized();
            (RESOURCES.get()).put(key, value);
            if (logger.isTraceEnabled()) {
                String msg = "Bound value of type [" + value.getClass().getName() + "] for key [" + key + "] to thread [" + Thread.currentThread().getName() + "]";
                logger.trace(msg);
            }
        }
    }

    private static Object get(Object key) {
        if (logger.isTraceEnabled()) {
            String msg = "get() - in thread [" + Thread.currentThread().getName() + "]";
            logger.trace(msg);
        }
        Map<Object, Object> perThreadResources = RESOURCES.get();
        Object value = perThreadResources != null ? perThreadResources.get(key) : null;
        if (value != null && logger.isTraceEnabled()) {
            String msg = "Retrieved value of type [" + value.getClass().getName() + "] for key [" + key + "] bound to thread [" + Thread.currentThread().getName() + "]";
            logger.trace(msg);
        }
        return value;
    }

    private static Object remove(Object key) {
        Map<Object, Object> perThreadResources = RESOURCES.get();
        Object value = perThreadResources != null ? perThreadResources.remove(key) : null;
        if (value != null && logger.isTraceEnabled()) {
            String msg = "Removed value of type [" + value.getClass().getName() + "] for key [" + key + "]from thread [" + Thread.currentThread().getName() + "]";
            logger.trace(msg);
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    private static void ensureResourcesInitialized() {
        if (RESOURCES.get() == null) {
            RESOURCES.set(new HashMap(8));
        }

    }

    public static void remove() {
        RESOURCES.remove();
    }

    private static final class InheritableThreadLocalMap<T extends Map<Object, Object>> extends InheritableThreadLocal<Map<Object, Object>> {
        private InheritableThreadLocalMap() {
        }

        @Override
        @SuppressWarnings("unchecked")
        protected Map<Object, Object> childValue(Map<Object, Object> parentValue) {
            return parentValue != null ? (Map)((HashMap)parentValue).clone() : null;
        }
    }
}
