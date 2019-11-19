package com.wangyi.dotaapi.dao;

import com.wangyi.dotaapi.domain.Team;
import org.apache.ibatis.annotations.Select;

/**
 * @author wangyi
 * @create 2019-02-01 8:39 PM
 **/
public interface TeamDao {

    @Select("select * from api.team where team = #{teams}")
    Team getByTeam(String teams);
}
