package org.horx.wdf.sys.dto.dataauth;

import org.horx.wdf.sys.annotation.DataPermission;
import org.horx.wdf.sys.enums.DataValidationScopeEnum;
import org.horx.wdf.sys.extension.dataauth.DataScope;

import java.io.Serializable;

/**
 * 系统管理数据权限DTO。
 * @since 1.0
 */
public class SysDataAuthDTO implements DataScope, Serializable {
    private static final long serialVersionUID = 1L;

    @DataPermission("sys.org")
    private OrgAuthDTO orgAuth;

    public OrgAuthDTO getOrgAuth() {
        return orgAuth;
    }

    public void setOrgAuth(OrgAuthDTO orgAuth) {
        this.orgAuth = orgAuth;
    }

    @Override
    public int getScope() {
        return (orgAuth == null) ? DataValidationScopeEnum.ALL.getCode() : orgAuth.getScope();
    }

}
