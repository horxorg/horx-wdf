package org.horx.wdf.common.spring.interceptor;

import org.horx.wdf.common.extension.context.ThreadContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 进入方法的拦截器。
 * @since 1.0
 */
public class EnterMethodInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ThreadContextHolder threadContextHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            threadContextHolder.setEnterMethod(handlerMethod.getMethod());
        }

        return true;
    }

}
