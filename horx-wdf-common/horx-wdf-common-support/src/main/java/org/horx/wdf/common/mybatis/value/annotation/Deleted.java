package org.horx.wdf.common.mybatis.value.annotation;

import org.horx.wdf.common.filed.value.annotation.ValueDef;
import org.horx.wdf.common.filed.value.generator.NowGenerator;
import org.horx.wdf.common.mybatis.value.generator.DeletedGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 逻辑删除赋值。
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@ValueDef(generatorClass = DeletedGenerator.class)
public @interface Deleted {
    Class<?>[] groups() default {};

    String notDeletedValue() default "0";

    String deletedValue() default "1";
}
