package org.horx.wdf.springcloud.provider.config;

import org.horx.wdf.common.spring.advice.ArgEntityAdvice;
import org.horx.wdf.sys.extension.datalog.DataLogConfigHandler;
import org.horx.wdf.sys.extension.datalog.DataLogHandler;
import org.horx.wdf.sys.extension.datalog.support.DataLogDbHandler;
import org.horx.wdf.sys.extension.datalog.support.DataLoggerHandler;
import org.horx.wdf.common.extension.level.support.IdLevelCodeGenerator;
import org.horx.wdf.common.extension.user.pwd.PwdEncoder;
import org.horx.wdf.common.extension.user.pwd.PwdEncoderConfig;
import org.horx.wdf.common.extension.user.pwd.support.Md5SaltPwdEncoder;
import org.horx.wdf.common.extension.user.pwd.support.PlaintextPwdEncoder;
import org.horx.wdf.sys.extension.identification.UserIdentificationConfigHandler;
import org.horx.wdf.sys.extension.identification.UserIdentificationHandler;
import org.horx.wdf.sys.extension.identification.UserIdentificationVcodeHandler;
import org.horx.wdf.sys.support.datalog.DataLogAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class WdfImplConfig {

    @Bean
    ArgEntityAdvice agEntityAdvice() {
        return new ArgEntityAdvice();
    }

    @Bean
    DataLogAdvice dataLogAdvice() {
        return new DataLogAdvice();
    }


    @Bean
    IdLevelCodeGenerator levelCodeGenerator() {
        return new IdLevelCodeGenerator();
    }

    @Bean
    PlaintextPwdEncoder plaintextPwdEncoder() {
        return new PlaintextPwdEncoder();
    }

    @Bean
    Md5SaltPwdEncoder md5SaltPwdEncoder() {
        return new Md5SaltPwdEncoder();
    }

    @Bean
    PwdEncoderConfig pwdEncoder() {
        Map<String, PwdEncoder> pwdEncoderMap = new HashMap<>();
        pwdEncoderMap.put("01", plaintextPwdEncoder());
        pwdEncoderMap.put("02", md5SaltPwdEncoder());
        PwdEncoderConfig pwdEncoderConfig = new PwdEncoderConfig();
        pwdEncoderConfig.setPwdEncoderMap(pwdEncoderMap);
        pwdEncoderConfig.setCurrentPwdEncoderCode("02");
        return pwdEncoderConfig;
    }

    @Bean
    DataLoggerHandler dataLoggerHandler() {
        return new DataLoggerHandler();
    }

    @Bean
    DataLogDbHandler dataLogDbHandler() {
        return new DataLogDbHandler();
    }

    @Bean
    DataLogHandler dataLogHandler() {
        DataLogConfigHandler dataLogConfigHandler = new DataLogConfigHandler();
        List<DataLogHandler> list = new ArrayList<>();
        list.add(dataLoggerHandler());
        list.add(dataLogDbHandler());
        dataLogConfigHandler.setDataLogHandlerList(list);
        return dataLogConfigHandler;
    }

    @Bean
    UserIdentificationVcodeHandler userIdentificationVcodeHandler() {
        return new UserIdentificationVcodeHandler();
    }

    @Bean
    UserIdentificationConfigHandler userIdentificationConfigHandler() {
        List<UserIdentificationHandler> list = new ArrayList<>(1);
        list.add(userIdentificationVcodeHandler());
        UserIdentificationConfigHandler configHandler = new UserIdentificationConfigHandler();
        configHandler.setHandlerList(list);
        return configHandler;
    }
}
