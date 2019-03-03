package com.usthe.sureness.util;

import java.util.HashMap;
import java.util.Map;

/**
 *  *  learn from shiro
 * @author tomsun28
 * @date 23:01 2019-01-09
 */
public class ThreadContext {

    public static final String SUBJECT_KEY = ThreadContext.class.getName() + "_SUBJECT_KEY";
    @SuppressWarnings("unchecked")
    private static final ThreadLocal<Map<Object, Object>> RESOURCES = new ThreadContext.InheritableThreadLocalMap();



    private void removeResources() {
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
