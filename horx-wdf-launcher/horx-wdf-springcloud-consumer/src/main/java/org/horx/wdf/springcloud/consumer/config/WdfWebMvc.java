package org.horx.wdf.springcloud.consumer.config;

import org.horx.wdf.common.entity.ErrorCodeEntity;
import org.horx.wdf.common.enums.ErrorCodeEnum;
import org.horx.wdf.common.spring.ExceptionResolver;
import org.horx.wdf.common.spring.arg.ArgEntityResolver;
import org.horx.wdf.common.spring.arg.ArgJsonResolver;
import org.horx.wdf.common.spring.http.converter.JsonHttpMessageConverter;
import org.horx.wdf.common.spring.interceptor.WebPageInterceptor;
import org.horx.wdf.sys.support.argresolver.ArgDataAuthResolver;
import org.horx.wdf.sys.support.interceptor.AccessPermissionInterceptor;
import org.horx.wdf.common.spring.interceptor.EnterMethodInterceptor;
import org.horx.wdf.common.spring.interceptor.UserInterceptor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
public class WdfWebMvc extends WebMvcConfigurerAdapter implements EnvironmentAware {

    private String enterMethodInterceptorPatterns;
    private String pageInterceptorPatterns;
    private String loginInterceptorPatterns;
    private String permissionInterceptorPatterns;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(argJsonResolver());
        argumentResolvers.add(argEntityResolver());
        argumentResolvers.add(argDataAuthResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(themeChangeInterceptor());

        if (enterMethodInterceptorPatterns != null) {
            registry.addInterceptor(enterMethodInterceptor()).addPathPatterns(enterMethodInterceptorPatterns.split(","));
        }
        if (pageInterceptorPatterns != null) {
            registry.addInterceptor(pageInterceptor()).addPathPatterns(pageInterceptorPatterns.split(","));
        }
        if (loginInterceptorPatterns != null) {
            registry.addInterceptor(loginInterceptor()).addPathPatterns(loginInterceptorPatterns.split(","));
        }
        if (permissionInterceptorPatterns != null) {
            registry.addInterceptor(permissionInterceptor()).addPathPatterns(permissionInterceptorPatterns.split(","));
        }
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        JsonHttpMessageConverter jsonHttpMessageConverter = new JsonHttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>(1);
        mediaTypes.add(MediaType.APPLICATION_JSON);
        jsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        jsonHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(0, jsonHttpMessageConverter);
    }

    @Bean
    ArgJsonResolver argJsonResolver() {
        return new ArgJsonResolver();
    }

    @Bean
    ArgEntityResolver argEntityResolver() {
        return new ArgEntityResolver();
    }

    @Bean
    ArgDataAuthResolver argDataAuthResolver() {
        return new ArgDataAuthResolver();
    }

    @Bean
    LocaleChangeInterceptor localeChangeInterceptor() {
        return new LocaleChangeInterceptor();
    }

    @Bean
    EnterMethodInterceptor enterMethodInterceptor() {
        return new EnterMethodInterceptor();
    }

    @Bean
    WebPageInterceptor pageInterceptor() {
        return new WebPageInterceptor();
    }

    @Bean
    UserInterceptor loginInterceptor() {
        return new UserInterceptor();
    }

    @Bean
    AccessPermissionInterceptor permissionInterceptor() {
        return new AccessPermissionInterceptor();
    }

    @Bean
    ThemeChangeInterceptor themeChangeInterceptor() {
        ThemeChangeInterceptor themeChangeInterceptor = new ThemeChangeInterceptor();
        themeChangeInterceptor.setParamName("theme");
        return themeChangeInterceptor;
    }

    @Bean
    LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        localeResolver.setCookieMaxAge(604800);
        localeResolver.setCookieName("lang");
        return localeResolver;
    }

    @Bean
    ThemeSource themeSource() {
        ResourceBundleThemeSource themeSource = new ResourceBundleThemeSource();
        themeSource.setBasenamePrefix("theme/theme-");
        return themeSource;
    }

    @Bean
    ThemeResolver themeResolver() {
        CookieThemeResolver themeResolver = new CookieThemeResolver();
        themeResolver.setDefaultThemeName("blue");
        themeResolver.setCookieMaxAge(604800);
        themeResolver.setCookieName("theme");
        return themeResolver;
    }

    @Bean
    ExceptionResolver exceptionResolver() {
        ExceptionResolver exceptionResolver = new ExceptionResolver();
        exceptionResolver.setDefaultErrorCode(new ErrorCodeEntity(ErrorCodeEnum.B0001.getCode(), "common.err.unknown"));
        return exceptionResolver;
    }


    @Override
    public void setEnvironment(Environment environment) {
        enterMethodInterceptorPatterns = environment.getProperty("wdf.enterMethodInterceptor.patterns");
        pageInterceptorPatterns = environment.getProperty("wdf.pageInterceptor.patterns");
        loginInterceptorPatterns = environment.getProperty("wdf.loginInterceptor.patterns");
        permissionInterceptorPatterns = environment.getProperty("wdf.permissionInterceptor.patterns");
    }
}
