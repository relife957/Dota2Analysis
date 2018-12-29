package com.wangyi.dotaapi.dao;

import com.wangyi.dotaapi.domain.OriginalData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wangyi
 * @create 2018-12-12 6:10 PM
 **/

public interface OriginalDataDao {

    @Select("select * from api.original_data")
    List<OriginalData> getAll();

    @Select("select * from api.original_data where sno=#{sno}")
    OriginalData getOneBySno(String sno) ;

    @Select("select * from api.original_data where sno=#{sno}")
    List<OriginalData> getSomeBySno(String sno) ;

}
