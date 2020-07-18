package org.horx.wdf.sys.extension.value.annotation;

import org.horx.wdf.common.filed.value.annotation.ValueDef;
import org.horx.wdf.sys.extension.value.generator.CurrentUserOrgIdGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 赋值当前登录用户所属机构ID的注解。
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@ValueDef(generatorClass = CurrentUserOrgIdGenerator.class)
public @interface CurrentUserOrgId {
    Class<?>[] groups() default {};
}
