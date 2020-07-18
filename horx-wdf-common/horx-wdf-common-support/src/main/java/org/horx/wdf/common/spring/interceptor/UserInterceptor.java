package org.horx.wdf.common.spring.interceptor;

import org.horx.wdf.common.exception.UnrecognizedUserException;
import org.horx.wdf.common.extension.context.ThreadContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证用户是否已登录的拦截器。
 * @since 1.0
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ThreadContextHolder threadContextHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isLoggedIn = threadContextHolder.isLoggedIn();
        if (!isLoggedIn) {
            throw new UnrecognizedUserException();
        }

        return true;
    }

}
