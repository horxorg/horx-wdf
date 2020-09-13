package org.horx.wdf.springboot.config;

import org.horx.wdf.common.extension.session.support.HttpSessionHandler;
import org.horx.wdf.common.spring.session.CacheableJdbcSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;

import java.time.Duration;

@Configuration
public class WdfWebConfig {

    @Autowired(required = false)
    @Qualifier("stringRedisTemplate")
    private RedisTemplate redisTemplate;

    @Bean
    HttpSessionHandler sessionHandler() {
        return new HttpSessionHandler();
    }

    @Bean
    SpringHttpSessionConfiguration springHttpSessionConfiguration() {
        return new SpringHttpSessionConfiguration();
    }

    @Bean
    CacheableJdbcSessionRepository sessionRepository() {
        CacheableJdbcSessionRepository sessionRepository = new CacheableJdbcSessionRepository();
        sessionRepository.setDefaultMaxInactiveInterval("40m");
        sessionRepository.setPersistInterval("2m");
        sessionRepository.setLocalCacheInterval("5m");
        sessionRepository.setEnableRedis(false);
        sessionRepository.setRedisTemplate(redisTemplate);
        return sessionRepository;
    }

}
