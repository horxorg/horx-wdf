package org.horx.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Duration工具类。
 * @since 1.0.3
 */
public final class DurationUtils {
    private DurationUtils() {}

    /**
     * Duration解析。
     * 支持以下格式：
     * 1、使用的标准 ISO-8601 格式
     * 2、一种更易读的格式，值和单位在一起（例如 10s 表示 10 秒）
     *    ns 为纳秒
     *    us 为微秒
     *    ms 为毫秒
     *    s 为秒
     *    m 为分钟
     *    h 为小时
     *    d 为天
     * 3、没有单位，纯数字，解析为毫秒
     * @param duration
     * @return
     */
    public static Duration parse(String duration) {
        if (StringUtils.isEmpty(duration)) {
            return null;
        }
        String str = duration.trim().toUpperCase();
        if (str.contains("P")) {
            return Duration.parse(duration);
        }
        Duration result;
        if (str.endsWith("NS")) {
            result = Duration.ofNanos(Long.valueOf(str.substring(0, str.length() - 2)));
        } else if (str.endsWith("US")) {
            result = Duration.of(Long.valueOf(str.substring(0, str.length() - 2)), ChronoUnit.MICROS);
        } else if (str.endsWith("MS")) {
            result =  Duration.ofMillis(Long.valueOf(str.substring(0, str.length() - 2)));
        } else if (str.endsWith("S")) {
            result = Duration.ofSeconds(Long.valueOf(str.substring(0, str.length() - 1)));
        } else if (str.endsWith("M")) {
            result =  Duration.ofMinutes(Long.valueOf(str.substring(0, str.length() - 1)));
        } else if (str.endsWith("H")) {
            result = Duration.ofHours(Long.valueOf(str.substring(0, str.length() - 1)));
        } else if (str.endsWith("D")) {
            result = Duration.ofDays(Long.valueOf(str.substring(0, str.length() - 1)));
        } else if (StringUtils.isNumeric(str)) {
            result =  Duration.ofMillis(Long.valueOf(str));
        } else {
            throw new RuntimeException("不识别的Duration格式：" + duration);
        }
        return result;
    }
}
