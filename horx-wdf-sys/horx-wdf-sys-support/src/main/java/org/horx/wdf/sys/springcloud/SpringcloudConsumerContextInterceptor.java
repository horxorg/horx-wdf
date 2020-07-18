package org.horx.wdf.sys.springcloud;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;

/**
 * SpringCloud服务消费者线程上下文拦截器。
 * @since 1.0
 */
public class SpringcloudConsumerContextInterceptor implements RequestInterceptor {

    @Autowired
    private SysContextHolder sysContextHolder;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (requestTemplate.url().startsWith("/sys/session/")) {
            return;
        }

        if (sysContextHolder.getUserId() != null) {
            requestTemplate.header("userId", String.valueOf(sysContextHolder.getUserId()));
        }

        if (StringUtils.isNotEmpty(sysContextHolder.getAccessPermissionCode())) {
            requestTemplate.header("accessPermissionCode", sysContextHolder.getAccessPermissionCode());
        }

        Long[] roleIds = sysContextHolder.getRoleIdsByPermissionCode();
        if (roleIds != null && roleIds.length > 0) {
            requestTemplate.header("roleIds", Arrays.toString(roleIds));
        }

        if (StringUtils.isNotEmpty(sysContextHolder.getTraceId())) {
            requestTemplate.header("traceId", sysContextHolder.getTraceIdForRpc());
        }

        if (LocaleContextHolder.getLocale() != null) {
            requestTemplate.header("locale", LocaleContextHolder.getLocale().toString());
        }
    }
}
