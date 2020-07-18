package org.horx.wdf.common.arg.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 从Json自动生成对象的参数注解。
 * @since 1.0
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ArgJson {

    /**
     * HttpServletRequest中的参数名。
     * @return
     */
    String value() default "";
}
