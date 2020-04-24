package com.wangyi.dotaapi.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
////localhost:8080/history/query?start_date=%222020-04-01T02:24:07.294Z%22&end_date=%222020-04-02T02:24:32.767Z%22

    public static boolean compareDate(String startDate , String endDate){
        LocalDate start = LocalDate.parse(startDate,formatter);
        LocalDate end = LocalDate.parse(endDate,formatter);
        return start.isAfter(end);
    }

    public static LocalDate string2Date(String d){
        return LocalDate.parse(d);
    }
}
