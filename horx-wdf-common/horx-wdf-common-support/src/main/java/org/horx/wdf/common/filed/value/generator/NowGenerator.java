package org.horx.wdf.common.filed.value.generator;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.filed.value.ValueContext;
import org.horx.wdf.common.filed.value.annotation.Now;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * 当前日期生成器。
 * @since 1.0
 */
public class NowGenerator implements ValueGenerator {

    @Override
    public Object value(Annotation anno, Field field, ValueContext context) {
        if (!(anno instanceof Now)) {
            return null;
        }

        Object obj = context.getAttribute("now");
        if (obj != null) {
            return obj;
        }
        obj = new Date();

        Now now = (Now)anno;
        if (StringUtils.isNotEmpty(now.expr())) {
            obj = context.execExpression(obj, now.expr());;
        }

        context.setAttribute("now", obj);
        return obj;
    }
}
