package org.horx.wdf.sys.extension.value.annotation;

import org.horx.wdf.common.filed.value.annotation.ValueDef;
import org.horx.wdf.sys.extension.value.generator.DataAuthGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 赋值数据权限对象的注解。
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@ValueDef(generatorClass = DataAuthGenerator.class)
public @interface DataAuth {
    Class<?>[] groups() default {};
}
