package com.youchat.common.stream;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

/**
 * @program: youchat-common
 * @description:
 * @author: lien6o
 * @create: 2018-08-20 19:32
 **/
public class TestDateTime {
    /**
     * @Description:
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/20
     */
    public static void main(String[] args) {
        localDateAndLocalTime();
        localDateTime();
        withDateTime();

        LocalDate date = LocalDate.now();
        TemporalAdjuster nextWorkingDay = nextWorkDay();
        date = date.with(nextWorkingDay);
        System.out.println("next work day " + date);

        temporal();

        ZoneOffset of = ZoneOffset.of("+8");
        System.out.println(ZoneOffset.systemDefault().getId());
        Long milliSecond = LocalDateTime.now().toInstant(of).toEpochMilli();
        // 获取秒数
        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        // 获取毫秒数
        Long milliSeconds = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        // 获取下个月最后24点时间 2018-08-31T23:59:59.999999999
        LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);


        Instant createTime1 =Instant.ofEpochSecond(System.currentTimeMillis()/1000);

        LocalDateTime createTime = LocalDateTime.ofEpochSecond( System.currentTimeMillis()/1000, 0, ZoneOffset.ofHours(8));
        System.out.println(createTime);
    }

    /**
     * @Description: 下一个工作日
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/20
     */
    private static TemporalAdjuster nextWorkDay() {
        return TemporalAdjusters.ofDateAdjuster(
                temporal -> {
                    DayOfWeek dow =
                            DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                    int dayToAdd = 1;
                    if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
                    if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
                    return temporal.plus(dayToAdd, ChronoUnit.DAYS);
                });
    }

    private static void withDateTime() {
        LocalDateTime timeMax = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        System.out.println(timeMax.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        LocalDate date1 = LocalDate.of(2014, 3, 18);
        LocalDate date2 = date1.withYear(2011);
        LocalDate date3 = date2.withDayOfMonth(25);
        LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 9);
    }

    /**
     * TemporalAdjuster 时间调整员
     * dayOfWeekInMonth  创建一个新的日期，它的值为同一个月中每一周的第几天
     * firstDayOfMonth  创建一个新的日期，它的值为当月的第一天
     * firstDayOfNextMonth  创建一个新的日期，它的值为下月的第一天
     * firstDayOfNextYear  创建一个新的日期，它的值为明年的第一天
     * firstDayOfYear  创建一个新的日期，它的值为当年的第一天
     * firstInMonth  创建一个新的日期，它的值为同一个月中，第一个符合星期几要求的值
     * lastDayOfMonth  创建一个新的日期，它的值为当月的最后一天
     * lastDayOfNextMonth  创建一个新的日期，它的值为下月的最后一天
     * lastDayOfNextYear  创建一个新的日期，它的值为明年的最后一天
     * lastDayOfYear  创建一个新的日期，它的值为今年的最后一天
     * lastInMonth  创建一个新的日期，它的值为同一个月中，最后一个符合星期几要求的值
     * next/previous
     * 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星
     * 期几要求的日期
     * nextOrSame/previousOrSame
     * 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星
     * 期几要求的日期，如果该日期已经符合要求，直接返回该对象
     */
    private static void temporal() {
        LocalDate date = LocalDate.of(2015, 2, 3);
        System.out.println(date.with(TemporalAdjusters.lastDayOfYear()));
    }

    /**
     * @Description: 合并了 日期和时间
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/20
     */
    private static void localDateTime() {
        System.out.println(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        LocalDateTime dateTime = LocalDateTime.of(2013, Month.APRIL, 1, 12, 12);
        int dayOfMonth = dateTime.getDayOfMonth();
        // 不要通过更改dayOfMonth返回不同时间，因为这个时间有范围！！
        System.out.println(dayOfMonth);
    }

    /**
     * @Description: LocalDate:日期
     * LocalTime:时间
     * @Param:
     * @return:
     * @Author: lien6o
     * @Date: 2018/08/20
     */
    private static void localDateAndLocalTime() {
        LocalDate date = LocalDate.of(2018, 2, 3);
        System.out.println(date.getYear());
        Month month = date.getMonth();
        System.out.println(month);
        System.out.println(date.getDayOfMonth());
        System.out.println(date.lengthOfMonth());
        // 是闰年?
        System.out.println(Optional.of(date.isLeapYear()));
        LocalTime localTime = LocalTime.of(12, 12, 33);
        System.out.println(localTime.getSecond());
        System.out.println(localTime.getHour());
        System.out.println(LocalTime.parse("12:45:20"));
        // 默认格式：The ISO date formatter that formats or parses a date without an
        //  offset, such as '2011-12-03'.
        System.out.println(LocalDate.parse("2010-06-06"));
        //  System.out.println(LocalDate.parse("2015/01/21", DateTimeFormatter.ofPattern("yyyy/mm/dd")));
    }
}
