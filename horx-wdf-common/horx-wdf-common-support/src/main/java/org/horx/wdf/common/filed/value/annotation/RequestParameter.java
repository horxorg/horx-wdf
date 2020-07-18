package org.horx.wdf.common.filed.value.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.horx.wdf.common.filed.value.generator.RequestParameterGenerator;

/**
 * 从request参数赋值。
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Repeatable(RequestParameter.List.class)
@ValueDef(generatorClass = RequestParameterGenerator.class)
public @interface RequestParameter {

    String key() default "";

    String pattern() default "";

    Class<?>[] groups() default {};

    String expr() default "";

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        RequestParameter[] value();
    }
}
