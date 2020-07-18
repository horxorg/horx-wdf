package org.horx.wdf.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * property配置。
 * @since 1.0
 */
public class PropertyPlaceholder extends PropertyPlaceholderConfigurer {
    protected Map<String,String> propertyMap = new HashMap<String, String>();;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);

        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            propertyMap.put(keyStr, value);
        }
    }

    /**
     * 获取property值。
     * @param name
     * @return
     */
    public String getProperty(String name) {
        return propertyMap.get(name);
    }
}
