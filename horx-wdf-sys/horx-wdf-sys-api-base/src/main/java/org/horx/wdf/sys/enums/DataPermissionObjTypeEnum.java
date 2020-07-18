package org.horx.wdf.sys.enums;

/**
 * 数据权限对象类型。
 * @since 1.0
 */
public enum DataPermissionObjTypeEnum {

    USER("user"),
    ORG("org"),
    DICT("dict");

    private String code;

    DataPermissionObjTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
