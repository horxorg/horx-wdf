package org.horx.wdf.common.extension.session.support;

import org.horx.wdf.common.config.CommonConfig;
import org.horx.wdf.common.extension.session.SessionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * 使用HttpSession的会话Handler。
 * @since 1.0
 */
public class HttpSessionHandler implements SessionHandler {


    @Autowired
    private CommonConfig commonConfig;

    @Override
    public void login(Long userId) {
        RequestAttributes reqAttrs = RequestContextHolder.currentRequestAttributes();
        reqAttrs.setAttribute(commonConfig.getUserIdSessionKey(), userId, RequestAttributes.SCOPE_SESSION);
    }

    @Override
    public void logout() {
        RequestAttributes reqAttrs = RequestContextHolder.getRequestAttributes();
        if (reqAttrs == null) {
            return;
        }
        HttpSession session = ((ServletRequestAttributes) reqAttrs).getRequest().getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @Override
    public Long getCurrentUserId() {
        RequestAttributes reqAttrs = RequestContextHolder.getRequestAttributes();
        if (reqAttrs == null) {
            return null;
        }
        HttpSession session = ((ServletRequestAttributes) reqAttrs).getRequest().getSession(false);
        if (session == null) {
            return null;
        }
        Object obj = session.getAttribute(commonConfig.getUserIdSessionKey());
        Long userId = (obj == null) ? null : (Long)obj;
        return userId;
    }
}
