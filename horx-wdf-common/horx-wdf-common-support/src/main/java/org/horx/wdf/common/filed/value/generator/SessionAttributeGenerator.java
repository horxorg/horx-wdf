package org.horx.wdf.common.filed.value.generator;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.filed.value.ValueContext;
import org.horx.wdf.common.filed.value.annotation.SessionAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * session赋值生成器。
 * @since 1.0
 */
public class SessionAttributeGenerator implements ValueGenerator {
    @Override
    public Object value(Annotation anno, Field field, ValueContext context) {
        if (!(anno instanceof SessionAttribute)) {
            return null;
        }
        SessionAttribute sessionAttribute = (SessionAttribute)anno;
        RequestAttributes reqAttrs = RequestContextHolder.currentRequestAttributes();
        HttpSession session = ((ServletRequestAttributes) reqAttrs).getRequest().getSession(false);
        if (session == null) {
            return null;
        }

        String key = sessionAttribute.key();
        if (StringUtils.isEmpty(key)) {
            key = field.getName();
        }

        Object value = session.getAttribute(key);
        value = context.convertType(value, field, null);

        if (StringUtils.isNotEmpty(sessionAttribute.expr())) {
            value = context.execExpression(value, sessionAttribute.expr());;
        }

        return value;
    }
}
