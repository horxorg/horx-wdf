package org.horx.wdf.sys.domain;

import org.horx.wdf.common.jdbc.annotation.Sequence;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 访问日志。
 * @since 1.0
 */
@Table(name = "wdf_access_log")
public class AccessLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Sequence(name = "seq_wdf_access_log")
    private Long id;

    @Column
    private Long userId;

    @Transient
    @Column
    private String userName;

    @Column
    private Long orgId;

    @Transient
    @Column
    private String orgName;

    @Column
    private String traceId;

    @Column
    private String environment;

    @Column
    private String url;

    @Column
    private String httpMethod;

    @Column(name = "permission_code")
    private String accessPermissionCode;

    @Column
    private String roleId;

    @Column
    private String clientIp;

    @Column
    private String userAgent;

    @Column
    private String className;

    @Column
    private String methodName;

    @Column
    private String serverIp;

    @Column
    private Date startTime;

    @Column
    private Date endTime;

    @Column
    private Long duration;

    @Column(name = "is_success")
    private Boolean success;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getAccessPermissionCode() {
        return accessPermissionCode;
    }

    public void setAccessPermissionCode(String accessPermissionCode) {
        this.accessPermissionCode = accessPermissionCode;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
