package org.horx.wdf.sys.dto.wrapper;

import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;

import java.io.Serializable;

/**
 * 机构操作DTO。
 * @since 1.0
 */
public class OrgWithAuthDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private OrgDTO org;

    private SysDataAuthDTO sysDataAuth;

    public OrgDTO getOrg() {
        return org;
    }

    public void setOrg(OrgDTO org) {
        this.org = org;
    }

    public SysDataAuthDTO getSysDataAuth() {
        return sysDataAuth;
    }

    public void setSysDataAuth(SysDataAuthDTO sysDataAuth) {
        this.sysDataAuth = sysDataAuth;
    }
}
