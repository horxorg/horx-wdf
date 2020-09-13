package org.horx.wdf.common.spring.cache.redis;

import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

/**
 * Redis缓存配置。
 * @since 1.0.3
 */
public class RedisCacheConfig {

    private Duration ttl;
    private boolean cacheNullValues;
    private boolean usePrefix;
    private String keyPrefix;

    public RedisCacheConfiguration toRedisCacheConfiguration() {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(ttl);
        if (!cacheNullValues) {
            configuration = configuration.disableCachingNullValues();
        }
        if (!usePrefix) {
            configuration = configuration.disableKeyPrefix();
        } else {
            configuration = configuration.prefixKeysWith(keyPrefix);
        }
        return configuration;
    }

    public Duration getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = Duration.parse("PT"+ttl);
    }

    public boolean isCacheNullValues() {
        return cacheNullValues;
    }

    public void setCacheNullValues(boolean cacheNullValues) {
        this.cacheNullValues = cacheNullValues;
    }

    public boolean isUsePrefix() {
        return usePrefix;
    }

    public void setUsePrefix(boolean usePrefix) {
        this.usePrefix = usePrefix;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
