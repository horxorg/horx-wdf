package org.horx.wdf.sys.dubbo;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.common.spring.SpringContext;
import org.horx.wdf.sys.dto.AccessLogDTO;
import org.horx.wdf.sys.extension.accesslog.AccessLogHandler;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * Dubbo服务提供者线程上下文过滤器。
 * @since 1.0
 */
@Activate
public class DubboProviderContextFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(DubboProviderContextFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        SysContextHolder threadContextHolder = SpringContext.getBean(SysContextHolder.class);
        threadContextHolder.clearRequest();

        String userId = RpcContext.getContext().getAttachment("userId");
        if (StringUtils.isNotEmpty(userId)) {
            threadContextHolder.setUserId(Long.valueOf(userId));
        }

        String accessPermissionCode = RpcContext.getContext().getAttachment("accessPermissionCode");
        if (StringUtils.isNotEmpty(accessPermissionCode)) {
            threadContextHolder.setAccessPermissionCode(accessPermissionCode);
        }

        String roleIds = RpcContext.getContext().getAttachment("roleIds");
        if (StringUtils.isNotEmpty(roleIds)) {
            Long[] arr = Arrays.asList(roleIds).toArray(new Long[0]);
            threadContextHolder.setRoleIds(arr);
        }

        String traceId = RpcContext.getContext().getAttachment("traceId");
        if (StringUtils.isNotEmpty(traceId)) {
            threadContextHolder.setTraceId(traceId);
        }

        String locale = RpcContext.getContext().getAttachment("locale");
        if (StringUtils.isNotEmpty(locale)) {
            LocaleContextHolder.setLocale(new Locale(locale));
        }

        if (StringUtils.isEmpty(traceId)) {
            return invoker.invoke(invocation);
        }

        AccessLogHandler accessLogHandler = SpringContext.getBean("accessLogHandler", AccessLogHandler.class);
        if (accessLogHandler == null) {
            return invoker.invoke(invocation);
        }

        long startTime = System.currentTimeMillis();
        EntityExtension entityExtension = SpringContext.getBean(EntityExtension.class);

        AccessLogDTO accessLogDTO = entityExtension.newEntity(AccessLogDTO.class);
        accessLogDTO.setUserId(threadContextHolder.getUserId());
        accessLogDTO.setOrgId(threadContextHolder.getUserOrgId());
        accessLogDTO.setTraceId(threadContextHolder.getTraceId());
        accessLogDTO.setEnvironment("dubbo");
        accessLogDTO.setAccessPermissionCode(threadContextHolder.getAccessPermissionCode());
        accessLogDTO.setRoleId(roleIds);

        accessLogDTO.setClientIp(RpcContext.getContext().getRemoteAddress().toString());

        accessLogDTO.setClassName(RpcContext.getContext().getUrl().getPath());
        accessLogDTO.setMethodName(RpcContext.getContext().getMethodName());
        accessLogDTO.setServerIp(RpcContext.getContext().getLocalAddress().toString());

        try {
            Result result = invoker.invoke(invocation);
            accessLogDTO.setSuccess(true);

            return result;
        } catch (Exception e) {
            logger.error("调用{}异常", RpcContext.getContext().getUrl().toString(), e);
            accessLogDTO.setSuccess(false);
            throw e;
        } finally {
            accessLogDTO.setStartTime(new Date(startTime));
            accessLogDTO.setEndTime(new Date(System.currentTimeMillis()));
            accessLogDTO.setDuration(accessLogDTO.getEndTime().getTime() - startTime);
            accessLogHandler.handle(accessLogDTO);
            threadContextHolder.clearRequest();
        }
    }
}
