package com.usthe.sureness.mgt.jwt;

import com.usthe.sureness.mgt.SurenessNoInitException;
import com.usthe.sureness.processor.exception.ExpiredCredentialsException;
import com.usthe.sureness.provider.ducument.DocumentResourceAccess;
import com.usthe.sureness.provider.ducument.DocumentResourceEntity;
import com.usthe.sureness.util.SurenessConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Default jwt manager, the entry of jwt management tool
 * initialize redis and map, can check jwt state
 */
public class DefaultJwtManager {

    private static final Logger logger = LoggerFactory.getLogger(DefaultJwtManager.class);

    private DefaultRedis defaultRedis;

    private PassiveExpiringJwtMap passiveExpiringJwtMap;

    public DefaultRedis getDefaultRedis() {
        return defaultRedis;
    }

    public void setDefaultRedis(DefaultRedis defaultRedis) {
        this.defaultRedis = defaultRedis;
    }

    public PassiveExpiringJwtMap getPassiveExpiringJwtMap() {
        return passiveExpiringJwtMap;
    }

    public void setPassiveExpiringJwtMap(PassiveExpiringJwtMap passiveExpiringJwtMap) {
        this.passiveExpiringJwtMap = passiveExpiringJwtMap;
    }

    private DefaultJwtManager() {
        init();
    }

    /**
     * Initialize the jwt cache configuration
     * Note: redis could be null, but map must not be null
     */
    private void init(){
        DocumentResourceEntity documentResourceEntity = null;
        try {
            documentResourceEntity = DocumentResourceAccess.loadConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String cacheType = SurenessConstant.TOKEN_CACHE_MAP;
        if (null!=documentResourceEntity.getTokenCache()) {
            for (Map<String, Object> map : documentResourceEntity.getTokenCache()) {
                cacheType = (null==map.get("type")) ? SurenessConstant.TOKEN_CACHE_MAP : (String)map.get("type");
                if (SurenessConstant.TOKEN_CACHE_MAP.equalsIgnoreCase(cacheType)) {
                    passiveExpiringJwtMap = new PassiveExpiringJwtMap(map.get("ttl"));
                } else if (SurenessConstant.TOKEN_CACHE_REDIS.equalsIgnoreCase(cacheType)){
                    defaultRedis = new DefaultRedis(documentResourceEntity);
                }
            }
        } else {
            passiveExpiringJwtMap = new PassiveExpiringJwtMap(null);
        }
    }
    /**
     * Check if redis is enabled
     * @return true if redis enabled, false otherwise
     */
    public Boolean isRedisEnabled() {
        if (defaultRedis == null) {
            return false;
        }
        return defaultRedis.isRedisServiceOpen();
    }

    /**
     * Check the initialization of defaultJwtManager's components,
     * throw SurenessNoInitException if it is not fully initialized
     */
    public void checkInnerComponentsInit() {
        if (defaultRedis == null && passiveExpiringJwtMap == null) {
            logger.error("DefaultJwtManager init error : DefaultJwtManager not init fill component");
            throw new SurenessNoInitException("DefaultJwtManager not init fill component");
        }
    }

    /**
     * Common method to cache token, set period time from yaml file
     * @param jwt json web token
     * @param period expiration time(seconds)
     */
    public void cacheToken(String jwt, Long period) {
        checkInnerComponentsInit();
        if (isRedisEnabled()) {
            defaultRedis.setJwt(jwt);
            defaultRedis.setJwtExpiration(jwt, period);
        } else {
            passiveExpiringJwtMap.put(jwt, period);
        }
    }

    /**
     * Check if jwt exists, if it does not, throw ExpiredCredentialException
     * @param jwt json web token
     */
    public void checkJwt(String jwt) {
        checkInnerComponentsInit();
        if (isRedisEnabled()) {
            if (defaultRedis.jwtExists(jwt) == 0) {
                throw new ExpiredCredentialsException("this jwt has expired");
            }
        } else {
            if (!passiveExpiringJwtMap.containsKey(jwt)) {
                throw new ExpiredCredentialsException("this jwt has expired");
            }
        }
    }

    private static class SingletonDefaultJwtManager {
        private static final DefaultJwtManager INSTANCE = new DefaultJwtManager();
    }

    public static DefaultJwtManager getInstance() {
        return SingletonDefaultJwtManager.INSTANCE;
    }
}
