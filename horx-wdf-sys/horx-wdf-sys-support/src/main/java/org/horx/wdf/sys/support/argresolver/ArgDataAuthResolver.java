package org.horx.wdf.sys.support.argresolver;

import org.horx.wdf.sys.annotation.ArgDataAuth;
import org.horx.wdf.sys.extension.dataauth.DataValidationTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 数据权限对象Resolver。
 * @since 1.0
 */
public class ArgDataAuthResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private DataValidationTool dataValidationTool;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ArgDataAuth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return dataValidationTool.genDataAuth(parameter.getParameterType());
    }
}
