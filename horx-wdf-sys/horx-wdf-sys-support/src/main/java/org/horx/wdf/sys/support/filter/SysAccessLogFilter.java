package org.horx.wdf.sys.support.filter;

import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.common.extension.context.ThreadContextHolder;
import org.horx.wdf.common.spring.SpringContext;
import org.horx.wdf.common.tools.ServerEnvironment;
import org.horx.wdf.common.tools.WebTool;
import org.horx.wdf.sys.extension.accesslog.AccessLogHandler;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.dto.AccessLogDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * 访问日志过滤器。
 * @since 1.0
 */
public class SysAccessLogFilter implements Filter {


    @Autowired
    protected SysContextHolder threadContextHolder;

    @Autowired
    protected WebTool webTool;

    @Autowired
    protected EntityExtension entityExtension;

    @Autowired
    protected AccessLogHandler accessLogHandler;

    @Autowired
    protected ServerEnvironment serverEnvironment;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (threadContextHolder == null) {
            threadContextHolder = SpringContext.getBean(SysContextHolder.class);
        }
        if (webTool == null) {
            webTool = SpringContext.getBean(WebTool.class);
        }
        if (entityExtension == null) {
            entityExtension = SpringContext.getBean(EntityExtension.class);
        }
        if (accessLogHandler == null) {
            accessLogHandler = SpringContext.getBean("accessLogHandler", AccessLogHandler.class);
        }
        if (serverEnvironment == null) {
            serverEnvironment = SpringContext.getBean(ServerEnvironment.class);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);

        if (accessLogHandler == null) {
            return;
        }

        HttpServletRequest request = (HttpServletRequest)servletRequest;

        AccessLogDTO accessLogDTO = entityExtension.newEntity(AccessLogDTO.class);
        accessLogDTO.setUserId(threadContextHolder.getUserId());
        accessLogDTO.setTraceId(threadContextHolder.getTraceId());
        accessLogDTO.setEnvironment("web");
        accessLogDTO.setUrl(request.getRequestURI());
        accessLogDTO.setHttpMethod(request.getMethod());

        accessLogDTO.setOrgId(threadContextHolder.getUserOrgId());
        accessLogDTO.setAccessPermissionCode(threadContextHolder.getAccessPermissionCode());
        Long[] roleIds = threadContextHolder.getRoleIdsByPermissionCode();
        if (roleIds != null && roleIds.length > 0) {
            accessLogDTO.setRoleId(Arrays.toString(roleIds));
        }

        accessLogDTO.setClientIp(webTool.getClientIp(request));
        accessLogDTO.setUserAgent(request.getHeader("user-agent"));

        Method method = threadContextHolder.getEnterMethod();
        if (method != null) {
            accessLogDTO.setClassName(method.getDeclaringClass().getName());
            accessLogDTO.setMethodName(method.getName());
        }

        accessLogDTO.setServerIp(serverEnvironment.getServerIp());

        accessLogDTO.setStartTime(new Date(threadContextHolder.getRequestStart()));
        accessLogDTO.setEndTime(new Date(System.currentTimeMillis()));
        accessLogDTO.setDuration(accessLogDTO.getEndTime().getTime() - accessLogDTO.getStartTime().getTime());
        accessLogDTO.setSuccess(threadContextHolder.getEx() == null);
        processAccessLog(accessLogDTO);
        accessLogHandler.handle(accessLogDTO);
    }

    @Override
    public void destroy() {
        threadContextHolder = null;
        webTool = null;
        entityExtension = null;
        accessLogHandler = null;
    }

    protected void processAccessLog(AccessLogDTO accessLogDTO) {

    }
}
