package org.horx.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date工具类。
 * @since 1.0
 */
public final class DateUtils {
    private static final long milliSecondsOfDay = 24 * 60 * 60 * 1000;

    private DateUtils() {}

    /**
     * 以给定的格式把字符串解析为Date。
     * @param dateString
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parse(String dateString, String pattern) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.parse(dateString);
    }

    /**
     * 以给定的格式把字符串解析为Date。
     * 可解析的格式有：
     *     yyyyMMdd、yyyy-MM-dd、yyyy/MM/dd
     *     yyyyMMddHH、yyyyMMdd HH、yyyy-MM-dd HH、yyyy/MM/dd HH
     *     yyyyMMddHHmm、yyyyMMdd HHmm、yyyyMMdd HH:mm、yyyy-MM-dd HHmm、yyyy-MM-dd HH:mm、yyyy/MM/dd HHmm、yyyy/MM/dd HH:mm
     *     yyyyMMddHHmmss、yyyyMMdd HHmmss、yyyyMMdd HH:mm:ss、yyyy-MM-dd HHmmss、yyyy-MM-dd HH:mm:ss、yyyy/MM/dd HHmmss、yyyy/MM/dd HH:mm:ss
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date parse(String dateString) throws ParseException {
        if (dateString == null || "".equals(dateString)) throw new ParseException("解析失败", 0);
        dateString = dateString.trim();

        String format = null;
        int idx = dateString.indexOf(' ');
        if (idx > 0) {
            String dateStr = dateString.substring(0, idx);
            String timeStr = dateString.substring(idx + 1);
            if (dateStr.indexOf('-') > 0) {
                format = "yyyy-MM-dd";
            } else if (dateStr.indexOf('/') > 0) {
                format = "yyyy/MM/dd";
            } else {
                format = "yyyyMMdd";
            }
            if (timeStr != null && !"".equals(timeStr)) {
                if (timeStr.indexOf(':') > 0) {
                    String[] arr = timeStr.split(":");
                    int len = arr.length;
                    switch(len) {
                        case 2:
                            format += " HH:mm";
                            break;
                        case 3:
                            format += " HH:mm:ss";
                            break;
                        default:
                            format = null;
                    }
                }
                else {
                    int len = timeStr.length();
                    switch(len) {
                        case 2:
                            format += " HH";
                            break;
                        case 4:
                            format += " HHmm";
                            break;
                        case 6:
                            format += " HHmmss";
                            break;
                        default:
                            format = null;
                    }
                }
            }
        }
        else {
            if (dateString.indexOf('-') > 0) {
                format = "yyyy-MM-dd";
            } else if (dateString.indexOf('/') > 0) {
                format = "yyyy/MM/dd";
            } else {
                int len = dateString.length();
                switch(len) {
                    case 8:
                        format = "yyyyMMdd";
                        break;
                    case 10:
                        format = "yyyyMMddHH";
                        break;
                    case 12:
                        format = "yyyyMMddHHmm";
                        break;
                    case 14:
                        format = "yyyyMMddHHmmss";
                        break;
                }
            }
        }
        if (format == null) throw new ParseException("解析失败", 0);

        return parse(dateString, format);
    }

    /**
     * 把Date格式化成给定格式的字符串。
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 把日期字符串以一种格式变形为另一种格式的字符串。
     * @param dateString
     * @param oldPattern
     * @param newPattern
     * @return
     * @throws ParseException
     */
    public static String transform(String dateString, String oldPattern, String newPattern)
            throws ParseException{
        Date date = parse(dateString, oldPattern);
        return format(date, newPattern);
    }

    /**
     * 在Date实例上增加天数。
     * @param date
     * @param days
     * @return
     */
    public static Date addDay(Date date, int days) {
        if (date == null) {
            return null;
        }
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.add(Calendar.DAY_OF_MONTH, days);
        return cld.getTime();
    }

    /**
     * 在Date实例上增加月份。
     * @param date
     * @param month 如果为负，则是减少月份。
     * @return
     */
    public static Date addMonth(Date date, int month) {
        if (date == null) {
            return null;
        }
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.add(Calendar.MONTH, month);
        return cld.getTime();
    }

    /**
     * 在Date实例上增加年份。
     * @param date
     * @param year 如果为负，则是减少年份。
     * @return
     */
    public static Date addYear(Date date, int year) {
        if (date == null) {
            return null;
        }
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.add(Calendar.YEAR, year);
        return cld.getTime();
    }

    /**
     * 得到当前时刻的字符串表示。
     * @param format
     * @return
     */
    public static String getNow(String format) {
        Date now = new Date();
        return format(now, format);
    }

