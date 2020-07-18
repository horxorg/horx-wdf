package org.horx.wdf.springboot.config;

import org.horx.wdf.common.extension.session.support.HttpSessionHandler;
import org.horx.wdf.common.spring.session.CacheableJdbcSessionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;

@Configuration
public class WdfWebConfig {

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
        sessionRepository.setDefaultMaxInactiveInterval(2400);
        sessionRepository.setPersistInterval(120);
        return sessionRepository;
    }

}
