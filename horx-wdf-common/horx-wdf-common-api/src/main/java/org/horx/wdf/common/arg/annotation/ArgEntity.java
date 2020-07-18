package org.horx.wdf.common.arg.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动生成实体对象的参数注解。
 * @since 1.0
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ArgEntity {

    /**
     * 是否记录哪些属性已赋值。
     * @return
     */
    boolean recordAssignment() default false;

    /**
     * 对所操作属性的分组。
     * @return
     */
    Class<?>[] groups() default {};

    /**
     * 是否校验。
     * @return
     */
    boolean validate() default true;

    /**
     * 是否创建操作。
     * @return
     */
    boolean create() default false;

    /**
     * 是否更新操作。
     * @return
     */
    boolean modify() default false;
}
