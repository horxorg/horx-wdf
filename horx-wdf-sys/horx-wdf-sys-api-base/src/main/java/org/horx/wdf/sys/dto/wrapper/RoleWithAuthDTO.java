package org.horx.wdf.sys.dto.wrapper;

import org.horx.wdf.sys.dto.RoleDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;

import java.io.Serializable;

/**
 * 角色操作DTO。
 * @since 1.0
 */
public class RoleWithAuthDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private RoleDTO role;

    private SysDataAuthDTO sysDataAuth;

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public SysDataAuthDTO getSysDataAuth() {
        return sysDataAuth;
    }

    public void setSysDataAuth(SysDataAuthDTO sysDataAuth) {
        this.sysDataAuth = sysDataAuth;
    }
}
