package org.horx.wdf.common.filed.value.annotation;

import org.horx.wdf.common.filed.value.generator.NowGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 赋值当前时间的注解。
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@ValueDef(generatorClass = NowGenerator.class)
public @interface Now {
    Class<?>[] groups() default {};

    String expr() default "";
}
