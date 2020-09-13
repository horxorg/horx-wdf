package org.horx.wdf.common.support.filter;

import org.horx.wdf.common.extension.context.ThreadContextHolder;
import org.horx.wdf.common.spring.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 线程上下文过滤器。
 * @since 1.0
 */
public class RequestContextFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestContextFilter.class);

    @Autowired
    protected ThreadContextHolder threadContextHolder;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (threadContextHolder == null) {
            threadContextHolder = SpringContext.getBean(ThreadContextHolder.class);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        threadContextHolder.clearRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes((HttpServletRequest)servletRequest, (HttpServletResponse)servletResponse);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        threadContextHolder.startRequest(true);
        logger.info("请求开始");
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            threadContextHolder.setEx(e);
            servletRequest.getRequestDispatcher("/err").forward(servletRequest, servletResponse);
        }

        logger.info("请求结束");
        threadContextHolder.clearRequest();
        RequestContextHolder.resetRequestAttributes();
    }

    @Override
    public void destroy() {
        threadContextHolder = null;
    }

}
