package com.usthe.sureness.mgt.jwt;

import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.Md5Util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stand-alone map, store jwt and expire key periodically
 */
public class PassiveExpiringJwtMap extends ConcurrentHashMap<String, Long> {

    // map that controls whether jwt expires
    private final Map<String, Long> expiringJwtMap = new ConcurrentHashMap<>(256);

    // if timeToLive is set, it works globally, unit: second
    private int timeToLive = 0;

    // identifier used to check whether timeToLive has been set
    private boolean isTTLSetup = false;

    public PassiveExpiringJwtMap(Object timeToLive) {
        super();
        loadTimeToLive(timeToLive);
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public boolean isTTLSetup() {
        return isTTLSetup;
    }

    /**
     * Load timeToLive from configuration file (eg:sureness.yml)
     * Note: By default, sureness.yml is loaded. If you need to specify another file,
     * please set the file path by using DocumentResourceAccess.setYamlName(String yamlName)
     * @return configured ttl if the configuration succeeds, 0 otherwise
     */
    public long loadTimeToLive(Object timeToLive) {
        if (null != timeToLive) {
            this.timeToLive = (int) timeToLive;
            this.isTTLSetup = true;
        }
        return this.timeToLive;
    }

    /**
     * Add jwt and its value to the map
     * @param jwt json web token
     * @param value jwt period(seconds)
     * @return value associated with key, null otherwise(null also indicates jwt is added for the first time)
     */
    @Override
    public Long put(String jwt, Long value) {
        if (JsonWebTokenUtil.isNotJsonWebToken(jwt)) {
            return null;
        }
        String key = Md5Util.md5(jwt);
        // if ttl is set up, ttl is the global expiration time
        if (isTTLSetup()) {
            value = (long) timeToLive;
        }
        return expiringJwtMap.put(key, System.currentTimeMillis() + value * 1000);
    }

    /**
     * Add jwt and its value to the map(ttl is set by default)
     * Note: if you explicitly set ttl in configuration file, please use this method to put jwt into map
     * @param jwt json web token
     * @return value associated with key, null otherwise(null also indicates jwt is added for the first time)
     */
    public Long put(String jwt) {
        if (JsonWebTokenUtil.isNotJsonWebToken(jwt)) {
            return null;
        }
        String key = Md5Util.md5(jwt);
        return expiringJwtMap.put(key, System.currentTimeMillis() + timeToLive * 1000L);
    }

    /**
     * Return the value of jwt
     * @param jwt the jwt whose associated value is to be returned
     * @return the expiration time corresponding to jwt(timestamp in milliseconds)
     */
    @Override
    public Long get(Object jwt) {
        String key = Md5Util.md5((String) jwt);
        if (checkExpiredKey(key)) {
            return null;
        }
        return expiringJwtMap.get(key);
    }

    /**
     * Determine if map contains the jwt
     * @param jwt possible json web token
     * @return true if jwt exists in map and has not expired, false otherwise
     */
    @Override
    public boolean containsKey(Object jwt) {
        String key = Md5Util.md5((String) jwt);
        return !checkExpiredKey(key);
    }

    /**
     * Return the number of jwt in this map
     * @return the number of jwt
     */
    @Override
    public int size() {
        clearExpiredKey();
        return expiringJwtMap.size();
    }

    /**
     * Clear the map and set state to default
     */
    @Override
    public void clear() {
        timeToLive = 0;
        isTTLSetup = false;
        expiringJwtMap.clear();
    }

    /***
     * Check if key in expiringMap already expired and delete expired key
     * @param key jwt after md5
     * @return true if the key expired, false otherwise
     */
    private boolean checkExpiredKey(String key) {
        // if key does not exist, it's already expired
        if (!expiringJwtMap.containsKey(key)) {
            return Boolean.TRUE;
        }
        Long expiryTime = expiringJwtMap.get(key);
        boolean flag = Boolean.FALSE;
        // if key expired
        if (System.currentTimeMillis() > expiryTime) {
            flag = Boolean.TRUE;
            expiringJwtMap.remove(key);
        }
        return flag;
    }

    /**
     * Clear all expired key, a helper method
     */
    private void clearExpiredKey() {
        for (String key : expiringJwtMap.keySet()) {
            checkExpiredKey(key);
        }
    }
}
