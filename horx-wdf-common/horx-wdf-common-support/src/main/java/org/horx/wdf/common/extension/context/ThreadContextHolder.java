package org.horx.wdf.common.extension.context;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.extension.session.SessionHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 线程上下文变量。
 * @param <T>
 * @since 1.0
 */
public class ThreadContextHolder<T extends ThreadContextParam> {

    protected ThreadLocal<T> requestThreadLocal = new ThreadLocal<>();

    @Autowired
    private SessionHandler sessionHandler;

    public String getUserType() {
        return null;
    }

    public Long getUserId() {
        ThreadContextParam contextParam = getContextParam();
        if (contextParam.getUserId() != null) {
            return contextParam.getUserId();
        }
        Long userId = sessionHandler.getCurrentUserId();
        contextParam.setUserId(userId);
        return userId;
    }

    public void setUserId(Long userId) {
        ThreadContextParam contextParam = getContextParam();
        contextParam.setUserId(userId);
    }

    public boolean isLoggedIn() {
        ThreadContextParam contextParam = getContextParam();
        return getUserId() != null;
    }

    public String getTraceId() {
        ThreadContextParam contextParam = getContextParam();
        return contextParam.getTraceId();
    }

    public String getTraceIdForRpc() {
        ThreadContextParam contextParam = getContextParam();
        return contextParam.getTraceId() + "." + contextParam.getNextRpcSeq();
    }

    public void setTraceId(String traceId) {
        ThreadContextParam contextParam = getContextParam();
        contextParam.setTraceId(traceId);
    }

    public Exception getEx() {
        ThreadContextParam contextParam = getContextParam();
        return contextParam.getEx();
    }

    public void setEx(Exception ex) {
        ThreadContextParam contextParam = getContextParam();
        contextParam.setEx(ex);
    }

    public Method getEnterMethod() {
        ThreadContextParam contextParam = getContextParam();
        return contextParam.getEnterMethod();
    }

    public void setEnterMethod(Method method) {
        ThreadContextParam contextParam = getContextParam();
        contextParam.setEnterMethod(method);
    }

    public void startRequest(boolean genTraceId) {
        ThreadContextParam contextParam = getContextParam();
        contextParam.setRequestStart(System.currentTimeMillis());
        if (StringUtils.isEmpty(contextParam.getTraceId()) && genTraceId) {
            contextParam.setTraceId(UUID.randomUUID().toString());
        }
    }

    public void clearRequest() {
        requestThreadLocal.remove();
    }

    public Long getRequestStart() {
        ThreadContextParam contextParam = getContextParam();
        return contextParam.getRequestStart();
    }

    protected T getContextParam() {
        T requestContext = requestThreadLocal.get();
        if (requestContext == null) {
            requestContext = newContextParam();
            requestThreadLocal.set(requestContext);
        }
        return requestContext;
    }

    protected T newContextParam() {
        return (T)(new ThreadContextParam());
    }
}
