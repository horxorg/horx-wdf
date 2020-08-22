package org.horx.wdf.common.spring.arg;

import org.horx.wdf.common.tools.WebTool;
import org.horx.wdf.common.entity.PaginationParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页参数解析。
 * @since 1.0
 */
public class PaginationParamResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private WebTool webTool;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(PaginationParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
        return webTool.genPaginationParam(request);
    }
}
