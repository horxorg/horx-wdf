package org.horx.wdf.common.filed.value.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.horx.wdf.common.filed.value.generator.RequestAttributeGenerator;

/**
 * 从request属性赋值。
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Repeatable(RequestAttribute.List.class)
@ValueDef(generatorClass = RequestAttributeGenerator.class)
public @interface RequestAttribute {

    String key() default "";

    Class<?>[] groups() default {};

    String expr() default "";

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        RequestAttribute[] value();
    }
}
