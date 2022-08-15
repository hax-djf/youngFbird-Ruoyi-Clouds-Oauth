package com.flybirds.common.util.date;

import com.flybirds.common.constant.Constant;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author :米饭饭一族
 * @create :2021-06-23 11:06:00
 * @description :
 */
public class LocalDateTimeUtils extends cn.hutool.core.date.LocalDateTimeUtil{

    public static final String YYYY = Constant.DateFormat.YYYY;

    public static final String YYYY_MM = Constant.DateFormat.YYYY_MM;

    public static final String YYYY_MM_DD = Constant.DateFormat.YYYY_MM_DD;

    public static final String YYYYMMDDHHMMSS = Constant.DateFormat.YYYYMMDDHHMMSS;

    public static final String YYYY_MM_DD_HH_MM_SS = Constant.DateFormat.YYYY_MM_DD_HH_MM_SS;

    public static final String GMT = Constant.DateFormat.TIME_ZONE_DEFAULT_GMT;

    /**
     * 获取指定日期所属周的周一的日期
     * @param localDate
     * @return
     */
    public static LocalDateTime getMondayForThisWeek(LocalDate localDate) {
        LocalDateTime monday = LocalDateTime.of(localDate, LocalTime.MIN).with(DayOfWeek.MONDAY);
        return monday;
    }

    /**
     * 获取指定日期所属周的周日的日期
     * @param localDate
     * @return
     */
    public static LocalDateTime getSundayForThisWeek(LocalDate localDate) {
        LocalDateTime sunday = LocalDateTime.of(localDate, LocalTime.MIN).with(DayOfWeek.SUNDAY);
        return sunday;
    }

    /**
     * 获取指定日期所属周的下周一的日期
     * @param localDate
     * @return
     */
    public static LocalDateTime getMondayForNextWeek(LocalDate localDate) {
        LocalDateTime monday = LocalDateTime.of(localDate, LocalTime.MIN).plusWeeks(1).with(DayOfWeek.MONDAY);
        return monday;
    }

    /**
     * 获取指定日期所属周的下周日的日期
     * @param localDate
     * @return
     */
    public static LocalDateTime getSundayForNextWeek(LocalDate localDate) {
        LocalDateTime sunday = LocalDateTime.of(localDate, LocalTime.MIN).plusWeeks(1).with(DayOfWeek.SUNDAY);
        return sunday;
    }

    /**
     * 指定格式为"yyyy-MM-dd HH:mm:ss"的字符串时间转化为LocalDateTime类型
     * @param dateStr
     * @return
     */
    public static LocalDateTime getLocalDateTimeFromString(String dateStr) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return localDateTime;
    }

    /**
     * LocalDateTime类型转化为格式为"yyyy-MM-dd HH:mm:ss"的字符串时间类型
     * @param localDateTime
     * @return
     */
    public static String getStringFromLocalDateTime(LocalDateTime localDateTime) {
        String localDateTimeStr = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return localDateTimeStr;
    }

    /**
     * Date类型时间转化为LocalDateTime类型
     * @param date
     * @return
     */
    public static LocalDateTime getLocalDateTimeFromDate(Date date) {
        LocalDateTime localDateTime = date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
        return localDateTime;
    }

    /**
     * LocalDateTime类型转化为Date类型时间
     * @param localDateTime
     * @return
     */
    public static Date getDateFromLocalDateTime(LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
        return date;
    }

    /**
     * 获取指定时间的00:00:00
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getLocalDateTimeForBegin(LocalDateTime localDateTime) {
        LocalDateTime begin = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
        return begin;
    }

    /**
     * 获取指定时间的23:59:59
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getLocalDateTimeForEnd(LocalDateTime localDateTime) {
        LocalDateTime end = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
        return end;
    }

    /**
     * 时间戳(毫秒)转化为LocalDateTime格式
     * @param timestamp
     * @return
     */
    public static LocalDateTime getLocalDateTimeFromTimestamp(Long timestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(timestamp/1000, 0, ZoneOffset.ofHours(8));
        return localDateTime;
    }

    /**
     * LocalDateTime格式转化为时间戳(毫秒)
     * @param localDateTime
     * @return
     */
    public static Long getTimestampFromLocalDateTime(LocalDateTime localDateTime) {
        Long timestamp = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return timestamp;
    }
}
