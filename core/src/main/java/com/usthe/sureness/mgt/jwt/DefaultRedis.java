package com.usthe.sureness.mgt.jwt;

import com.usthe.sureness.provider.ducument.DocumentResourceEntity;
import com.usthe.sureness.util.JsonWebTokenUtil;
import com.usthe.sureness.util.Md5Util;
import com.usthe.sureness.util.SurenessConstant;
import io.jsonwebtoken.Claims;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Default redis class, after configuring redis, choose it as a container to store token cache
 */
public class DefaultRedis {
    // default command for redis
    private RedisCommands<String, String> commandInstance;

    private Boolean isRedisServiceOpen = false;

    // default time to cache jwt, unit: minute
    private int cacheTime = SurenessConstant.DEFAULT_TOKEN_CACHE_TIME;

    public DefaultRedis(DocumentResourceEntity documentResourceEntity) {
        commandInstance = loadRedis(documentResourceEntity);
    }

    public RedisCommands<String, String> getCommandInstance() {
        return commandInstance;
    }

    public void setCommandInstance(RedisCommands<String, String> instance) {
        this.commandInstance = instance;
    }

    /**
     * The variable that marks whether the Redis service is enabled in the configuration file
     * @return true if redis service is configured and enabled in the configuration file, false otherwise
     */
    public Boolean isRedisServiceOpen() {
        return isRedisServiceOpen;
    }

    /**
     * Test connectivity of current redis instance
     * @return true if redis connected, false otherwise
     */
    public Boolean isConnected() {
        return commandInstance.ping().equals("PONG");
    }

    /**
     * Load redis instance from configuration file (eg:sureness.yml)
     * Note: default loading "sureness.yml", if another file is required,
     * @param documentResourceEntity configured documentResourceEntity
     * @return redis command instance
     */
    public RedisCommands<String, String> loadRedis(DocumentResourceEntity documentResourceEntity) {
        Map<String, Object> currentMap = null;
        RedisURI redisURI = null;
        for (Map<String, Object> map : documentResourceEntity.getTokenCache()) {
            if (SurenessConstant.TOKEN_CACHE_REDIS.equalsIgnoreCase((String) map.get("type")) && (Boolean) map.get("enabled")) {
                currentMap = map;
                isRedisServiceOpen = true;
            }
        }
        if (currentMap == null) {
            return null;
        }
        if (currentMap.get("username") != null && currentMap.get("password") != null) {
            redisURI = RedisURI.builder()
                    .withHost((String) currentMap.get("host"))
                    .withPort((Integer) currentMap.get("port"))
                    .withTimeout(Duration.of((Integer) currentMap.get("timeout"), ChronoUnit.SECONDS))
                    .withAuthentication((String) currentMap.get("username"), (String) currentMap.get("password"))
                    .build();
        } else {
            redisURI = RedisURI.builder()
                    .withHost((String) currentMap.get("host"))
                    .withPort((Integer) currentMap.get("port"))
                    .withTimeout(Duration.of((Integer) currentMap.get("timeout"), ChronoUnit.SECONDS))
                    .build();
        }
        if (null!=currentMap.get("ttl")) {
            cacheTime = (Integer) currentMap.get("ttl");
        }
        RedisClient redisClient = RedisClient.create(redisURI);
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        return connect.sync();
    }

    /**
     * Add jwt into redis and set its corresponding expiration time(timestamp in milliseconds)
     * @param jwt json web token
     * @return "OK" if set successfully, null otherwise
     */
    public String setJwt(String jwt) {
        if (JsonWebTokenUtil.isNotJsonWebToken(jwt)) {
            return null;
        }
        String key = Md5Util.md5(jwt);
        Claims claims = JsonWebTokenUtil.parseJwt(jwt);
        long expirationTimeInMills = claims.getExpiration().getTime();
        return commandInstance.set(key, String.valueOf(expirationTimeInMills));
    }

    /**
     * Return the expiration time of jwt
     * @param jwt json web token
     * @return the expiration time of jwt, null if key does not exist
     */
    public String getJwtExpiration(String jwt) {
        String key = Md5Util.md5(jwt);
        return commandInstance.get(key);
    }

    /**
     * Delete jwt from redis
     * @param jwt json web token
     * @return the number of jwt that are deleted
     */
    public Long deleteJwt(String jwt) {
        String key = Md5Util.md5(jwt);
        return commandInstance.del(key);
    }

    /**
     * Check how many jwt exist
     * @param jwt json web token
     * @return number of jwt that exists
     */
    public Long jwtExists(String jwt) {
        String key = Md5Util.md5(jwt);
        return commandInstance.exists(key);
    }

    /**
     * Set the expiration time of jwt, default expiration time equals 30 minutes
     * @param jwt json web token
     * @param period survival time of jwt (seconds)
     * @return true if the period is set, false if jwt does not exist or period cannot be set
     */
    public Boolean setJwtExpiration(String jwt, Long period) {
        String key = Md5Util.md5(jwt);
        if (null == period) {
            period = cacheTime * 60L;
            return commandInstance.pexpire(key, period * 1000);
        }
        return commandInstance.pexpire(key, period * 1000);
    }

    /**
     * Remove all jwt from redis
     * @return "OK" if it executes successfully
     */
    public String clear() {
        return commandInstance.flushall();
    }

}
