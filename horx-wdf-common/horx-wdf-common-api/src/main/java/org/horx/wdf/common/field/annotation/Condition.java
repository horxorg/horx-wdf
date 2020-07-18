package org.horx.wdf.common.field.annotation;

import org.horx.wdf.common.enums.ComparisonOperatorEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 属性查询条件。
 * @since 1.0
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {

    String field() default "";

    ComparisonOperatorEnum operator() default ComparisonOperatorEnum.EQUAL;
}
