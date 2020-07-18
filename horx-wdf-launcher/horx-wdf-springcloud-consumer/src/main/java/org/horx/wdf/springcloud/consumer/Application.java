package org.horx.wdf.springcloud.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

@SpringBootApplication
@ComponentScan({"org.horx.wdf"})
@EnableSpringHttpSession
@ServletComponentScan(basePackages = {"org.horx.wdf.springcloud.consumer.config"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"org.horx.wdf.sys.service"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
