package org.horx.wdf.common.filed.value.generator;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.filed.value.ValueContext;
import org.horx.wdf.common.filed.value.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * request属性赋值生成器。
 * @since 1.0
 */
public class RequestAttributeGenerator implements ValueGenerator {
    @Override
    public Object value(Annotation anno, Field field, ValueContext context) {
        if (!(anno instanceof RequestAttribute)) {
            return null;
        }
        RequestAttribute requestAttribute = (RequestAttribute)anno;
        RequestAttributes reqAttrs = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) reqAttrs).getRequest();

        String key = requestAttribute.key();
        if (StringUtils.isEmpty(key)) {
            key = field.getName();
        }

        Object value = request.getAttribute(key);
        value = context.convertType(value, field, null);

        if (StringUtils.isNotEmpty(requestAttribute.expr())) {
            value = context.execExpression(value, requestAttribute.expr());;
        }

        return value;
    }
}
