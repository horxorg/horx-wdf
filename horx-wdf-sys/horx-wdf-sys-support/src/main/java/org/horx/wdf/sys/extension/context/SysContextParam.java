package org.horx.wdf.sys.extension.context;

import org.horx.wdf.common.extension.context.ThreadContextParam;
import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.dto.UserDTO;

/**
 * 系统管理线上下文变量。
 * @since 1.0
 */
public class SysContextParam extends ThreadContextParam {

    private UserDTO user;

    private OrgDTO org;

    private String accessPermissionCode;

    private Long[] roleIds;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public OrgDTO getOrg() {
        return org;
    }

    public void setOrg(OrgDTO org) {
        this.org = org;
    }

    public String getAccessPermissionCode() {
        return accessPermissionCode;
    }

    public void setAccessPermissionCode(String accessPermissionCode) {
        this.accessPermissionCode = accessPermissionCode;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }
}
