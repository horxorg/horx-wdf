package org.horx.wdf.common.spring.arg;

import org.horx.wdf.common.tools.EntityTool;
import org.horx.wdf.common.arg.annotation.ArgEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * ArgEntit注解解析。
 * @since 1.0
 */
public class ArgEntityResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private EntityTool entityTool;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ArgEntity.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        ArgEntity argEntity = parameter.getParameterAnnotation(ArgEntity.class);
        boolean recordAssignment = (argEntity.modify()) ? true : argEntity.recordAssignment();
        Class<?>[] groups = entityTool.toGroups(argEntity);

        Object bean = entityTool.newEntity(parameter.getParameterType(), recordAssignment, groups, argEntity.validate());

        return bean;
    }
}
