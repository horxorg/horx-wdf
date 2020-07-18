package org.horx.wdf.common.spring.arg;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.arg.annotation.ArgJson;
import org.horx.common.utils.JsonUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * ArgJson注解解析。
 * @since 1.0
 */
public class ArgJsonResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ArgJson.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        ArgJson argEntity = parameter.getParameterAnnotation(ArgJson.class);
        String key = argEntity.value();
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String value = request.getParameter(key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        Class<?> clazz = parameter.getParameterType();
        Object bean = JsonUtils.fromJson(value, clazz);

        return bean;
    }
}
