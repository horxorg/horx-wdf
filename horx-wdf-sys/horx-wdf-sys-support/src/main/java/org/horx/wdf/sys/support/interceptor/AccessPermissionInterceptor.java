package org.horx.wdf.sys.support.interceptor;

import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.exception.PermissionDeniedException;
import org.horx.wdf.sys.service.AccessPermissionService;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 访问权限拦截器。
 * @since 1.0
 */
public class AccessPermissionInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessPermissionInterceptor.class);

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private AccessPermissionService accessPermissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            String permissionCode = null;
            AccessPermission permission = handlerMethod.getMethodAnnotation(AccessPermission.class);
            if (permission == null) {
                permissionCode = request.getParameter("accessPermissionCode");
                if (permissionCode == null) {
                    Class<?> cls = handlerMethod.getBeanType();
                    permission = cls.getAnnotation(AccessPermission.class);
                }
            }

            if (permissionCode == null && permission != null) {
                permissionCode = permission.value();
            }

            if (permissionCode == null) {
                return true;
            }

            Long userId = sysContextHolder.getUserId();
            boolean isAllowed = accessPermissionService.isPermissionAllowedForUser(userId, permissionCode);
            if (!isAllowed) {
                LOGGER.error("用户{}无访问权限{}", userId, permissionCode);
                throw new PermissionDeniedException();
            }

            sysContextHolder.setAccessPermissionCode(permissionCode);
        }
        return true;
    }
}
