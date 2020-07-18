package org.horx.wdf.springcloud.provider.config;

import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.springcloud.SpringcloudProviderContextInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SpringcloudProvider extends WebMvcConfigurerAdapter {

    @Bean
    SysContextHolder threadContextHolder() {
        return new SysContextHolder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(springcloudProviderContextInterceptor());
    }

    @Bean
    SpringcloudProviderContextInterceptor springcloudProviderContextInterceptor() {
        return new SpringcloudProviderContextInterceptor();
    }

}
