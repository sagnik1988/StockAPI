package com.assesment.stock.util;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilTest {

    @Test
    void getCurrentDateAndTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime currentTimeFromUtil = DateTimeUtil.getCurrentDateAndTime();
        assertThat(currentTimeFromUtil).isNotNull();
        assertThat(currentTime.isEqual(currentTimeFromUtil));
    }

    @Test
    void findIfDifferenceIsGreaterThan60Seconds() {
        long timeStamp = 1478192204000L;
        LocalDateTime localTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp),
                TimeZone.getDefault().toZoneId());
        LocalDateTime currentTime = LocalDateTime.now();
        boolean statusFromUtil = DateTimeUtil.findIfDifferenceIsGreaterThan60Seconds(currentTime, localTime);
        assertTrue(statusFromUtil);
    }

    @Test
    void getTimefromTimeStamp() {
        long timeStamp = 1594550050000L;
        LocalDateTime localTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp),
                TimeZone.getDefault().toZoneId());
        LocalDateTime localTimeFromUtil = DateTimeUtil.getTimefromTimeStamp(timeStamp);
        assertThat(localTimeFromUtil).isNotNull();
        assertThat(localTime.isEqual(localTimeFromUtil));
    }

    @Test
    void isIfOlderThan60Seconds() {
        long timeStamp = 1478192204000L;
        LocalDateTime currentTime = LocalDateTime.now();
        boolean statusFromUtil = DateTimeUtil.isIfOlderThan60Seconds(timeStamp);
        assertThat(statusFromUtil).isNotNull();
        assertTrue(statusFromUtil);
    }

    @Test
    void timeInFuture() {
        long timeStamp = 1626086050000L;
        LocalDateTime currentTime = LocalDateTime.now();
        boolean statusFromUtil = DateTimeUtil.timeInFuture(timeStamp);
        assertThat(statusFromUtil).isNotNull();
        assertTrue(statusFromUtil);
    }
}