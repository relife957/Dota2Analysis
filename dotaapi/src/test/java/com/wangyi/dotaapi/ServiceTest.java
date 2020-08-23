package com.wangyi.dotaapi;

import com.wangyi.dotaapi.dao.HeroInfoDao;
import com.wangyi.dotaapi.domain.HeroInfo;
import com.wangyi.dotaapi.domain.RankInfo;
import com.wangyi.dotaapi.service.RankService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: wangyi
 * @description:
 * @date: 2020-04-25 12:58
 **/
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DotaapiApplication.class)
public class ServiceTest {

    @Autowired
    RankService rankService;

    @Autowired
    HeroInfoDao heroInfoDao;

    @Ignore
    @Test
    public void test(){
        String startDate = "2020-04-01";
        String endDate = "2020-04-30";
        List<RankInfo> rankInfoList = rankService.getRankList(startDate,endDate,10);
        rankInfoList.forEach(System.out::println);
    }


    @Test
    public void updateInfo(){
        List<HeroInfo> heroInfoList = heroInfoDao.getAllHeroes();
        for (HeroInfo heroInfo : heroInfoList) {
            heroInfo.setCnRoles(deal(heroInfo.getCnRoles()));
            heroInfoDao.updateRoles(heroInfo);
        }
    }

    String deal(String attr){
        return attr.substring(1,attr.length()-1).replace("\"","")
                .replace(" ","");
    }
}
