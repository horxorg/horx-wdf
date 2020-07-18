package org.horx.wdf.common.filed.sort.annotation;

import org.horx.wdf.common.filed.sort.generator.CustomSortGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义排序注解。
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface CustomSortDef {

    /**
     * 自定义排序实现类的bean id。
     * @return
     */
    String beanId() default "";

    /**
     * 自定义排序实现类。
     * @return
     */
    Class<? extends CustomSortGenerator> generatorClass();

    /**
     * 自定义排序实现类是否单例。
     * @return
     */
    boolean isSingleton() default true;
}
