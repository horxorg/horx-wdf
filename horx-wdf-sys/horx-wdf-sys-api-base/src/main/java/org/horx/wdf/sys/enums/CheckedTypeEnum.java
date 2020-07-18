package org.horx.wdf.sys.enums;

/**
 * 节点选中类型枚举。
 * @since 1.0
 */
public enum CheckedTypeEnum {

    /**
     * 未选中。
     */
    UNCHECKED(0),
    /**
     * 本节点选中。
     */
    CHECKED(1),
    /**
     * 本节点及下级节点选中。
     */
    CHECKED_ALL(2);

    private int code;

    CheckedTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
