package org.horx.wdf.sys.support.datalog;

import org.horx.wdf.common.enums.OperationTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据操作日志注解。
 * @since 1.0
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataLog {

    String bizType() default "";

    String bizName() default "";

    OperationTypeEnum operationType() default OperationTypeEnum.OTHER;

    String desc() default "";

    int[] paramIndex() default {};
}
