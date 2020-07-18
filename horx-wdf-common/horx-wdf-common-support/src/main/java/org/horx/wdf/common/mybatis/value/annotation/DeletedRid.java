package org.horx.wdf.common.mybatis.value.annotation;

import org.horx.wdf.common.filed.value.annotation.ValueDef;
import org.horx.wdf.common.mybatis.value.generator.DeletedRidGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 逻辑删除辅助字段赋值。
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@ValueDef(generatorClass = DeletedRidGenerator.class)
public @interface DeletedRid {
    Class<?>[] groups() default {};

    String defaultValue() default "0";

}
