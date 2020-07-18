package org.horx.wdf.common.filed.sort.annotation;

import org.horx.wdf.common.filed.sort.generator.FieldSortGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段排序注解。
 * @since 1.0
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@CustomSortDef(generatorClass = FieldSortGenerator.class)
public @interface FieldSort {

    /**
     * 升序排序字段定义。
     * @return
     */
    String asc() default  "";

    /**
     * 降序排序字段定义。
     * @return
     */
    String desc() default "";

    /**
     * 排序需要join的sql。
     * @return
     */
    String join() default "";
}
