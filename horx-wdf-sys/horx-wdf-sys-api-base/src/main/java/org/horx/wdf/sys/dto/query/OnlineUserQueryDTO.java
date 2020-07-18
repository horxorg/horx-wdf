package org.horx.wdf.sys.dto.query;

import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;

import java.io.Serializable;

/**
 * 在线用户查询条件DTO。
 * @since 1.0
 */
public class OnlineUserQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private String username;

    private Long[] orgIds;

    private SysDataAuthDTO sysDataAuth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(Long[] orgIds) {
        this.orgIds = orgIds;
    }

    public SysDataAuthDTO getSysDataAuth() {
        return sysDataAuth;
    }

    public void setSysDataAuth(SysDataAuthDTO sysDataAuth) {
        this.sysDataAuth = sysDataAuth;
    }
}
