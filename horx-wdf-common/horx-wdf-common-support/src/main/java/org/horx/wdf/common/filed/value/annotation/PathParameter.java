package org.horx.wdf.common.filed.value.annotation;

import org.horx.wdf.common.filed.value.generator.PathParameterGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 从url变量赋值。
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Repeatable(PathParameter.List.class)
@ValueDef(generatorClass = PathParameterGenerator.class)
public @interface PathParameter {
    String key() default "";

    String pattern() default "";

    Class<?>[] groups() default {};

    String expr() default "";

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        PathParameter[] value();
    }
}
