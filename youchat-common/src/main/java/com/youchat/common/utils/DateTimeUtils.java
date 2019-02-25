package com.youchat.common.utils;


import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

/**
 * @program: youchat-common
 * @description:
 * @author: lien6o
 * @create: 2018-08-21 10:24
 **/
public class DateTimeUtils {
    private DateTimeUtils() {
    }

    /**
     * 获取两个日期相差的时间间隔
     * @param before
     * @param after
     * @return
     */
    public static long getPeriodDays(LocalDate before, LocalDate after) {
        return before.toEpochDay() - after.toEpochDay();
    }

    /**
     * 是否是相邻的两天
     * @param before
     * @param after
     * @return
     */
    public static boolean isAdjacent(LocalDate before, LocalDate after) {
        if (before.isBefore(after)) {
            return getPeriodDays(before, after) == 1;
        }
        return false;
    }

    /**
     * 查询两个LocalDate的相差月数 l2 - l1 不建议使用：还是和日期相关。4.22 - 3.10 不足30天为0
     */
    public static long getPeriodMonths(LocalDate l1, LocalDate l2) {
        return l1.until(l2, ChronoUnit.MONTHS);
    }


    public static Date convertLocalDateTime2Date(LocalDateTime dateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = dateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }


    public static long getYesterdayMaxTime() {
        return DateTimeUtils.convertLocalDateTime2Timestamp(DateTimeUtils.getSpecifiedMaxDateTime(LocalDateTime.now().plusDays(-1).toLocalDate()));
    }

    public static long getYesterdayMinTime() {
        return DateTimeUtils.convertLocalDateTime2Timestamp(DateTimeUtils.getSpecifiedMinDateTime(LocalDateTime.now().plusDays(-1).toLocalDate()));
    }

    public static long getTodayMaxTime() {
        return DateTimeUtils.convertLocalDateTime2Timestamp(DateTimeUtils.getNowMaxDateTime());
    }

    public static long getTodayMinTime() {
        return DateTimeUtils.convertLocalDateTime2Timestamp(DateTimeUtils.getNowMinDateTime());
    }

    public static boolean isInToday(long time) {
        return isInSpecifiedTimeInterval(time, getTodayMinTime(), getTodayMaxTime());
    }

    public static boolean isInYesterday(long time) {
        return isInSpecifiedTimeInterval(time, getYesterdayMinTime(), getYesterdayMaxTime());
    }

    /**
     * 获取截止到当天23点59分59秒9999毫秒 的秒数 用于缓存
     *
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static int getDuringSecondsToNowMaxDateTime() {
        return (int) Duration.between(LocalDateTime.now(), DateTimeUtils.getNowMaxDateTime()).getSeconds();
    }

    /**
     * @Description: 获取当天23点59分59秒9999毫秒
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static LocalDateTime getNowMaxDateTime() {
        return getSpecifiedMaxDateTime(LocalDate.now());
    }

    /**
     * @Description: 获取当天0点0分0秒0000毫秒
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static LocalDateTime getNowMinDateTime() {
        return getSpecifiedMinDateTime(LocalDate.now());
    }

    /**
     * @Description: 获取指定日期的时间最大值23点59分59秒9999毫秒
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static LocalDateTime getSpecifiedMaxDateTime(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        return LocalDateTime.of(localDate, LocalTime.MAX);
    }

    /**
     * @Description: 获取指定时间的时间最小值0点0分0秒0000毫秒
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static LocalDateTime getSpecifiedMinDateTime(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    /**
     * @Description: 时间戳转DateTime
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static LocalDateTime convertTimestamp2LocalDateTime(long timestamp) {
        Objects.requireNonNull(timestamp);
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * @Description: DateTime转时间戳
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static long convertLocalDateTime2Timestamp(LocalDateTime dateTime) {
        Objects.requireNonNull(dateTime);
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * @Description: 是否在指定时间区间
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static boolean isInSpecifiedTimeInterval(LocalDateTime dateTime, LocalDateTime start, LocalDateTime end) {
        Objects.requireNonNull(dateTime);
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
        if (start.isAfter(end)) {
            throw new IllegalArgumentException();
        }
        return dateTime.isAfter(start) && dateTime.isBefore(end);
    }

    /**
     * @Description: 是否在指定时间区间
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static boolean isInSpecifiedTimeInterval(long dateTime, long start, long end) {
        if (start >= end) {
            throw new IllegalArgumentException();
        }
        return dateTime > start && dateTime < end;
    }

    /**
     * @Description: 是否在指定时间区间
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static boolean isInSpecifiedTimeInterval(long dateTime, LocalDateTime start, LocalDateTime end) {
        return isInSpecifiedTimeInterval(convertTimestamp2LocalDateTime(dateTime), start, end);
    }

    /**
     * @Description: dateTime 转 字符串
     * @Param: yyyy-MM-dd HH:mm:ss
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static String dateTimeFormatter(LocalDateTime dateTime, String formatter) {
        Objects.requireNonNull(dateTime, "dateTime was null");
        Objects.requireNonNull(formatter, "formatter was null");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatter);
        return dateTime.format(dtf);
    }

    /**
     * @Description: 时间戳 转 字符串
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static String dateTimeFormatter(long dateTime, String formatter) {
        Objects.requireNonNull(dateTime, "dateTime was null");
        Objects.requireNonNull(formatter, "formatter was null");
        return convertTimestamp2LocalDateTime(dateTime).format(DateTimeFormatter.ofPattern(formatter));
    }

    /**
     * @Description: 时间字符串转DateTime
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static LocalDateTime parseDateTimeString(String dateTime, String formatter) {
        Objects.requireNonNull(dateTime, "dateTime was null");
        Objects.requireNonNull(formatter, "formatter was null");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatter);
        return LocalDateTime.parse(dateTime, dtf);
    }

}


