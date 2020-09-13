package org.horx.wdf.springboot.config;

import org.horx.wdf.common.spring.cache.IgnoreCacheErrorHandler;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置。
 * @since 1.0.3
 */
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Override
    public CacheErrorHandler errorHandler() {
        return new IgnoreCacheErrorHandler();
    }
}
