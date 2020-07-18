package org.horx.wdf.common.spring;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * msg文本。
 * @since 1.0
 */
public class LocaleMessageSource extends ReloadableResourceBundleMessageSource {

    /**
     * 获取多语言msg。
     * @param locale
     * @return
     */
    public Map<String, String> getMergedMsg(Locale locale) {
        ReloadableResourceBundleMessageSource.PropertiesHolder propertiesHolder = super.getMergedProperties(locale);
        Properties properties = propertiesHolder.getProperties();
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (key == null || value == null) {
                continue;
            }
            result.put(key.toString(), value.toString());
        }

        return result;
    }
}
