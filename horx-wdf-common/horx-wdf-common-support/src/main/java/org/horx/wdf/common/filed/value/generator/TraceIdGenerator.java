package org.horx.wdf.common.filed.value.generator;

import org.horx.wdf.common.extension.context.ThreadContextHolder;
import org.horx.wdf.common.filed.value.ValueContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 跟踪ID生成器。
 * @since 1.0
 */
@Component
public class TraceIdGenerator implements ValueGenerator {

    @Autowired
    private ThreadContextHolder threadContextHolder;

    @Override
    public Object value(Annotation anno, Field field, ValueContext context) {
        return threadContextHolder.getTraceId();
    }
}
