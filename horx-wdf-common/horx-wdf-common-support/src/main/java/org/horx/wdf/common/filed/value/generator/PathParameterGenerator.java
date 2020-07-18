package org.horx.wdf.common.filed.value.generator;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.filed.value.ValueContext;
import org.horx.wdf.common.filed.value.annotation.PathParameter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * url变量赋值生成器。
 * @since 1.0
 */
public class PathParameterGenerator implements ValueGenerator {
    @Override
    public Object value(Annotation anno, Field field, ValueContext context) {
        if (!(anno instanceof PathParameter)) {
            return null;
        }
        PathParameter pathParameter = (PathParameter)anno;
        RequestAttributes reqAttrs = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) reqAttrs).getRequest();
        Object obj = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (obj == null || !(obj instanceof Map)) {
            return null;
        }

        Map map = (Map)obj;
        String key = pathParameter.key();
        if (StringUtils.isEmpty(key)) {
            key = field.getName();
        }
        Object value = map.get(key);

        value = context.convertType(value, field, pathParameter.pattern());

        if (StringUtils.isNotEmpty(pathParameter.expr())) {
            value = context.execExpression(value, pathParameter.expr());;
        }

        return value;
    }
}