    /**
     * 得到当前时刻的字符串表示。默认格式为<code>yyyy-MM-dd</code>。
     * @return
     */
    public static String getNow() {
        Date now = new Date();
        return format(now, "yyyy-MM-dd");
    }

    /**
     * 获取当前周第一天。
     * @return
     */
    public static Date getCurrWeekStartDate(boolean isMondayFirst) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(new Date());
        int d = cld.get(Calendar.DAY_OF_WEEK);
        if (isMondayFirst) {
            d -= 2;
        } else {
            d -= 1;
        }
        cld.add(Calendar.DAY_OF_MONTH, -d);
        return cld.getTime();
    }

    /**
     * 获取当前周最后一天。
     * @param isMondayFirst
     * @return
     */
    public static Date getCurrWeekEndDate(boolean isMondayFirst) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(new Date());
        int d = 7 - cld.get(Calendar.DAY_OF_WEEK);
        if (isMondayFirst) {
            d += 1;
        }

        cld.add(Calendar.DAY_OF_MONTH, d);
        return cld.getTime();
    }

    /**
     * 获取当前月第一天。
     * @return
     */
    public static Date getCurrMonthStartDate() {
        Calendar cld = Calendar.getInstance();
        cld.set(Calendar.DAY_OF_MONTH, 1);
        return cld.getTime();
    }

    /**
     * 获取当前月最后一天。
     * @return
     */
    public static Date getCurrMonthEndDate() {
        Calendar cld = Calendar.getInstance();
        cld.add(Calendar.MONTH, 1);
        cld.set(Calendar.DAY_OF_MONTH, 1);
        cld.add(Calendar.DAY_OF_MONTH, -1);
        return cld.getTime();
    }
    /**
     * 获取下月第一天。
     * @return
     */
    public static Date getNextMonthStartDate() {
        Calendar cld = Calendar.getInstance();
        cld.add(Calendar.MONTH, +1);  //+1 和直接写1是一样的
        cld.set(Calendar.DAY_OF_MONTH, 1);
        return cld.getTime();
    }

    /**
     * 获取下月最后一天。
     * @return
     */
    public static Date getNextMonthEndDate() {
        Calendar cld = Calendar.getInstance();
        cld.add(Calendar.MONTH,+2);
        cld.set(Calendar.DAY_OF_MONTH,1);
        cld.add(Calendar.DAY_OF_MONTH, -1);
        return cld.getTime();
    }

    /**
     * 获取昨天
     * @return
     */
    public static Date getYesterday() {
        Calendar cld = Calendar.getInstance();
        cld.add(Calendar.DAY_OF_MONTH, -1);
        return cld.getTime();
    }

    /**
     * 获取明天
     * @return
     */
    public static Date getTomorrow() {
        Calendar cld = Calendar.getInstance();
        cld.add(Calendar.DAY_OF_MONTH, 1);
        return cld.getTime();
    }

    /**
     * 获取上月第一天。
     * @return
     */
    public static Date getLastMonthStartDate() {
        Calendar cld = Calendar.getInstance();
        cld.add(Calendar.MONTH, -1);
        cld.set(Calendar.DAY_OF_MONTH, 1);
        return cld.getTime();
    }
    /**
     * 获取上月最后一天。
     * @return
     */
    public static Date getLastMonthEndDate() {
        Calendar cld = Calendar.getInstance();
        cld.set(Calendar.DAY_OF_MONTH, +1);
        cld.add(Calendar.DAY_OF_MONTH, -1);
        return cld.getTime();
    }

    /**
     * 获取下一天。
     * @param d
     * @return
     */
    public static Date getNextDay(Date d) {
        if (d == null) return null;
        Calendar cld = Calendar.getInstance();
        cld.setTime(d);
        cld.add(Calendar.DAY_OF_MONTH, 1);
        return cld.getTime();
    }

    /**
     * 获取两个日期间相隔的自然天数。
     * 如2013-01-30与2013-01-30间相隔1天，2013-01-30与2013-01-31间相隔2天。
     * @param startDate
     * @param endDate
     * @return
     */
    public static int diffDays(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) return 0;
        long start = startDate.getTime() / milliSecondsOfDay;
        long end = getNextDay(endDate).getTime() / milliSecondsOfDay;
        Long days = end - start;
        return days.intValue();
    }

    /**
     * 清空时间，只保留日期数据。
     * @param d
     * @return
     */
    public static Date emptyTime(Date d) {
        if (d == null) return null;
        Calendar cld = Calendar.getInstance();
        cld.setTime(d);
        cld.set(Calendar.HOUR_OF_DAY, 0);
        cld.set(Calendar.MINUTE, 0);
        cld.set(Calendar.SECOND, 0);
        cld.set(Calendar.MILLISECOND, 0);
        return cld.getTime();
    }
}
