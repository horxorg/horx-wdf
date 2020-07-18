package org.horx.wdf.common.filed.value.annotation;

import org.horx.wdf.common.filed.value.generator.CurrentUserIdGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 获取当前用户ID的注解。
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@ValueDef(generatorClass = CurrentUserIdGenerator.class)
public @interface CurrentUserId {
    Class<?>[] groups() default {};
}
