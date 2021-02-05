package com.usthe.sureness.subject;

import java.util.Map;

/**
 * map for principal to storage
 * @author tomsun28
 * @date 2021/2/5 21:14
 */
public interface PrincipalMap extends Map<String, Object> {

    /**
     * set principal map
     * @param principals principal map
     * @return PrincipalMap instance
     */
    PrincipalMap setPrincipals(Map<String, Object> principals);

    /**
     * set principal
     * @param key key
     * @param principal principal value
     * @return principal value
     */
    Object setPrincipal(String key, Object principal);

    /**
     * get principal by key
     * @param key key
     * @return principal
     */
    Object getPrincipal(String key);

    /**
     * remove principal by key
     * @param key key
     * @return principal
     */
    Object removePrincipal(String key);

}
