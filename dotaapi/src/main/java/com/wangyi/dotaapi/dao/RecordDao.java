package com.wangyi.dotaapi.dao;

import com.wangyi.dotaapi.domain.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author wangyi
 * @create 2019-11-06 4:57 PM
 **/
public interface RecordDao {

    @Select("select max(id) from api.record")
    int findMaxId();

    @Insert("insert into api.record values(#{id},#{input},#{output},#{count},now(),#{version})")
    void insert(Record record);

    @Update("update api.record set input =#{input},output=#{output},count=#{count},add_time=now(),version=#{version} where id=#{id}")
    void update(Record record);

    @Select("select * from api.record where input=#{input}")
    Record findByInput(String input);

}
