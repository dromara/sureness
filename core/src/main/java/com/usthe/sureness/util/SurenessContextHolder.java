package com.usthe.sureness.util;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectSum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * learn from shiro ThreadContext
 * @author from shiro
 * @date 23:01 2019-01-09
 */
public class SurenessContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(SurenessContextHolder.class);

    public static final String SUBJECT_KEY = "SUBJECT_KEY";

    private static final ThreadLocal<Map<Object, Object>> RESOURCES = InheritableThreadLocal
            .withInitial(() -> new HashMap<>(8));

    /**
     * Called before the thread ends
     */
    public static void clear() {
        if (RESOURCES.get() != null) {
            RESOURCES.get().clear();
        }
        RESOURCES.remove();
    }

    public static void bind(Object key, Object value) {
        internalPut(key, value);
    }

    public static void unbind(Object key) {
        if (key != null) {
            internalRemove(key);
        }
    }

    public static Object getBind(Object key) {
        if (key == null) {
            return null;
        }
        return internalGet(key);
    }

    public static void bindSubject(SubjectSum subjectSum) {
        internalPut(SUBJECT_KEY, subjectSum);
    }

    public static void bindSubject(Subject subject) {
        if (subject != null) {
            internalPut(SUBJECT_KEY, subject.generateSubjectSummary());
        }
    }

    public static void unbindSubject() {
        internalRemove(SUBJECT_KEY);
    }


    public static SubjectSum getBindSubject() {
        return (SubjectSum) internalGet(SUBJECT_KEY);
    }


    private static void internalPut(Object key, Object value) {
        if (key == null) {
            throw new NullPointerException("key cannot be null");
        } else if (value == null) {
            internalRemove(key);
        } else {
            ensureResourcesInitialized();
            RESOURCES.get().put(key, value);
        }
    }

    private static Object internalGet(Object key) {
        if (logger.isTraceEnabled()) {
            logger.trace("get() - in thread [{}]", Thread.currentThread().getName());
        }
        Map<Object, Object> perThreadResources = RESOURCES.get();
        return perThreadResources != null ? perThreadResources.get(key) : null;
    }

    private static void internalRemove(Object key) {
        Map<Object, Object> perThreadResources = RESOURCES.get();
        if (perThreadResources != null) {
            perThreadResources.remove(key);
        }
    }

    private static void ensureResourcesInitialized() {
        if (RESOURCES.get() == null) {
            RESOURCES.set(new HashMap<>(8));
        }
    }
}
