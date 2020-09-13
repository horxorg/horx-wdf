package org.horx.wdf.common.filed.value.generator;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.filed.value.ValueContext;
import org.horx.wdf.common.filed.value.annotation.RequestParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * request参数赋值生成器。
 * @since 1.0
 */
public class RequestParameterGenerator implements ValueGenerator {
    private final static Logger logger = LoggerFactory.getLogger(RequestParameterGenerator.class);

    @Override
    public Object value(Annotation anno, Field field, ValueContext context) {
        if (!(anno instanceof RequestParameter)) {
            return null;
        }
        RequestParameter requestParameter = (RequestParameter)anno;
        RequestAttributes reqAttrs = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) reqAttrs).getRequest();
        String key = requestParameter.key();

        Object value = null;
        if (field.getType().isArray()) {
            if (StringUtils.isEmpty(key)) {
                key = field.getName() + "[]";
            }
            String[] arr = request.getParameterValues(key);
            value = arr;
        } else {
            if (StringUtils.isEmpty(key)) {
                key = field.getName();
            }
            String str = request.getParameter(key);
            if (str != null) {
                str = str.trim();
            }
            value = str;
        }

        value = context.convertType(value, field, requestParameter.pattern());

        if (StringUtils.isNotEmpty(requestParameter.expr())) {
           value = context.execExpression(value, requestParameter.expr());;
        }

        return value;
    }
}
