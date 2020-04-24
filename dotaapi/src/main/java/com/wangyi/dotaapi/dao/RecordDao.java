package com.wangyi.dotaapi.dao;

import com.wangyi.dotaapi.domain.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * @author wangyi
 * @create 2019-11-06 4:57 PM
 **/
public interface RecordDao {

    @Select("select * from api.record where add_time between #{startDate} and #{endDate} limit #{limit}")
    List<Record> findRecordByAddTime(LocalDate startDate, LocalDate endDate, int limit);

    @Insert("insert into api.record(input,output,add_time) values(#{input},#{output},now())")
    void insert(Record record);


    @Select("select * from api.record where input=#{input}")
    Record findByInput(String input);

}
