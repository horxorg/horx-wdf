package org.horx.wdf.sys.enums;

/**
 * 用户授权类型。
 * @since 1.0
 */
public enum UserAuthorityTypeEnum {
    FORBIDDEN("forbidden", "无权限", "sys.dataAuth.forbidden"),
    ALL("all", "不限", "sys.dataAuth.all"),
    CURRENT_USER("currentUser", "当前登录用户", "sys.dataAuth.currentUser");

    private String code;
    private String name;
    private String msgKey;

    UserAuthorityTypeEnum(String code, String name, String msgKey) {
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
