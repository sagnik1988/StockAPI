package com.assesment.stock.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

public class DateTimeUtil {
    /**
     * returns the current date and time
     *
     * @return LocalDateTime
     */
    public static LocalDateTime getCurrentDateAndTime() {
        return LocalDateTime.now();
    }

    /**
     * returns true if the time difference is more than 60 seconds
     * else return false
     *
     * @param currentTime
     * @param stockTimeStamp
     * @return boolean
     */
    public static boolean findIfDifferenceIsGreaterThan60Seconds(LocalDateTime currentTime, LocalDateTime stockTimeStamp) {
        return ChronoUnit.SECONDS.between(stockTimeStamp, currentTime) > 60;
    }

    public static LocalDateTime getTimefromTimeStamp(long timestamp) {
        LocalDateTime localTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                TimeZone.getDefault().toZoneId());
        return localTime;
    }

    public static boolean isIfOlderThan60Seconds(long timestamp) {
        return DateTimeUtil.findIfDifferenceIsGreaterThan60Seconds(DateTimeUtil.getCurrentDateAndTime(),DateTimeUtil.getTimefromTimeStamp(timestamp));
    }

    public static boolean timeInFuture(Long timestamp) {
        return DateTimeUtil.getCurrentDateAndTime().isBefore(DateTimeUtil.getTimefromTimeStamp(timestamp));
    }
}
