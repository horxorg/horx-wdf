package org.horx.wdf.sys.dto.query;

import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;

import java.io.Serializable;

/**
 * 角色查询条件DTO。
 * @since 1.0
 */
public class RoleQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private Long[] orgIds;

    private Boolean subUsable;

    private Boolean enabled;

    private SysDataAuthDTO sysDataAuth;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(Long[] orgIds) {
        this.orgIds = orgIds;
    }

    public Boolean getSubUsable() {
        return subUsable;
    }

    public void setSubUsable(Boolean subUsable) {
        this.subUsable = subUsable;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public SysDataAuthDTO getSysDataAuth() {
        return sysDataAuth;
    }

    public void setSysDataAuth(SysDataAuthDTO sysDataAuth) {
        this.sysDataAuth = sysDataAuth;
    }
}
