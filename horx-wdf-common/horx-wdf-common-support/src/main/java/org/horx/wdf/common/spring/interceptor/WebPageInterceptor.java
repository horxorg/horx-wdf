package org.horx.wdf.common.spring.interceptor;

import org.horx.wdf.common.tools.WebTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 页面拦截器，用于设置是否加载js源文件、静态文件版本。
 * @since 1.0
 */
public class WebPageInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private WebTool webTool;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        String viewName = modelAndView.getViewName();
        if (viewName != null && !viewName.startsWith("redirect:")) {
            modelAndView.addObject("staticVer", webTool.getStaticVer());
            modelAndView.addObject("loadJsSrc", webTool.isLoadJsSrc());
        }

    }
}
