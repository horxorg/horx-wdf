package org.horx.wdf.sys.springcloud;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.common.tools.ServerEnvironment;
import org.horx.wdf.common.tools.WebTool;
import org.horx.wdf.sys.dto.AccessLogDTO;
import org.horx.wdf.sys.extension.accesslog.AccessLogHandler;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * SpringCloud服务提供者线程上下文拦截器。
 * @since 1.0
 */
public class SpringcloudProviderContextInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SysContextHolder threadContextHolder;

    @Autowired
    private EntityExtension entityExtension;

    @Autowired(required = false)
    private AccessLogHandler accessLogHandler;

    @Autowired
    protected WebTool webTool;

    @Autowired
    protected ServerEnvironment serverEnvironment;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        threadContextHolder.clearRequest();

        String userId = request.getHeader("userId");
        if (StringUtils.isNotEmpty(userId)) {
            threadContextHolder.setUserId(Long.valueOf(userId));
        }

        String accessPermissionCode = request.getHeader("accessPermissionCode");
        if (StringUtils.isNotEmpty(accessPermissionCode)) {
            threadContextHolder.setAccessPermissionCode(accessPermissionCode);
        }

        String roleIds = request.getHeader("roleIds");
        if (StringUtils.isNotEmpty(roleIds)) {
            Long[] arr = Arrays.asList(roleIds).toArray(new Long[0]);
            threadContextHolder.setRoleIds(arr);
        }

        String traceId = request.getHeader("traceId");
        if (StringUtils.isNotEmpty(traceId)) {
            threadContextHolder.setTraceId(traceId);
        }

        String locale = request.getHeader("locale");
        if (StringUtils.isNotEmpty(locale)) {
            LocaleContextHolder.setLocale(new Locale(locale));
        }

        threadContextHolder.startRequest(false);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        String traceId = threadContextHolder.getTraceId();
        if (accessLogHandler == null || StringUtils.isEmpty(traceId)) {
            return;
        }

        AccessLogDTO accessLogDTO = entityExtension.newEntity(AccessLogDTO.class);
        accessLogDTO.setUserId(threadContextHolder.getUserId());
        accessLogDTO.setOrgId(threadContextHolder.getUserOrgId());
        accessLogDTO.setTraceId(traceId);
        accessLogDTO.setEnvironment("springcloud");
        accessLogDTO.setUrl(request.getRequestURI());
        accessLogDTO.setHttpMethod(request.getMethod());
        accessLogDTO.setAccessPermissionCode(threadContextHolder.getAccessPermissionCode());

        Long[] roleIds = threadContextHolder.getRoleIdsByPermissionCode();
        if (roleIds != null && roleIds.length > 0) {
            accessLogDTO.setRoleId(Arrays.toString(roleIds));
        }

        accessLogDTO.setClientIp(webTool.getClientIp(request));
        accessLogDTO.setServerIp(serverEnvironment.getServerIp());

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            accessLogDTO.setClassName(handlerMethod.getBeanType().getName());
            accessLogDTO.setMethodName(handlerMethod.getMethod().getName());
        }

        accessLogDTO.setStartTime(new Date(threadContextHolder.getRequestStart()));
        accessLogDTO.setEndTime(new Date(System.currentTimeMillis()));
        accessLogDTO.setDuration(accessLogDTO.getEndTime().getTime() - accessLogDTO.getStartTime().getTime());

        accessLogDTO.setSuccess(ex == null);

        accessLogHandler.handle(accessLogDTO);
    }
}
