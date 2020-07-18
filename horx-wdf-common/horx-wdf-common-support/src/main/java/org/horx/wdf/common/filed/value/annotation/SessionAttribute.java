package org.horx.wdf.common.filed.value.annotation;

import org.horx.wdf.common.filed.value.generator.SessionAttributeGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 从session赋值。
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Repeatable(SessionAttribute.List.class)
@ValueDef(generatorClass = SessionAttributeGenerator.class)
public @interface SessionAttribute {

    String key() default "";

    Class<?>[] groups() default {};

    String expr() default "";

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        SessionAttribute[] value();
    }
}
