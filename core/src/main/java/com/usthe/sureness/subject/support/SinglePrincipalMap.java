package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.PrincipalMap;

import java.util.*;

/**
 * principal map single impl
 * @author tomsun28
 * @date 2021/2/5 21:22
 */
public class SinglePrincipalMap implements PrincipalMap {

    private Map<String, Object> singleMap;

    public SinglePrincipalMap() {}

    public SinglePrincipalMap(Map<String, Object> principals) {
        setPrincipals(principals);
    }

    @Override
    public PrincipalMap setPrincipals(Map<String, Object> principals) {
        if (principals != null) {
            if (singleMap == null) {
                singleMap = principals;
            } else {
                singleMap.putAll(principals);
            }
        }
        return this;
    }

    @Override
    public Object setPrincipal(String key, Object principal) {
        if (key == null || principal == null) {
            return null;
        }
        ensureSingleMap().put(key, principal);
        return principal;
    }

    @Override
    public Object getPrincipal(String key) {
        return get(key);
    }

    @Override
    public Object removePrincipal(String key) {
        return remove(key);
    }

    @Override
    public int size() {
        return singleMap == null ? 0 : singleMap.size();
    }

    @Override
    public boolean isEmpty() {
        return singleMap == null || singleMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return singleMap != null && singleMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return singleMap != null && singleMap.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return singleMap == null ? null : singleMap.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return ensureSingleMap().put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return singleMap == null ? null : singleMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        if (map != null && !map.isEmpty()) {
            ensureSingleMap().putAll(map);
        }
    }

    @Override
    public void clear() {
        if (singleMap != null) {
            singleMap.clear();
            singleMap = null;
        }
    }

    @Override
    public Set<String> keySet() {
        return singleMap == null ? Collections.emptySet() : singleMap.keySet();
    }

    @Override
    public Collection<Object> values() {
        return singleMap == null ? Collections.emptySet() : singleMap.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return singleMap == null ? Collections.emptySet() : singleMap.entrySet();
    }

    @Override
    public String toString() {
        return "SinglePrincipalMap{" +
                "singleMap=" + singleMap +
                '}';
    }

    private Map<String, Object> ensureSingleMap() {
        if (this.singleMap == null) {
            this.singleMap = new HashMap<>(8);
        }
        return this.singleMap;
    }
}
