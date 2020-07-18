package org.horx.wdf.sys.domain;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * 在线用户。
 * @since 1.0
 */
public class OnlineUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    private Long id;

    @Column
    private Long userId;

    @Column
    private String username;

    @Column
    private String name;

    @Column
    private String orgName;

    @Column
    private String clientIp;

    @Column
    private Date createTime;

    @Column
    private Date lastAccessTime;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
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
}
