package org.horx.wdf.sys.dubbo;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.horx.wdf.common.spring.SpringContext;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Dubbo服务消费者线程上下文过滤器。
 * @since 1.0
 */
@Activate
public class DubboConsumerContextFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        URL url = RpcContext.getContext().getUrl();
        if ("org.horx.wdf.sys.service.SessionService".equals(url.getPath())) {
            return invoker.invoke(invocation);
        }

        Map<String, String> context = new HashMap<>();

        SysContextHolder sysContextHolder = SpringContext.getBean(SysContextHolder.class);
        if (sysContextHolder.getUserId() != null) {
            context.put("userId", String.valueOf(sysContextHolder.getUserId()));
        }
        if (StringUtils.isNotEmpty(sysContextHolder.getAccessPermissionCode())) {
            context.put("accessPermissionCode", sysContextHolder.getAccessPermissionCode());
        }

        Long[] roleIds = sysContextHolder.getRoleIdsByPermissionCode();
        if (roleIds != null && roleIds.length > 0) {
            context.put("roleIds", Arrays.toString(roleIds));
        }

        if (StringUtils.isNotEmpty(sysContextHolder.getTraceId())) {
            context.put("traceId", sysContextHolder.getTraceIdForRpc());
        }

        if (LocaleContextHolder.getLocale() != null) {
            context.put("locale", LocaleContextHolder.getLocale().toString());
        }

        RpcContext.getContext().setAttachments(context);
        return invoker.invoke(invocation);
    }
}
