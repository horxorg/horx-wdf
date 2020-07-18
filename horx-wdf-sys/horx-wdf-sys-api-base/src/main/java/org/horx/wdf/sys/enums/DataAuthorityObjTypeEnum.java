package org.horx.wdf.sys.enums;

/**
 * 数据授权对象类型。
 * @since 1.0
 */
public enum DataAuthorityObjTypeEnum {
    DEFAULT("default"),
    USER("user"),
    ROLE("role"),
    ADMIN_ROLE("adminRole");

    private String code;

    DataAuthorityObjTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
