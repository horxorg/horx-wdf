package org.horx.wdf.springcloud.provider.config;

import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.common.jdbc.dialect.DbDialect;
import org.horx.wdf.common.jdbc.dialect.support.MysqlDialect;
import org.horx.wdf.common.mybatis.interceptor.KeyGeneratorInterceptor;
import org.horx.wdf.common.mybatis.interceptor.PaginationInterceptor;
import org.horx.wdf.common.mybatis.interceptor.ResultMapInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
class MybatisConfig {

    @Autowired
    private EntityExtension entityExtension;

    @Bean
    DbDialect dbDialect() {
        return new MysqlDialect();
    }

    @Bean
    @Order(1)
    KeyGeneratorInterceptor keyGeneratorInterceptor() {
        KeyGeneratorInterceptor keyGeneratorInterceptor = new KeyGeneratorInterceptor();
        keyGeneratorInterceptor.setDbDialect(dbDialect());
        return keyGeneratorInterceptor;
    }

    @Bean
    @Order(2)
    PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDbDialect(dbDialect());
        paginationInterceptor.setEntityExtension(entityExtension);
        return paginationInterceptor;
    }

    @Bean
    @Order(3)
    ResultMapInterceptor resultMapInterceptor() {
        ResultMapInterceptor resultMapInterceptor = new ResultMapInterceptor();
        resultMapInterceptor.setEntityExtension(entityExtension);
        return resultMapInterceptor;
    }

}
