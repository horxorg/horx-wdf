package org.horx.wdf.sys.dto.dataauth;

import org.horx.wdf.sys.annotation.DataPermission;
import org.horx.wdf.sys.enums.DataValidationScopeEnum;
import org.horx.wdf.sys.extension.dataauth.DataScope;

import java.io.Serializable;

/**
 * 字典数据权限DTO。
 * @since 1.0
 */
public class DictDataAuthDTO implements DataScope, Serializable {
    private static final long serialVersionUID = 1L;

    @DataPermission("sys.dict.bizType")
    private DictItemAuthDTO bizTypeAuth;

    public DictItemAuthDTO getBizTypeAuth() {
        return bizTypeAuth;
    }

    public void setBizTypeAuth(DictItemAuthDTO bizTypeAuth) {
        this.bizTypeAuth = bizTypeAuth;
    }

    @Override
    public int getScope() {
        return (bizTypeAuth == null) ? DataValidationScopeEnum.ALL.getCode() : bizTypeAuth.getScope();
    }
}
