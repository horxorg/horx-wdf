package org.horx.wdf.common.enums;

/**
 * 排序枚举。
 * @since 1.0
 */
public enum SortEnum {
    ASC, DESC;

    /**
     * 从字符串解析为枚举类型。
     * @param order
     * @return
     */
    public static SortEnum parse(String order) {
        if (order == null || order.length() == 0 || "ASC".equalsIgnoreCase(order)) {
            return ASC;
        } else if ("DESC".equalsIgnoreCase(order)) {
            return DESC;
        }
        return null;
    }
}
