package org.horx.wdf.sys.extension.dataauth;

/**
 * 数据验证范围接口。
 * @since 1.0
 */
public interface DataScope {

    /**
     * 数据验证范围。
     * @return 0无权限，1可能部分权限，2不限制。可以用DataValidationScopeEnum表示。
     */
    int getScope();
}
