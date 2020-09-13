package org.horx.wdf.common.spring.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis缓存管理器工厂类。
 * @since 1.0.3
 */
public class RedisCacheManagerFactory {

    private RedisConnectionFactory redisConnectionFactory;

    private RedisCacheConfig defaultCacheConfiguration;

    private Map<String, RedisCacheConfig> cacheConfigurations;

    private boolean disableCreateOnMissingCache;

    public RedisCacheManager build() {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfiguration.toRedisCacheConfiguration());

        if (!CollectionUtils.isEmpty(cacheConfigurations)) {
            Map<String, RedisCacheConfiguration> cacheConfigMap = new HashMap<>();
            for (Map.Entry<String, RedisCacheConfig> entry : cacheConfigurations.entrySet()) {
                cacheConfigMap.put(entry.getKey(), entry.getValue().toRedisCacheConfiguration());
            }
            builder = builder.withInitialCacheConfigurations(cacheConfigMap);
        }

        if (disableCreateOnMissingCache) {
            builder = builder.disableCreateOnMissingCache();
        }

        return builder.build();
    }

    public RedisConnectionFactory getRedisConnectionFactory() {
        return redisConnectionFactory;
    }

    @Autowired
    public void setRedisConnectionFactory(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    public RedisCacheConfig getDefaultCacheConfiguration() {
        return defaultCacheConfiguration;
    }

    public void setDefaultCacheConfiguration(RedisCacheConfig defaultCacheConfiguration) {
        this.defaultCacheConfiguration = defaultCacheConfiguration;
    }

    public Map<String, RedisCacheConfig> getCacheConfigurations() {
        return cacheConfigurations;
    }

    public void setCacheConfigurations(Map<String, RedisCacheConfig> cacheConfigurations) {
        this.cacheConfigurations = cacheConfigurations;
    }

    public boolean isDisableCreateOnMissingCache() {
        return disableCreateOnMissingCache;
    }

    public void setDisableCreateOnMissingCache(boolean disableCreateOnMissingCache) {
        this.disableCreateOnMissingCache = disableCreateOnMissingCache;
    }
}
