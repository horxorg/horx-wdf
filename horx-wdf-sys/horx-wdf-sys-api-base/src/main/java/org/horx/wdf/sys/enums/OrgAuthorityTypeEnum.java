package org.horx.wdf.sys.enums;

/**
 * 机构授权类型。
 * @since 1.0
 */
public enum OrgAuthorityTypeEnum {
    FORBIDDEN("forbidden", "无权限", "sys.dataAuth.forbidden"),
    ALL("all", "不限", "sys.dataAuth.all"),
    USER_ORG("userOrg", "用户所属机构", "sys.dataAuth.userOrg"),
    USER_ORG_AND_SUBS("userOrgAndSubs", "用户所属机构及所有下级机构", "sys.dataAuth.userOrgAndSubs"),
    ASSIGNED("assigned", "设定值", "sys.dataAuth.assigned");

    private String code;
    private String name;
    private String msgKey;

    OrgAuthorityTypeEnum(String code, String name, String msgKey) {
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
