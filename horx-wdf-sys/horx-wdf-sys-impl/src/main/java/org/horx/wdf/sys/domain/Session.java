package org.horx.wdf.sys.domain;

import org.horx.wdf.common.jdbc.annotation.Sequence;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 会话。
 * @since 1.0
 */
@Table(name = "wdf_session")
public class Session implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Sequence(name = "seq_wdf_session")
    private Long id;

    @Column
    private String sessionKey;

    @Column
    private Date createTime;

    @Column
    private Date lastAccessTime;

    @Column
    private Integer inactiveInterval;

    @Column
    private Date expiredTime;

    @Column
    private String clientIp;

    @Column
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
