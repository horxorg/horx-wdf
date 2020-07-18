package org.horx.wdf.sys.extension.sort.annotation;

import org.horx.wdf.common.filed.sort.annotation.CustomSortDef;
import org.horx.wdf.sys.extension.sort.generator.DictSortGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典排序注解。
 * @since 1.0
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@CustomSortDef(generatorClass = DictSortGenerator.class)
public @interface DictSort {

    String dictCode() default "";
}
