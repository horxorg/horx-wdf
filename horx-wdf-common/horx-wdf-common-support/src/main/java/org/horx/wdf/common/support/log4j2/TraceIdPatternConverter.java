package org.horx.wdf.common.support.log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.horx.wdf.common.extension.context.ThreadContextHolder;
import org.horx.wdf.common.spring.SpringContext;

/**
 * 跟踪ID的log4j2自定义PatternConverter。
 * <code>@ConverterKeys({"traceId"})</code>中的traceId是在pattern中的参数名。
 * @since 1.0
 */
@Plugin(name = "TraceIdPatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"traceId"})
public class TraceIdPatternConverter extends LogEventPatternConverter {
    private static final TraceIdPatternConverter INSTANCE = new TraceIdPatternConverter();

    public static TraceIdPatternConverter newInstance(final String[] options) {
        return INSTANCE;
    }

    private ThreadContextHolder threadContextHolder;

    private TraceIdPatternConverter(){
        super("TraceId", "traceId");
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        if (threadContextHolder == null) {
            ThreadContextHolder threadContextHolder = SpringContext.getBean(ThreadContextHolder.class);
            if (threadContextHolder == null) {
                return;
            }
            this.threadContextHolder = threadContextHolder;
        }

        String traceId = threadContextHolder.getTraceId();
        toAppendTo.append((traceId == null) ? "" : traceId);
    }
}
