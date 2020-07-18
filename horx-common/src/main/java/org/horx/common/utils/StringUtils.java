package org.horx.common.utils;

/**
 * 字符串工具类。
 * @since 1.0
 */
public class StringUtils {

    /**
     * 驼峰字符串转为下划线连接的字符串。
     * @param str 驼峰字符串，如<code>userId</code>。
     * @return 下划线连接的字符串,如<code>user_id</code>。
     */
    public static String camelToUnderLine(String str) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (Character.isUpperCase(chr)) {
                builder.append("_").append(Character.toLowerCase(chr));
            } else {
                builder.append(chr);
            }
        }
        return builder.toString();
    }
}
