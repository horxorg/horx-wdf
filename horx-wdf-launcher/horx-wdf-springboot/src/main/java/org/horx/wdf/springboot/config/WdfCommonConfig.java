package org.horx.wdf.springboot.config;

import org.horx.wdf.common.config.CommonConfig;
import org.horx.wdf.common.tools.EntityTool;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.common.tools.ServerEnvironment;
import org.horx.wdf.common.tools.WebTool;
import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.common.spring.LocaleMessageSource;
import org.horx.wdf.common.spring.PropertyPlaceholder;
import org.horx.wdf.common.spring.SpringContext;
import org.horx.wdf.sys.config.SysConfig;
import org.horx.wdf.sys.extension.accesslog.AccessLogConfigHandler;
import org.horx.wdf.sys.extension.accesslog.AccessLogHandler;
import org.horx.wdf.sys.extension.accesslog.support.AccessLogDbHandler;
import org.horx.wdf.sys.extension.accesslog.support.AccessLoggerHandler;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.ArrayList;
import java.util.List;

@Configuration
class WdfCommonConfig implements EnvironmentAware {

    private String messageSourceLocations;

    private String propertyLocations;

    @Bean
    EntityExtension entityExtension() {
        EntityExtension entityExtension = new EntityExtension();
        return entityExtension;
    }

    @Bean
    MessageSource messageSource() {
        LocaleMessageSource messageSource = new LocaleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(false);
        messageSource.setCacheSeconds(86400);

        if (messageSourceLocations != null) {
            messageSource.setBasenames(messageSourceLocations.split(","));
        }

        return messageSource;
    }

    @Bean
    PropertyPlaceholder propertyConfigurer() {
        PropertyPlaceholder propertyPlaceholder = new PropertyPlaceholder();

        if (propertyLocations != null) {
            String[] arr = propertyLocations.split(",");
            Resource[] resources = new Resource[arr.length];

            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            for (int i = 0; i < arr.length; i++) {
                resources[i] = resolver.getResource(arr[i]);
            }
            propertyPlaceholder.setLocations(resources);
        }

        return propertyPlaceholder;
    }

    @Bean
    SpringContext springContext() {
        return new SpringContext();
    }

    @Bean
    LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource());
        return localValidatorFactoryBean;
    }

    @Bean
    CommonConfig commonConfig() {
        return new CommonConfig();
    }

    @Bean
    MsgTool msgTool() {
        return new MsgTool();
    }

    @Bean
    WebTool webTool() {
        return new WebTool();
    }

    @Bean
    EntityTool entityTool() {
        return new EntityTool();
    }

    @Bean
    ServerEnvironment serverEnvironment() {
        ServerEnvironment serverEnvironment = new ServerEnvironment();
        return serverEnvironment;
    }

    @Bean
    AccessLoggerHandler accessLoggerHandler() {
        return new AccessLoggerHandler();
    }

    @Bean
    AccessLogDbHandler accessLogDbHandler() {
        return new AccessLogDbHandler();
    }

    @Bean
    AccessLogHandler accessLogHandler() {
        AccessLogConfigHandler accessLogConfigHandler = new AccessLogConfigHandler();
        List<AccessLogHandler> list = new ArrayList<>();
        list.add(accessLoggerHandler());
        list.add(accessLogDbHandler());
        accessLogConfigHandler.setAccessLogHandlerList(list);
        return accessLogConfigHandler;
    }

    @Override
    public void setEnvironment(Environment environment) {
        messageSourceLocations = environment.getProperty("wdf.messageSource.locations");
        propertyLocations = environment.getProperty("wdf.property.locations");
    }

    @Bean
    SysConfig sysConfig() {
        return new SysConfig();
    }

    @Bean
    SysContextHolder sysContextHolder() {
        return new SysContextHolder();
    }
}
