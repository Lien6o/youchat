package com.youchat.common.utils;


import java.time.*;
import java.time.format.DateTimeFormatter;
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
    public static LocalDateTime convertTiemstampToLocalDateTiem(long timestamp) {
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
    public static long convertLocalDateTiemToTiemstamp(LocalDateTime dateTime) {
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
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static boolean isInSpecifiedTimeInterval(long dateTime, LocalDateTime start, LocalDateTime end) {
        return isInSpecifiedTimeInterval(convertTiemstampToLocalDateTiem(dateTime), start, end);
    }

    /**
     * @Description: dateTiem 转 字符串
     * @Param: yyyy-MM-dd HH:mm:ss
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static String dateTimeFomatter(LocalDateTime dateTime, String formatter) {
        Objects.requireNonNull(dateTime);
        Objects.requireNonNull(formatter);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatter);
        return dateTime.format(dtf);
    }

    /**
     * @Description: 时间戳 转 字符串
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static String dateTimeFomatter(long dateTime, String formatter) {
        Objects.requireNonNull(dateTime);
        Objects.requireNonNull(formatter);
        return convertTiemstampToLocalDateTiem(dateTime).format(DateTimeFormatter.ofPattern(formatter));
    }

    /**
     * @Description: 时间字符串转DateTime
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/21
     */
    public static LocalDateTime parseDateTimeString(String dateTime, String formatter) {
        Objects.requireNonNull(dateTime);
        Objects.requireNonNull(formatter);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatter);
        return LocalDateTime.parse(dateTime, dtf);
    }

}

