package com.youchat.common.utils;

import com.google.common.base.Splitter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 按照时间控制开关
 *
 *
 * daily:每日
 * 主要是从中学会 datetime = data + time
 */
@Slf4j
public class DateTimeController {
    private static final String DAILY_RULE = "daily";
    private static final String HMS_FORMATTER = "HH:mm:ss";

    private DateTimeController() {
    }


    public static boolean isInConfigTime(String config) {
        return isInConfigTime(config, LocalDateTime.now());
    }

    private static boolean isInConfigTime(String config, LocalDateTime argDateTime) {
        try {
            DateTimeRule dateTimeRule = dateTimeConfigParser(config);
            LocalTime startTime = dateTimeRule.getStartTime();
            LocalTime endTime = dateTimeRule.getEndTime();
            LocalDate localDate = argDateTime.toLocalDate();
            if (DAILY_RULE.equalsIgnoreCase(dateTimeRule.getTimeRule())) {

                if (endTime.isAfter(startTime)) {
                    return argDateTime.isAfter(LocalDateTime.of(localDate, startTime))
                            && argDateTime.isBefore(LocalDateTime.of(localDate, endTime));
                }
                // 跨日期
                if (startTime.isAfter(endTime)) {
                    if (argDateTime.isAfter(LocalDateTime.of(localDate, startTime)) && argDateTime.isBefore(DateTimeUtils.getSpecifiedMaxDateTime(localDate))) {
                        return true;
                    }
                    if (argDateTime.isAfter(DateTimeUtils.getSpecifiedMinDateTime(localDate)) && argDateTime.isBefore(LocalDateTime.of(localDate, endTime))) {
                        return true;
                    }
                    return argDateTime.isEqual(DateTimeUtils.getSpecifiedMaxDateTime(localDate)) || argDateTime.isEqual(DateTimeUtils.getSpecifiedMinDateTime(localDate));
                }
            }
            return false;
        } catch (Exception e) {
            // log.error("DateTimeController parse error ", e);
            return false;
        }
    }

   private static DateTimeRule dateTimeConfigParser(String config) {
       List<String> timeList = Splitter.on('|').splitToList(config);
       String timeRule = timeList.get(0);
       String startTimeStr = timeList.get(1);
       String duration = timeList.get(2);
       LocalTime localTime = DateTimeUtils.parseTimeString(startTimeStr, HMS_FORMATTER);

       return DateTimeRule.builder().timeRule(timeRule).startTime(localTime).endTime(localTime.plusHours(Integer.parseInt(duration))).build();
   }
 
    @Builder
    @Data
    private static class DateTimeRule{
        private  String timeRule;
        private LocalTime startTime;
        private LocalTime endTime;
    }

    public static void main(String[] args) {
        System.out.println(isInConfigTime("daily|21:00:00|2", LocalDateTime.of(2019, 3, 28, 22, 0, 0, 0)));
    }

}
