package org.horx.wdf.sys.dto.dataauth;

import org.horx.wdf.sys.enums.DataPermissionObjTypeEnum;
import org.horx.wdf.sys.extension.dataauth.DataPermissionObj;
import org.horx.wdf.sys.extension.dataauth.DataScope;

import java.io.Serializable;

/**
 * 用户数据权限DTO。
 * @since 1.0
 */
public class UserAuthDTO implements DataScope, DataPermissionObj, Serializable {
    private static final long serialVersionUID = 1L;

    private int scope;
    private Long[] authIds;
    private Long[] userIds;

    public Long[] getAuthIds() {
        return authIds;
    }

    public void setAuthIds(Long[] authIds) {
        this.authIds = authIds;
    }

    public Long[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Long[] userIds) {
        this.userIds = userIds;
    }

    @Override
    public String getDataPermissionObjType() {
        return DataPermissionObjTypeEnum.USER.getCode();
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    @Override
    public int getScope() {
        return scope;
    }
}
