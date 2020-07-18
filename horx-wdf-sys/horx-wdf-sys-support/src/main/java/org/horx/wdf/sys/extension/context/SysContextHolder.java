package org.horx.wdf.sys.extension.context;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.extension.context.ThreadContextHolder;
import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统管理线上下文Holder。
 * @since 1.0
 */
public class SysContextHolder extends ThreadContextHolder<SysContextParam> {

    @Autowired
    private SessionService sessionService;

    @Override
    public String getUserType() {
        return "sys";
    }

    public UserDTO getUser() {
        SysContextParam contextParam = getContextParam();
        if (contextParam.getUser() != null) {
            return contextParam.getUser();
        }

        Long userId = getUserId();
        if (userId == null) {
            return null;
        }

        UserDTO user = sessionService.getUserById(userId);
        contextParam.setUser(user);
        return user;
    }

    public Long getUserOrgId() {
        UserDTO user = getUser();
        return (user == null) ? null : user.getOrgId();
    }

    public OrgDTO getUserOrg() {
        SysContextParam contextParam = getContextParam();
        if (contextParam.getOrg() != null) {
            return contextParam.getOrg();
        }

        UserDTO user = getUser();
        if (user == null || user.getOrgId() == null) {
            return null;
        }

        OrgDTO org = sessionService.getOrgById(user.getOrgId());
        contextParam.setOrg(org);
        return org;
    }

    public Long[] getRoleIdsByPermissionCode() {
        Long userId = getUserId();
        if (userId == null) {
            return new Long[0];
        }

        SysContextParam contextParam = getContextParam();
        if (contextParam.getRoleIds() != null) {
            return contextParam.getRoleIds();
        }

        Long[] roleIds = null;
        String permissionCode = getPermissionCode();
        if (StringUtils.isNotEmpty(permissionCode)) {
            roleIds = sessionService.getRoleIdsByPermissionCode(userId, permissionCode);
            contextParam.setRoleIds(roleIds);
        }

        if (roleIds == null) {
            roleIds = new Long[0];
        }

        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        SysContextParam contextParam = getContextParam();
        contextParam.setRoleIds(roleIds);
    }

    public String getAccessPermissionCode() {
        SysContextParam contextParam = getContextParam();
        return contextParam.getAccessPermissionCode();
    }

    public void setAccessPermissionCode(String accessPermissionCode) {
        SysContextParam contextParam = getContextParam();
        contextParam.setAccessPermissionCode(accessPermissionCode);
    }

    @Override
    protected SysContextParam newContextParam() {
        return new SysContextParam();
    }

    private String getPermissionCode() {
        SysContextParam contextParam = getContextParam();
        String accessPermissionCode = contextParam.getAccessPermissionCode();
        if (StringUtils.isNotEmpty(accessPermissionCode)) {
            return accessPermissionCode;
        }
        return null;
    }
}
