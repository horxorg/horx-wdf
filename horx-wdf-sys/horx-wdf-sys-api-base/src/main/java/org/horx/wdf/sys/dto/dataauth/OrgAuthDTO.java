package org.horx.wdf.sys.dto.dataauth;

import org.horx.wdf.sys.enums.DataPermissionObjTypeEnum;
import org.horx.wdf.sys.extension.dataauth.DataPermissionObj;
import org.horx.wdf.sys.extension.dataauth.DataScope;

import java.io.Serializable;

/**
 * 机构数据权限DTO。
 * @since 1.0
 */
public class OrgAuthDTO implements DataScope, DataPermissionObj, Serializable {
    private static final long serialVersionUID = 1L;

    private int scope;
    private Long[] authIds;
    private Long[] orgIds;
    private Long[] orgIdsWithSub;

    public Long[] getAuthIds() {
        return authIds;
    }

    public void setAuthIds(Long[] authIds) {
        this.authIds = authIds;
    }

    public Long[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(Long[] orgIds) {
        this.orgIds = orgIds;
    }

    public Long[] getOrgIdsWithSub() {
        return orgIdsWithSub;
    }

    public void setOrgIdsWithSub(Long[] orgIdsWithSub) {
        this.orgIdsWithSub = orgIdsWithSub;
    }

    @Override
    public String getDataPermissionObjType() {
        return DataPermissionObjTypeEnum.ORG.getCode();
    }

    @Override
    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

}
