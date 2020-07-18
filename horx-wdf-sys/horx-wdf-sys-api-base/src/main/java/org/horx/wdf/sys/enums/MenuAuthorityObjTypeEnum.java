package org.horx.wdf.sys.enums;

/**
 * 菜单授权对象类型。
 * @since 1.0
 */
public enum MenuAuthorityObjTypeEnum {
    ROLE("role"),
    ADMIN_ROLE("adminRole");

    private String code;

    MenuAuthorityObjTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static MenuAuthorityObjTypeEnum getByCode(String code) {
        MenuAuthorityObjTypeEnum[] arr = MenuAuthorityObjTypeEnum.values();
        for (MenuAuthorityObjTypeEnum item : arr) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
