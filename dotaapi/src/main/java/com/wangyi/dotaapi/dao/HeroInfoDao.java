package com.wangyi.dotaapi.dao;

import com.wangyi.dotaapi.domain.HeroInfo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author wangyi
 * @create 2019-10-22 5:57 PM
 **/
public interface HeroInfoDao {
    @Select("select * from api.hero_info where cn_name like #{key} ")
    List<HeroInfo> getHeroesBykeyName(String key);

    @Select("select * from api.hero_info where hero_id = #{heroId}")
    HeroInfo getHeroByHeroId(int heroId);
    @Select("select * from api.hero_info")
    List<HeroInfo> getAllHeroes();

    @Update("update api.hero_info set cn_roles = #{cnRoles} where hero_id = #{heroId}")
    void updateRoles(HeroInfo heroInfo);
}
