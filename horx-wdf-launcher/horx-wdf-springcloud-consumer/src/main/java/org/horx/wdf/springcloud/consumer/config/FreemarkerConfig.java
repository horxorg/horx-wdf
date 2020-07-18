package org.horx.wdf.springcloud.consumer.config;

import org.horx.wdf.sys.view.freemarker.tag.MenuAllowedTag;
import org.horx.wdf.sys.view.freemarker.tag.PermissionAllowedTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FreemarkerConfig {

    @Autowired
    protected freemarker.template.Configuration configuration;

    @PostConstruct
    public void setSharedVariable() {
        configuration.setSharedVariable("wdf_menuAllowed", menuAllowedTag());
        configuration.setSharedVariable("wdf_permissionAllowed", permissionAllowedTag());
    }

    @Bean
    MenuAllowedTag menuAllowedTag() {
        return new MenuAllowedTag();
    }

    @Bean
    PermissionAllowedTag permissionAllowedTag() {
        return new PermissionAllowedTag();
    }
}
