package org.horx.wdf.sys.dto.dataauth;

import org.horx.wdf.sys.enums.DataPermissionObjTypeEnum;
import org.horx.wdf.sys.extension.dataauth.DataPermissionObj;
import org.horx.wdf.sys.extension.dataauth.DataScope;

import java.io.Serializable;

/**
 * 字典项数据权限DTO。
 * @since 1.0
 */
public class DictItemAuthDTO implements DataScope, DataPermissionObj, Serializable {
    private static final long serialVersionUID = 1L;

    private int scope;
    private Long[] authIds;
    private String[] dictItemCodes;

    public Long[] getAuthIds() {
        return authIds;
    }

    public void setAuthIds(Long[] authIds) {
        this.authIds = authIds;
    }

    public String[] getDictItemCodes() {
        return dictItemCodes;
    }

    public void setDictItemCodes(String[] dictItemCodes) {
        this.dictItemCodes = dictItemCodes;
    }

    @Override
    public String getDataPermissionObjType() {
        return DataPermissionObjTypeEnum.DICT.getCode();
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    @Override
    public int getScope() {
        return scope;
    }
}
