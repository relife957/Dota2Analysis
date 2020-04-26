package com.wangyi.dotaapi.service;

import com.alibaba.druid.sql.visitor.functions.If;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.wangyi.dotaapi.dao.HeroInfoDao;
import com.wangyi.dotaapi.domain.HeroInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangyi
 * @create 2019-10-22 5:59 PM
 **/
@Service
public class HeroInfoService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HeroInfoDao heroInfoDao ;
    private Map<Integer,HeroInfo> allHeroesMap ;

    @Autowired
    public HeroInfoService(HeroInfoDao heroInfoDao) {
        this.heroInfoDao = heroInfoDao;
        initHero();
    }


    public List<HeroInfo> getHeroesByKeyName(String keyName){
        return heroInfoDao.getHeroesBykeyName("%"+keyName+"%");
    }

    public HeroInfo getHeroByHeroId(int id){
        if (allHeroesMap == null){
            logger.info("初始化全部英雄映射");
            initHero();
        }
        if (allHeroesMap.containsKey(id)){
            return allHeroesMap.get(id);
        }else{
            logger.info("不包含 {} 对应英雄",id);
        }
        return null;
    }

    public List<HeroInfo> getHeroInfoList(String numberTeam){
        if (Strings.isNullOrEmpty(numberTeam)){
            return new ArrayList<>();
        }
        String[] heroes = numberTeam.split(" ");
        return Lists.newArrayList(heroes).stream()
                .map(hero -> allHeroesMap.get(Integer.parseInt(hero)))
                .filter(Objects::nonNull).collect(Collectors.toList());

    }

    private void initHero() {
        List<HeroInfo> allHeroes = heroInfoDao.getAllHeroes();
        allHeroesMap = new HashMap<>(128);
        for (HeroInfo hero : allHeroes) {
            allHeroesMap.put(hero.getHeroId(),hero);
        }
    }
}
