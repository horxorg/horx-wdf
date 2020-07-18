package org.horx.wdf.sys.vo.query;

import org.horx.wdf.common.filed.value.annotation.RequestParameter;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.extension.value.annotation.DataAuth;

import java.io.Serializable;

/**
 * 角色日志查询条件VO。
 * @since 1.0
 */
public class RoleQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @RequestParameter()
    private String code;

    @RequestParameter()
    private String name;

    @RequestParameter()
    private Long[] orgIds;

    @RequestParameter()
    private Boolean subUsable;

    @RequestParameter()
    private Boolean enabled;

    @DataAuth
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
