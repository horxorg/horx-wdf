package org.horx.wdf.common.spring.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

/**
 * 忽略缓存错误的缓存异常处理器。
 * @since 1.0.3
 */
public class IgnoreCacheErrorHandler implements CacheErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(IgnoreCacheErrorHandler.class);

    @Override
    public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
        logger.error("Get缓存异常,name:{}", cache.getName(), e);
    }

    @Override
    public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
        logger.error("Put异常,name:{}", cache.getName(), e);
    }

    @Override
    public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
        logger.error("Evict缓存异常,name:{}", cache.getName(), e);
    }

    @Override
    public void handleCacheClearError(RuntimeException e, Cache cache) {
        logger.error("Clear缓存异常,name:{}", cache.getName(), e);
    }
}
