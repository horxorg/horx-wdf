package org.horx.wdf.common.extension.session;

import java.io.Serializable;
import java.util.Date;

/**
 * 会话DTO。
 * @since 1.0
 */
public class SessionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String sessionKey;

    private Date createTime;

    private Date lastAccessTime;

    private Integer inactiveInterval;

    private Date expiredTime;

    private String clientIp;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Integer getInactiveInterval() {
        return inactiveInterval;
    }

    public void setInactiveInterval(Integer inactiveInterval) {
        this.inactiveInterval = inactiveInterval;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
