package com.wangyi.dotaapi.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author: wangyi
 * @description:
 * @date: 2020-04-20 09:13
 **/
public class DateUtils {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String today(){
        LocalDate date = LocalDate.now();
        return formatter.format(date);
    }

    public static boolean compareDate(String startDate , String endDate){
        LocalDate start = LocalDate.parse(startDate,formatter);
        LocalDate end = LocalDate.parse(endDate,formatter);
        return start.isAfter(end);
    }

    public static LocalDate string2Date(String d){
        return LocalDate.parse(d);
    }

    public static String date2String(Date d){
        Instant instant = d.toInstant();
        LocalDate ld = instant.atOffset(ZoneOffset.UTC).toLocalDate();
        return formatter.format(ld);
    }
}
