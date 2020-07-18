package org.horx.wdf.sys.enums;

/**
 * 字典授权类型。
 * @since 1.0
 */
public enum DictAuthorityTypeEnum {

    FORBIDDEN("forbidden", "无权限", "sys.dataAuth.forbidden"),
    ALL("all", "不限", "sys.dataAuth.all"),
    ASSIGNED("assigned", "设定值", "sys.dataAuth.assigned");

    private String code;
    private String name;
    private String msgKey;

    DictAuthorityTypeEnum(String code, String name, String msgKey) {
        this.code = code;
        this.name = name;
        this.msgKey = msgKey;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getMsgKey() {
        return msgKey;
    }
}
