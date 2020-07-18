package org.horx.wdf.sys.enums;

/**
 * 数据验证范围。
 * @since 1.0
 */
public enum DataValidationScopeEnum {
    FORBIDDEN(0),
    PART(1),
    ALL(2);

    private int code;

    DataValidationScopeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
