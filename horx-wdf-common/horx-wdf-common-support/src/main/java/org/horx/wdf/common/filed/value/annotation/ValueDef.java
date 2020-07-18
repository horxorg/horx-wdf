package org.horx.wdf.common.filed.value.annotation;

import org.horx.wdf.common.filed.value.generator.ValueGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 赋值规则定义。
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface ValueDef {

    String beanId() default "";

    Class<? extends ValueGenerator> generatorClass();

    boolean isSingleton() default true;
}
