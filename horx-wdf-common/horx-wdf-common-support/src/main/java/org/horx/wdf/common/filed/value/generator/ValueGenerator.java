package org.horx.wdf.common.filed.value.generator;

import org.horx.wdf.common.filed.value.ValueContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 赋值生成器接口。
 * @since 1.0
 */
public interface ValueGenerator {

    /**
     * 生成值。
     * @param anno 注解。
     * @param field 属性。
     * @param context 赋值上下文。
     * @return 生成的值。
     */
    Object value(Annotation anno, Field field, ValueContext context);
}
