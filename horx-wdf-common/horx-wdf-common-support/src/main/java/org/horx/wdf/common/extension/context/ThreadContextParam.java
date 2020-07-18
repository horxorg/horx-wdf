package org.horx.wdf.common.extension.context;

import java.lang.reflect.Method;

/**
 * 线程上下文变量。
 * @since 1.0
 */
public class ThreadContextParam {

    private String userType;

    private Long userId;

    private String traceId;

    private int rpcSeq;

    private Long requestStart;

    private Exception ex;

    private Method enterMethod;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public int getNextRpcSeq() {
        return rpcSeq++;
    }

    public Long getRequestStart() {
        return requestStart;
    }

    public void setRequestStart(Long requestStart) {
        this.requestStart = requestStart;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }

    public Method getEnterMethod() {
        return enterMethod;
    }

    public void setEnterMethod(Method enterMethod) {
        this.enterMethod = enterMethod;
    }
}
