package org.horx.wdf.sys.dto.wrapper;

import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;

import java.io.Serializable;

/**
 * 系统管理批量操作DTO。
 * @since 1.0
 */
public class BatchWithSysAuthDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long[] ids;

    private SysDataAuthDTO sysDataAuth;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public SysDataAuthDTO getSysDataAuth() {
        return sysDataAuth;
    }

    public void setSysDataAuth(SysDataAuthDTO sysDataAuth) {
        this.sysDataAuth = sysDataAuth;
    }

}
