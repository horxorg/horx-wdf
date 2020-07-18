package org.horx.wdf.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring上下文。
 * @since 1.0
 */
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    /**
     * 获取bean。
     * @param beanId
     * @return
     */
    public static Object getBean(String beanId) {
        if (applicationContext == null) {
            return null;
        }
        if (applicationContext.containsBean(beanId)) {
            return applicationContext.getBean(beanId);
        }
        return null;
    }

    /**
     * 获取bean。
     * @param beanId
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanId, Class<T> beanClass) {
        if (applicationContext == null) {
            return null;
        }
        if (applicationContext.containsBean(beanId)) {
            Object obj = applicationContext.getBean(beanId);
            if (beanClass != null) {
                if (beanClass.isAssignableFrom(obj.getClass())) {
                    return (T)obj;
                }
            }
            return null;
        }
        return null;
    }

    /**
     * 获取bean。
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> beanClass) {
        if (applicationContext == null) {
            return null;
        }

        try {
            return applicationContext.getBean(beanClass);
        } catch (NoSuchBeanDefinitionException e) {

        }
        return null;
    }

    /**
     * 获取ApplicationContext。
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
